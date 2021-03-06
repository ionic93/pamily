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

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchShopRepositoryImpl extends QuerydslRepositorySupport implements SearchShopRepository {
    public SearchShopRepositoryImpl() {
        super(Shop.class);
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Long scno, Pageable pageable) {
        log.info("searchPage..........");

        QShop shop = QShop.shop;
        QShopReply qShopReply = QShopReply.shopReply;
        QShopImage qShopImage = QShopImage.shopImage;

        JPQLQuery<Shop> jpqlQuery = from(shop);
        jpqlQuery.leftJoin(qShopReply).on(qShopReply.shop.eq(shop));
        jpqlQuery.leftJoin(qShopImage).on(qShopImage.shop.eq(shop));

        log.info("jqplQuery:"+jpqlQuery);
        JPQLQuery<Tuple> tuple = jpqlQuery.select(shop, qShopImage, qShopReply.countDistinct());
        log.info("tuple: "+tuple);


        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = shop.sid.gt(0L);
        if (scno != null) {
        BooleanExpression exCate = shop.scno.scno.eq(scno);
        booleanBuilder.and(exCate);
        }
        booleanBuilder.and(expression);


        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t){
                    case "t":
                        conditionBuilder.or(shop.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(shop.member.name.contains(keyword));
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

        //sort??? ?????? ???????????? ???????????? ????????? ???????????? ???????????? ??????
        sort.stream().forEach(order->{
            //????????????, ??????????????? Order ????????? ??????
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            //Sort ????????? ????????? prop?????? ??????( ex board??? bno??? ????????? ???)
            String prop = order.getProperty();

            //?????? ???????????? ???????????? ??? ??????(shop)??? JPQLQery??? ????????? ??? ???????????? Q domain ???????????? ??????
            PathBuilder orderByExpression = new PathBuilder(Shop.class, "shop");

            //?????? ????????? ????????? = tuple.orderBy(board.bno.desc());
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));

        });

        tuple.groupBy(qShopImage,shop);

        //page ??????
        tuple.offset(pageable.getOffset());
        //pageable??? ??????????????? ?????????(10)
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info("fetchresult: "+result);

        //count ?????? ??????

        JPQLQuery<Shop> jpqlQuery1 = from(shop);
        jpqlQuery1.leftJoin(qShopReply).on(qShopReply.shop.eq(shop));

        log.info("jqplQuery1:"+jpqlQuery1);
        JPQLQuery<Tuple> tuple1 = jpqlQuery1.select(shop, qShopReply.countDistinct());
        log.info("tuple1: "+tuple1);


        BooleanBuilder booleanBuilder1 = new BooleanBuilder();
        BooleanExpression expression1 = shop.sid.gt(0L);
        if (scno != null) {
            BooleanExpression exCate = shop.scno.scno.eq(scno);
            booleanBuilder1.and(exCate);
        }
        booleanBuilder1.and(expression1);


        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t){
                    case "t":
                        conditionBuilder.or(shop.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(shop.member.name.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(shop.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder1.and(conditionBuilder);
        }

        tuple1.where(booleanBuilder1);
        tuple1.groupBy(shop);

        long count = tuple1.fetchCount();
        log.info("Count: " + count);

        log.info("pageResult: "+result);
        return new PageImpl<Object[]>(
                result.stream().map(t-> t.toArray()).collect(Collectors.toList())
                ,pageable, count);
    }

}
