package com.hoshogi.onlyonepick.domain.game.repository;

import com.hoshogi.onlyonepick.domain.game.dto.request.SearchGameCondition;
import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.hoshogi.onlyonepick.domain.game.entity.QGame.*;
import static com.hoshogi.onlyonepick.domain.report.entity.QReport.*;
import static org.springframework.util.StringUtils.*;

@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Game> search(SearchGameCondition condition, Pageable pageable) {
        List<Game> content = queryFactory
                .selectFrom(game)
                .where(game.id.notIn(
                        JPAExpressions
                                .select(report.game.id)
                                .from(report)
                                .where(report.member.id.eq(condition.getMemberId()))),
                        createdAt(condition.getCreatedAt(), condition.getGameId()),
                        likeCount(condition.getLikeCount(), condition.getGameId()),
                        playCount(condition.getPlayCount(), condition.getGameId()),
                        titleEq(condition.getQuery()))
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(game.count())
                .from(game)
                .where(game.id.notIn(
                                JPAExpressions
                                        .select(report.game.id)
                                        .from(report)
                                        .where(report.member.id.eq(condition.getMemberId()))),
                        titleEq(condition.getQuery()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression createdAt(LocalDateTime createdAt, Long gameId) {
        return createdAt == null ? null : game.createdAt.lt(createdAt).or(game.createdAt.eq(createdAt).and(idLt(gameId)));
    }

    private BooleanExpression likeCount(Long likeCount, Long gameId) {
        return likeCount == null ? null : game.likeCount.lt(likeCount).or(game.likeCount.eq(likeCount).and(idLt(gameId)));
    }

    private BooleanExpression playCount(Long playCount, Long gameId) {
        return playCount == null ? null : game.playCount.lt(playCount).or(game.playCount.eq(playCount).and(idLt(gameId)));
    }

    private BooleanExpression idLt(Long gameId) {
        return gameId == null ? null : game.id.lt(gameId);
    }

    private BooleanExpression titleEq(String query) {
        return hasText(query) ? game.title.contains(query) : null;
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            PathBuilder pathBuilder = new PathBuilder(game.getType(), game.getMetadata());
            orders.add(new OrderSpecifier(direction, pathBuilder.get(order.getProperty())));
        });
        orders.add(new OrderSpecifier(Order.DESC, game.id));
        return orders;
     }
}
