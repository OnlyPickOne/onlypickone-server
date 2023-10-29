package com.hoshogi.onlyonepick.domain.game.service;

import com.hoshogi.onlyonepick.domain.game.dto.request.CreateGameRequest;
import com.hoshogi.onlyonepick.domain.game.dto.request.ShowGameStatsRequest;
import com.hoshogi.onlyonepick.domain.game.dto.response.GameResponse;
import com.hoshogi.onlyonepick.domain.game.dto.response.ShowGameItemResponse;
import com.hoshogi.onlyonepick.domain.game.dto.response.ShowGameStatsResponse;
import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.game.repository.GameRepository;
import com.hoshogi.onlyonepick.domain.item.entity.Item;
import com.hoshogi.onlyonepick.domain.item.repository.ItemRepository;
import com.hoshogi.onlyonepick.domain.item.service.ItemService;
import com.hoshogi.onlyonepick.domain.like.repository.LikeRepository;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.domain.model.ImageExtension;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import com.hoshogi.onlyonepick.global.util.StringUtil;
import com.hoshogi.onlyonepick.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Page<GameResponse> showGames(Pageable pageable) {
        Member member = memberService.findById(SecurityUtil.getCurrentMemberId());
        return gameRepository.findAll(pageable).map(game ->
                new GameResponse(game, isLikedByMember(member, game), isCreatedByMember(member, game),
                        itemRepository.findTopByGameOrderByWinCountDesc(game.getGameId(), THUMBNAIL_ITEM_COUNT)
                                .stream()
                                .map(Item::getImageUrl)
                                .collect(Collectors.toList())));
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
        for (MultipartFile multipartFile : multipartFiles) {
            if (!ImageExtension.containsImageExtension(StringUtil.getFileExtension(multipartFile.getOriginalFilename()))) {
                throw new BadRequestException(ErrorCode.UNSUPPORTED_IMAGE_EXTENSION);
            }
        }
    }

    private Boolean isLikedByMember(Member member, Game game) {
        return likeRepository.existsByMemberAndGame(member, game);
    }

    private Boolean isCreatedByMember(Member member, Game game) {
        if (game.getMember() == member) {
            return true;
        }
        return false;
    }

    private Game findById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.GAME_NOT_FOUND));
    }
}
