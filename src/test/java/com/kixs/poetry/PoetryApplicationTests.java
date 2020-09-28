package com.kixs.poetry;

import com.kixs.poetry.config.PoetryProperties;
import com.kixs.poetry.parser.ParserSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PoetryApplicationTests {

    @Resource
    private PoetryProperties poetryProperties;

    @Resource
    private ParserSupport parserSupport;

    @Test
    public void test() {
        parserSupport.parse(poetryProperties.getBaseDir());
    }

}
