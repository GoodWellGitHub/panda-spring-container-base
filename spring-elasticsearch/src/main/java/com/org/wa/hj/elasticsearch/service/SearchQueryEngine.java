package com.org.wa.hj.elasticsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public abstract class SearchQueryEngine<T> {
    @Autowired
    protected ElasticsearchTemplate elasticsearchTemplate;

    public abstract int saveOrUpdate(List<T> t);

    public abstract <R> List<R> aggregation(T query, Class<R> rClass);

    // public abstract <R> Page<R> scroll(T query, Class<R> rClass, Pageable pageable, ScrollIdForNode scrollId);

    public abstract <R> List<R> find(T query, Class<R> rClass, int size);

    public abstract <R> Page<R> find(T query, Class<R> rClass, Pageable pageable);

    public abstract <R> R sum(T query, Class<R> rClass);


    protected Document getDocument(T t) {
        Document document = t.getClass().getAnnotation(Document.class);
/*        if (document == null) {
            throw new SearchQueryBuildException("Can't find annotation @Document on " + t.getClass().getName());
        }*/
        return document;
    }

    protected String getFieldName(Field field, String column) {
        return !StringUtils.isEmpty(column) ? column : field.getName();
    }

    protected void setFieldValue(Field field, Object object, Object value) {
        boolean isAccessable = field.isAccessible();
        field.setAccessible(true);
        try {
            switch (field.getType().getSimpleName()) {
                case "BigDecimal":
                    field.set(object, new BigDecimal(value.toString()).setScale(5, BigDecimal.ROUND_HALF_UP));
                    break;
                case "Long":
                    field.set(object, new Long(value.toString()));
                    break;
                case "Integer":
                    field.set(object, new Integer(value.toString()));
                    break;
                case "Date":
                    field.set(object, new Date(Long.valueOf(value.toString())));
                    break;
                default:
                    field.set(object, value);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(isAccessable);
        }
    }


    protected Object getFieldValue(Field field, Object object) {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(isAccessible);
        }
        return null;
    }

    /**
     * 91      * 转换为es识别的value值
     * 92      *
     * 93      * @param value
     * 94      * @return
     * 95
     */
    protected Object formValue(Object value) {
        if (value instanceof Date) {
            return ((Date) value).getTime();
        } else {
            return value;
        }
    }


/*    protected int getNumberOfShards(T t) {
        return Integer.parseInt(elasticsearchTemplate.getSetting(getDocument(t).indexName()).get(IndexMetaData.SETTING_NUMBER_OF_SHARDS).toString());
    }*/
}
