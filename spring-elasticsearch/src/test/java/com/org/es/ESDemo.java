package com.org.es;

import com.org.wa.hj.elasticsearch.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:/spring/spring.xml")
public class ESDemo {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void demo() {
        System.out.println("ok");
    }

    @Test
    public void elasticsearchDemo() {
        IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("spring-es").withType("spring").withId("1").withObject(Student.getStudent()).build();
        String documentId = elasticsearchTemplate.index(indexQuery);
        System.out.println("索引结果 : " + documentId);
    }
}
