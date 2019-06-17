package com.org.wa.hj.elasticsearch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "paper", type = "spring-paper", indexStoreType = "s", shards = 2, replicas = 2)
public class ElasticSetting {
    int number_of_replicas;
    int number_of_shards;
}
