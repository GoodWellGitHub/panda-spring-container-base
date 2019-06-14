package com.org.wa.hj.elasticsearch.dao;

import com.org.wa.hj.elasticsearch.domain.Paper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PaperRepository extends ElasticsearchRepository<Paper, String> {
}
