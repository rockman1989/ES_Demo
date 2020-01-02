package com.example.springboot_es;

import com.example.springboot_es.entity.es.EsBolg;
import com.example.springboot_es.repository.es.EsBlogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

@SpringBootTest
class SpringbootEsApplicationTests {

	@Autowired
	EsBlogRepository blogRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testEs(){

		Iterable<EsBolg> all = blogRepository.findAll();
		Iterator<EsBolg> iterator = all.iterator();
		EsBolg next = iterator.next();
		System.out.println("-------------"+next.getTitle());

	}
}
