package com.org.wa.hj.elasticsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ElasticsearchUtil {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public String createIndex(String index, String type, Map<String,Integer> settingObject, String id,Object object) {
        if (index == null) {
            return "";
        } else if (type == null && settingObject == null) {
            elasticsearchTemplate.createIndex(index);
        } else if (type == null && settingObject != null) {
            elasticsearchTemplate.createIndex(index, settingObject);
        } else {
            IndexQuery indexQuery = new IndexQueryBuilder().withIndexName(index).withType(type).withId(id).withObject(object).build();
            String document = elasticsearchTemplate.index(indexQuery);
            return document;
        }
        return "";
    }




}
