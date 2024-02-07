package com.hoshogi.onlyonepick.domain.notice.repository;

import com.hoshogi.onlyonepick.domain.notice.dto.request.SearchNoticeCondition;
import com.hoshogi.onlyonepick.domain.notice.entity.Notice;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.hoshogi.onlyonepick.domain.notice.entity.QNotice.*;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Notice> search(SearchNoticeCondition condition, Pageable pageable) {
       List<Notice> content = queryFactory
               .selectFrom(notice)
               .where(createdAt(condition.getCreatedAt(), condition.getNoticeId()))
               .limit(pageable.getPageSize() + 1)
               .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
               .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression createdAt(LocalDateTime createdAt, Long noticeId) {
        return createdAt == null ? null : notice.createdAt.lt(createdAt).or(notice.createdAt.eq(createdAt).and(idLt(noticeId)));
    }

    private BooleanExpression idLt(Long noticeId) {
        return noticeId == null ? null : notice.id.lt(noticeId);
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            PathBuilder pathBuilder = new PathBuilder(notice.getType(), notice.getMetadata());
            orders.add(new OrderSpecifier(direction, pathBuilder.get(order.getProperty())));
        });
        orders.add(new OrderSpecifier(Order.DESC, notice.id));
        return orders;
    }
}
