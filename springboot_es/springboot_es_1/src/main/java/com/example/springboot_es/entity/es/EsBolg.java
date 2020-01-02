package com.example.springboot_es.entity.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Entity;

@Data
@Document(indexName = "blog",type="doc",useServerConfiguration = true,createIndex = false)
@Entity
public class EsBolg {
    @Id
    private Integer id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String author;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;
}
