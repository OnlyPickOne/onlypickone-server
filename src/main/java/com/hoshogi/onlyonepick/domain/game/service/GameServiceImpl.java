package com.hoshogi.onlyonepick.domain.game.service;

import com.hoshogi.onlyonepick.domain.game.dto.request.CreateGameRequest;
import com.hoshogi.onlyonepick.domain.game.dto.request.SearchGameCondition;
import com.hoshogi.onlyonepick.domain.game.dto.request.ShowGameStatsRequest;
import com.hoshogi.onlyonepick.domain.game.dto.response.GameResponse;
import com.hoshogi.onlyonepick.domain.game.dto.response.ShowGameItemResponse;
import com.hoshogi.onlyonepick.domain.game.dto.response.ShowGameStatsResponse;
import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.game.repository.GameRepository;
import com.hoshogi.onlyonepick.domain.item.entity.Item;
import com.hoshogi.onlyonepick.domain.item.repository.ItemRepository;
import com.hoshogi.onlyonepick.domain.item.service.ItemService;
import com.hoshogi.onlyonepick.domain.like.entity.Like;
import com.hoshogi.onlyonepick.domain.like.repository.LikeRepository;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.global.model.ImageExtension;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.error.exception.ForbiddenException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import com.hoshogi.onlyonepick.global.util.StringUtil;
import com.hoshogi.onlyonepick.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hoshogi.onlyonepick.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final MemberService memberService;
    private final ItemService itemService;
    private final S3Service s3Service;
    private final GameRepository gameRepository;
    private final ItemRepository itemRepository;
    private final LikeRepository likeRepository;

    @Value("${cloud.aws.s3.directory}")
    private String directory;

    private static final Long THUMBNAIL_ITEM_COUNT  = 2L;

    @Override
    @Transactional
    public void createGame(CreateGameRequest request) {
        Game game = request.toEntity(memberService.findById(SecurityUtil.getCurrentMemberId()));
        List<Item> items = createItems(request.getMultipartFiles(), game);
        game.addItems(items);
        gameRepository.save(game);
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<GameResponse> searchGames(SearchGameCondition condition, Pageable pageable) {
        if (SecurityUtil.hasAuthentication()) {
            Member member = memberService.findById(SecurityUtil.getCurrentMemberId());
            condition.setMemberId(member.getId());
            return gameRepository.search(condition, pageable).map(game ->
                    mappingGameResponse(game, isLikedByMember(member, game), isCreatedByMember(member, game),
                            extractThumbnailImages(game.getItems())));
        }
        return gameRepository.search(condition, pageable).map(game ->
                mappingGameResponse(game, false, false, extractThumbnailImages(game.getItems())));
    }

    @Override
    @Transactional
    public GameResponse showGameInfo(Long gameId) {
        Game game = findById(gameId);
        game.increaseViewCount();
        if (SecurityUtil.hasAuthentication()) {
            Member member = memberService.findById(SecurityUtil.getCurrentMemberId());
            return mappingGameResponse(game, isLikedByMember(member, game), isCreatedByMember(member, game),
                    extractThumbnailImages(game.getItems()));
        }
        return mappingGameResponse(game, false, false, extractThumbnailImages(game.getItems()));
    }

    @Override
    @Transactional
    public List<ShowGameStatsResponse> showGameStats(ShowGameStatsRequest request, Long gameId) {
        Game game = findById(gameId);
        game.increasePlayCount();
        itemService.findById(request.getWinItemId()).increaseWinCount();
        return itemRepository.findByGameOrderByWinCountDesc(game)
                .stream()
                .map(ShowGameStatsResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowGameItemResponse> showGameItems(Long gameId, Long count) {
        return itemRepository.findRandomByGame(gameId, count)
                .stream()
                .map(ShowGameItemResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteGame(Long gameId) {
        Member member = memberService.findById(SecurityUtil.getCurrentMemberId());
        Game game = findById(gameId);
        if (member.isNotAdmin() && game.getMember() != member) {
            throw new ForbiddenException(FORBIDDEN_USER);
        }
        gameRepository.delete(game);
    }

    @Override
    @Transactional(readOnly = true)
    public Game findById(Long gameId) {
         return gameRepository.findById(gameId)
                .orElseThrow(() -> new BadRequestException(GAME_NOT_FOUND));
    }

    private List<Item> createItems(List<MultipartFile> multipartFiles, Game game) {
        List<Item> items = new ArrayList<>();
        List<String> imageUrls = uploadImageToS3(multipartFiles);
        for (int i = 0; i < multipartFiles.size(); i++) {
            items.add(Item.create(imageUrls.get(i),
                    StringUtil.removeFileExtension(multipartFiles.get(i).getOriginalFilename()), game));
        }
        return items;
    }

    private List<String> uploadImageToS3(List<MultipartFile> multipartFiles) {
        checkImageExtension(multipartFiles);
        return s3Service.uploadFiles(multipartFiles, directory);
    }

    private void checkImageExtension(List<MultipartFile> multipartFiles) {
        multipartFiles.forEach(multipartFile -> {
            if (!ImageExtension.containsImageExtension(StringUtil.getFileExtension(multipartFile.getOriginalFilename()))) {
                throw new BadRequestException(UNSUPPORTED_IMAGE_EXTENSION);
            }
        });
    }

    private Boolean isLikedByMember(Member member, Game game) {
        return likeRepository.findByMemberAndGame(member, game)
                .map(Like::isNotDeleted)
                .orElse(false);
    }

    private List<String> extractThumbnailImages(List<Item> items) {
        return items.stream()
                .sorted((o1, o2) -> Long.compare(o1.getWinCount(), o2.getWinCount()) * -1)
                .limit(THUMBNAIL_ITEM_COUNT)
                .map(Item::getImageUrl)
                .collect(Collectors.toList());
    }

    private Boolean isCreatedByMember(Member member, Game game) {
        return game.getMember() == member;
    }

    private GameResponse mappingGameResponse(Game game, boolean isLiked, boolean isCreated, List<String> imageUrls) {
        return new GameResponse(game, isLiked, isCreated, imageUrls);
    }
}
