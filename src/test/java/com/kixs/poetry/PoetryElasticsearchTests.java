package com.kixs.poetry;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kixs.poetry.config.PoetryProperties;
import com.kixs.poetry.dao.PoetryDao;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.parser.ParserSupport;
import com.kixs.poetry.service.AuthorService;
import com.kixs.poetry.service.PoetryService;
import org.assertj.core.util.Lists;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PoetryElasticsearchTests {

    @Resource
    private PoetryProperties poetryProperties;

    @Resource
    private ParserSupport parserSupport;

    @Resource
    private AuthorService authorService;

    @Resource
    private PoetryService poetryService;

    @Resource
    private PoetryDao poetryDao;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    ThreadPoolExecutor

    @Test
    public void createIndexTest() throws IOException {
        // 1. 创建索引请求
        // CreateIndexRequest firstIndex = new CreateIndexRequest("poetry_author");
        CreateIndexRequest firstIndex = new CreateIndexRequest("poetry");

        // 2. 客户端执行创建索引的请求
        CreateIndexResponse response = restHighLevelClient.indices().create(firstIndex, RequestOptions.DEFAULT);

        System.out.println(response.index());
    }

    @Test
    public void deleteIndexTest() throws IOException {
        // 1. 创建一个delete请求，用于删除索引信息
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("poetry");

        // 2. 客户端执行删除请求
        AcknowledgedResponse result = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSON(result));
    }

    @Test
    public void addDocumentTest() throws IOException {

        List<Author> authors = authorService.queryAuthor();
/*
        // 1. 创建对象
        User user = new User("xununan", 27);

        // 2. 创建请求并指定索引
        IndexRequest indexRequest = new IndexRequest("xununan_index");

        // 3. 创建的规则：put /xununan_index/_doc/1
        indexRequest.id("1");            // 设置ID
        indexRequest.timeout("1s");      // 设置超时时间

        // 4. 将数据放入到请求中
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);

        // 5. 使用客户端发送请求
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(JSON.toJSONString(index));*/
    }

    @Test
    public void addDocumentByBatchTest() throws IOException {
        // 1. 创建批量的请求
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("100s");     //  设置超时时间

        List<Author> authors = authorService.queryAuthor();

        // 2. 将多条数据批量的放入bulkRequest中
        for (int i = 0; i < authors.size(); i++) {
            // 批量更新和批量删除在这里修改对应的请求即可
            bulkRequest.add(new IndexRequest("poetry_author")
                    .id(authors.get(i).getId())
                    .source(JSON.toJSONString(authors.get(i)), XContentType.JSON)
            );
        }


        // 3. 执行批量创建文档
        BulkResponse responses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(responses.hasFailures());    // 是否失败，如果false则表示全部成功
        System.out.println(JSON.toJSONString(responses));
    }

    @Test
    public void addPoetryDocumentByBatchTest() throws IOException {
        LambdaQueryWrapper<Poetry> wrapper = new LambdaQueryWrapper<>();
        int count = poetryDao.selectCount(wrapper);

        int pageSize = 500;
        int pageNo = 1;
        int sumCount = 0;

        do {
            IPage<Poetry> page = poetryDao.selectPage(new Page<>(pageNo, pageSize), wrapper);
            if (page.getRecords().size() > 0) {
                // 1. 创建批量的请求
                BulkRequest bulkRequest = new BulkRequest();
                // 设置超时时间
                bulkRequest.timeout("100s");
                // 批量更新和批量删除在这里修改对应的请求即可
                // 2. 将多条数据批量的放入bulkRequest中
                page.getRecords().forEach(poetry ->
                        bulkRequest.add(new IndexRequest("poetry")
                                .id(poetry.getId())
                                .source(JSON.toJSONString(poetry), XContentType.JSON)
                        ));
                // 3. 执行批量创建文档
                BulkResponse responses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
                System.out.println(responses.hasFailures() + "\t" + page.getRecords().size());
            }
            pageNo++;
            sumCount += pageSize;
        } while (count >= sumCount);
    }

    @Test
    public void readTest() throws IOException {
        SearchRequest request = new SearchRequest("poetry_author");
        System.out.println(request);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("id", "1332698485254205441"));
        request.source(builder);
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(JSON.toJSONString(hit.getSourceAsMap()));
        }
    }

}
