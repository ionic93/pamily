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

        //sort는 단일 컬럼에만 적용하면 의미가 없으므로 순회하며 처리
        sort.stream().forEach(order->{
            //오름차순, 내림차순을 Order 객체로 변환
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            //Sort 객체의 속성을 prop으로 담음( ex board의 bno를 가져올 때)
            String prop = order.getProperty();

            //이때 생성할때 문자열로 된 이름(shop)는 JPQLQery를 생성할 때 사용하는 Q domain 변수명과 동일
            PathBuilder orderByExpression = new PathBuilder(Shop.class, "shop");

            //직접 코드로 처리시 = tuple.orderBy(board.bno.desc());
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        tuple.groupBy(shop);

        //page 처리
        tuple.offset(pageable.getOffset());
        //pageable의 사이즈만큼 들고옴(10)
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info("fetchresult: "+result);

        //count 얻는 방법
        long count = tuple.fetchCount();
        log.info("Count: " + count);

        log.info("resultSearch: "+result);
        return new PageImpl<Object[]>(
                result.stream().map(t-> t.toArray()).collect(Collectors.toList())
                ,pageable, count);
    }

}
