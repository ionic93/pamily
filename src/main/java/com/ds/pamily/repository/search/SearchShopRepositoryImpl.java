package com.ds.pamily.repository.search;

import com.ds.pamily.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchShopRepositoryImpl extends QuerydslRepositorySupport implements SearchShopRepository {
    public SearchShopRepositoryImpl() {
        super(Shop.class);
    }

    @Override
    public Shop search1() {
        log.info("search1.....");
        QShop shop = QShop.shop;
        QShopReply shopReply = QShopReply.shopReply;
        QMember member = QMember.member;

        JPQLQuery<Shop> jpqlQuery = from(shop);
        jpqlQuery.leftJoin(member).on(shop.member.eq(member));
        jpqlQuery.leftJoin(shopReply).on(shopReply.shop.eq(shop));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(shop, member.name, shopReply.count()).groupBy(shop);

        log.info("------------");
        log.info(tuple);
        log.info("------------");
        List<Tuple> result = tuple.fetch();
        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage............");

        QShop shop = QShop.shop;
        QShopReply shopReply = QShopReply.shopReply;
        QMember member = QMember.member;

        JPQLQuery<Shop> jpqlQuery = from(shop);
        jpqlQuery.leftJoin(member).on(shop.member.eq(member));
        jpqlQuery.leftJoin(shopReply).on(shopReply.shop.eq(shop));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(shop, member, shopReply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = shop.sid.gt(0L);
        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(shop.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.name.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(shop.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);

        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Shop.class,"shop");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        tuple.groupBy(shop);

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        log.info(result);
        long count = tuple.fetchCount();
        log.info("COUNT: "+count);

        return new PageImpl<Object[]>(result.stream().map(t->t.toArray()).collect(Collectors.toList()),pageable,count);
    }
}
