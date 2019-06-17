package com.org.wa.hj.elasticsearch.service;

import com.sun.xml.internal.ws.api.server.Container;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.search.MatchQuery;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

@Component
@ComponentScan
public class SimpleSearchQueryEngine<T> extends SearchQueryEngine<T> {
    @Override
    public int saveOrUpdate(List<T> t) {
        if (CollectionUtils.isEmpty(t)) {
            return 0;
        }
        T base = t.get(0);
        Field fieldId = null;
        for (Field field : base.getClass().getDeclaredFields()) {
            Id businessId = field.getAnnotation(Id.class);
            if (businessId != null) {
                fieldId = field;
                break;
            }
        }

        if (fieldId == null) {
            return 0;
        }

        Document document = getDocument(base);
        List<UpdateQuery> updateQueries = new ArrayList<UpdateQuery>();

        for (T tValue : t) {
            UpdateQuery updateQuery = new UpdateQuery();
            updateQuery.setIndexName(document.indexName());
            updateQuery.setId(getFieldValue(fieldId, tValue).toString());
            updateQuery.setType(document.type());
            //updateQuery.setUpdateRequest(new UpdateRequest(updateQuery.getIndexName(), updateQuery.getType(), updateQuery.getId()).doc(JSONObject.toJSONString(tValue, SerializerFeature.WriteMapNullValue)));
            updateQuery.setDoUpsert(true);
            updateQuery.setClazz(tValue.getClass());
            updateQueries.add(updateQuery);
        }
        elasticsearchTemplate.bulkUpdate(updateQueries);
        return updateQueries.size();
    }

    @Override
    public <R> List<R> aggregation(T query, Class<R> rClass) {


        return null;
    }

    @Override
    public <R> List<R> find(T query, Class<R> rClass, int size) {
        return null;
    }

    @Override
    public <R> Page<R> find(T query, Class<R> rClass, Pageable pageable) {
        return null;
    }

    @Override
    public <R> R sum(T query, Class<R> rClass) {
        return null;
    }


/*    private NativeSearchQueryBuilder buildNativeSearchQueryBuilder(T query) {
        Document document = getDocument(query);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withIndices(document.indexName())
                .withTypes(document.type());

        QueryBuilder whereBuilder = buildBoolQuery(query);
        if (whereBuilder != null) {
            nativeSearchQueryBuilder.withQuery(whereBuilder);
        }

        return nativeSearchQueryBuilder;
    }*/

/*    private BoolQueryBuilder buildBoolQuery(T query) {
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        buildMatchQuery(boolQueryBuilder, query);
        buildRangeQuery(boolQueryBuilder, query);
        BoolQueryBuilder queryBuilder = boolQuery().must(boolQueryBuilder);
        return queryBuilder;
    }*/


    /**
     * and or 查询构建
     *
     * @param boolQueryBuilder
     * @param query
     */
/*    private void buildMatchQuery(BoolQueryBuilder boolQueryBuilder, T query) {
        Class clazz = query.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            MatchQuery annotation = field.getAnnotation(MatchQuery.class);
            Object value = getFieldValue(field, query);
            if (annotation == null || value == null) {
                continue;
            }
            if (Container.must.equals(annotation.container())) {
                boolQueryBuilder.must(matchQuery(getFieldName(field, annotation.column()), formatValue(value)));
            } else if (should.equals(annotation.container())) {
                if (value instanceof Collection) {
                    BoolQueryBuilder shouldQueryBuilder = boolQuery();
                    Collection tmp = (Collection) value;
                    for (Object obj : tmp) {
                        shouldQueryBuilder.should(matchQuery(getFieldName(field, annotation.column()), formatValue(obj)));
                    }
                    boolQueryBuilder.must(shouldQueryBuilder);
                } else {
                    boolQueryBuilder.must(boolQuery().should(matchQuery(getFieldName(field, annotation.column()), formatValue(value))));
                }
            }
        }
    }*/
}
