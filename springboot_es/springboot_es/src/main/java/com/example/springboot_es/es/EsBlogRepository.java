package com.example.springboot_es.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBolg,Integer> {
}
