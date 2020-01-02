package com.example.springboot_es;

import com.example.springboot_es.es.EsBlogRepository;
import com.example.springboot_es.es.EsBolg;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import org.elasticsearch.client.transport.TransportClient;
@SpringBootTest
class SpringbootEsApplicationTests {

	@Autowired
	EsBlogRepository blogRepository;

	@Autowired
	private TransportClient esClient;

	@Test
	void contextLoads() {
	}

	@Test
	public void testEs(){

		String searchContent = "小时代是一部好电影";
		TransportClient client = esClient;
		String index = "blog";
		SearchRequestBuilder searchBuilder = client.prepareSearch(index);
		//分页
		searchBuilder.setFrom(0).setSize(10);
		//explain为true表示根据数据相关度排序，和关键字匹配最高的排在前面
		searchBuilder.setExplain(true);

		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		// 搜索 title字段包含IPhone的数据
		queryBuilder.should(QueryBuilders.matchQuery("title", searchContent));

		FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[1];

		//过滤条件1：分类为：品牌手机最重要 -- 权重查询Weight
		ScoreFunctionBuilder<WeightBuilder> scoreFunctionBuilder = new WeightBuilder();
		scoreFunctionBuilder.setWeight(100);
		QueryBuilder termQuery = QueryBuilders.termQuery("author", "小红");
		FunctionScoreQueryBuilder.FilterFunctionBuilder category = new FunctionScoreQueryBuilder.FilterFunctionBuilder(termQuery, scoreFunctionBuilder);
		filterFunctionBuilders[0] = category;

		// 过滤条件2：销量越高越排前 --计分查询 FieldValueFactor
//		ScoreFunctionBuilder<FieldValueFactorFunctionBuilder> fieldValueScoreFunction = new FieldValueFactorFunctionBuilder("salesVolume");
//		((FieldValueFactorFunctionBuilder) fieldValueScoreFunction).factor(1.2f);
//		FunctionScoreQueryBuilder.FilterFunctionBuilder sales = new FunctionScoreQueryBuilder.FilterFunctionBuilder(fieldValueScoreFunction);
//		filterFunctionBuilders[1] = sales;

		// 给定每个用户随机展示：  --random_score
//		ScoreFunctionBuilder<RandomScoreFunctionBuilder> randomScoreFilter = new RandomScoreFunctionBuilder();
//		((RandomScoreFunctionBuilder) randomScoreFilter).seed(2);
//		FunctionScoreQueryBuilder.FilterFunctionBuilder random = new FunctionScoreQueryBuilder.FilterFunctionBuilder(randomScoreFilter);
//		filterFunctionBuilders[2] = random;

		// 多条件查询 FunctionScore
		FunctionScoreQueryBuilder query = QueryBuilders.functionScoreQuery(queryBuilder, filterFunctionBuilders)
				.scoreMode(FunctionScoreQuery.ScoreMode.SUM).boostMode(CombineFunction.SUM);
		searchBuilder.setQuery(query);

		SearchResponse response = searchBuilder.execute().actionGet();
		SearchHits hits = response.getHits();
		String searchSource;
		for (SearchHit hit : hits)
		{
			searchSource = hit.getSourceAsString();
			System.out.println(searchSource);
		}
		//        long took = response.getTook().getMillis();
		long total = hits.getTotalHits();
		System.out.println(total);


//		Iterable<EsBolg> all = blogRepository.findAll();
//		Iterator<EsBolg> iterator = all.iterator();
//		EsBolg next = iterator.next();
//		System.out.println("-------------"+next.getTitle());

	}
}
