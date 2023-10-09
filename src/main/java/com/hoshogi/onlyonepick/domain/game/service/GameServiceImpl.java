package com.hoshogi.onlyonepick.domain.game.service;

import com.hoshogi.onlyonepick.domain.game.dto.request.CreateGameRequest;
import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.game.repository.GameRepository;
import com.hoshogi.onlyonepick.domain.item.entity.Item;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import com.hoshogi.onlyonepick.global.util.StringUtil;
import com.hoshogi.onlyonepick.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final MemberService memberService;
    private final S3Service s3Service;
    private final GameRepository gameRepository;

    @Override
    @Transactional
    public void createGame(CreateGameRequest request, List<MultipartFile> multipartFiles) {
        Member member = memberService.findById(SecurityUtil.getCurrentMemberId());
        Game game = request.toEntity(member);
        List<String> imageUrls = uploadImagesToS3(multipartFiles);
        List<Item> items = createItems(multipartFiles, imageUrls, game);
        game.addItems(items);
        gameRepository.save(game);
    }

    private List<Item> createItems(List<MultipartFile> multipartFiles, List<String> imageUrls, Game game) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < multipartFiles.size(); i++) {
            items.add(Item.create(imageUrls.get(i),
                    StringUtil.removeFileExtension(multipartFiles.get(i).getOriginalFilename()), game));
        }
        return items;
    }
    private List<String> uploadImagesToS3(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null && multipartFiles.isEmpty()) {
            throw new BadRequestException(ErrorCode._BAD_REQUEST);
        }

        try {
            return s3Service.uploadFiles(multipartFiles, "images");
        } catch (IOException e) {
            throw new BadRequestException(ErrorCode.S3_SERVER_ERROR);
        }
    }
}
