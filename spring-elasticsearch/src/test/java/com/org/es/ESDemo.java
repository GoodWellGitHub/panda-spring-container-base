package com.org.es;

import com.org.wa.hj.elasticsearch.domain.Paper;
import com.org.wa.hj.elasticsearch.service.Init;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:/spring/spring.xml")
public class ESDemo {
    @Autowired
    private Init init;

    @Autowired(required = false)
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void demo() {
        System.out.println("ok");
    }

    @Test
    public void elasticsearchDemo() {
        IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("paper").withType("spring-paper").withId("1").withObject(new Paper()).build();
        String documentId = elasticsearchTemplate.index(indexQuery);
        System.out.println("索引结果 : " + documentId);
    }


    @Test
    public void esESCreate() {
        List<Paper> papers = new ArrayList<Paper>();
        for (int i = 0; i < 40; i++) {
            Paper post = new Paper();
            post.setTitle(init.getTitle().get(i));
            post.setContent(init.getContent().get(i));
            post.setWeight(i);
            post.setId(UUID.randomUUID().toString());
            post.setUserId(i % 10);
            papers.add(post);
        }

        papers.forEach(paper -> {
                    IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("paper").withType("spring-paper").withId(UUID.randomUUID().toString()).withObject(paper).build();
                    String documentId = elasticsearchTemplate.index(indexQuery);
                    System.out.println("索引结果 : " + documentId);
                }
        );
    }

    @Test
    public void boolSearch() {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("id", "455892a3-de0c-4e95-a22c-7af99184c38b"));


        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withIndices("paper")
                .withTypes("spring-paper")
                .withSearchType(SearchType.DEFAULT)
                .build();

        Map map = elasticsearchTemplate.getMapping("paper", "spring-paper");

        System.out.println(map);
        //   List<Paper> papers = elasticsearchTemplate.queryForList(searchQuery,Paper.class);

        /*
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                return searchResponse.getAggregations();
            }
        });*/

        //     System.out.println(papers);

    }
}
