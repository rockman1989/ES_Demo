package com.example.springboot_es.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "blog",type="_doc",useServerConfiguration = true)
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
