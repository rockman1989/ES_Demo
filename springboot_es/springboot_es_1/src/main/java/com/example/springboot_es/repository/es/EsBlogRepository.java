package com.example.springboot_es.repository.es;

import com.example.springboot_es.entity.es.EsBolg;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBolg,Integer> {
}
