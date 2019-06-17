package com.org.wa.hj.elasticsearch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "paper", type = "spring-paper", indexStoreType = "fs", shards = 3, replicas = 2)
public class Paper {
    @Id
    private String id;

    private String title;

    private String content;

    private int userId;

    private int weight;

}
