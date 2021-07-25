package com.kixs.poetry.parser.sishuwujing;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.poetry.constant.ParserConstant;
import com.kixs.poetry.entity.Article;
import com.kixs.poetry.entity.Book;
import com.kixs.poetry.enums.ParserEnum;
import com.kixs.poetry.parser.ParseContext;
import com.kixs.poetry.parser.PoetryParser;
import com.kixs.poetry.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 四书五经-诗经解析
 *
 * @author kixs
 * @version v1.0.0
 * @since 2021/7/24 23:07
 */
@Slf4j
@Service(ParserConstant.SHIJING)
public class ShiJingParser implements PoetryParser {

    @Override
    public String dynasty() {
        return "先秦战国";
    }

    @Override
    public ParseContext parse(String filePath) {
        ParseContext context = new ParseContext();
        // 典籍解析
        Book book = new Book();
        book.setId(IdWorker.getIdStr());
        book.setName(ParserEnum.SHIJING.getDesc());
        book.setTag("四书五经");
        context.addBook(book);
        // 诗词解析
        String pattern = "^shijing.json$";
        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).forEach(file -> {
                String data = FileUtils.read(file);
                List<Shijing> articles = JSON.parseArray(data, Shijing.class);
                for (int sort = 0; sort < articles.size(); sort++) {
                    Shijing shijing = articles.get(sort);
                    Article article = new Article();
                    article.setId(IdWorker.getIdStr());
                    article.setBookId(book.getId());
                    article.setVolume(shijing.getChapter());
                    article.setVolumeSort(sort);
                    article.setSection(shijing.getSection());
                    article.setSectionSort(sort);
                    article.setTitle(shijing.getTitle());
                    article.setSort(sort);
                    article.setContent(shijing.getContent());
                    context.addArticle(article);
                }
            });
        }
        log.debug("解析四书五经-诗经数据：作者-{}，文章-{}", context.getAuthorMap().size(), context.getArticles().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\shijing";
        ShiJingParser parser = new ShiJingParser();
        parser.parse(filePath);
    }
}
