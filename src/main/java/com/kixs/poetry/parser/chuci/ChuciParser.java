package com.kixs.poetry.parser.chuci;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.poetry.constant.ParserConstant;
import com.kixs.poetry.entity.Article;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Book;
import com.kixs.poetry.enums.ParserEnum;
import com.kixs.poetry.parser.ParseContext;
import com.kixs.poetry.parser.PoetryParser;
import com.kixs.poetry.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 先秦战国-楚辞解析
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 16:09
 */
@Slf4j
@Component(ParserConstant.CHU_CI)
public class ChuciParser implements PoetryParser {

    @Override
    public String dynasty() {
        return "先秦战国";
    }

    @Override
    public ParseContext parse(String filePath) {
        ParseContext context = new ParseContext();
        // 作者解析
        Author author = new Author();
        author.setId(IdWorker.getIdStr());
        author.setName("屈原");
        author.setDynasty(dynasty());
        context.putAuthor(author);
        // 典籍解析
        Book book = new Book();
        book.setId(IdWorker.getIdStr());
        book.setName(ParserEnum.CHU_CI.getDesc());
        book.setAuthorId(author.getId());
        book.setTag(ParserEnum.CHU_CI.getDesc());
        context.addBook(book);
        // 诗词解析
        String pattern = "^chuci.json$";
        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).forEach(file -> {
                String data = FileUtils.read(file);
                List<ChuciArticle> articles = JSON.parseArray(data, ChuciArticle.class);
                for (int sort = 0; sort < articles.size(); sort++) {
                    ChuciArticle chuci = articles.get(sort);
                    Article article = new Article();
                    article.setId(IdWorker.getIdStr());
                    article.setBookId(book.getId());
                    article.setSection(chuci.getSection());
                    article.setSectionSort(sort);
                    article.setTitle(chuci.getTitle());
                    article.setSort(sort);
                    article.setAuthorId(author.getId());
                    article.setContent(chuci.getContent());
                    context.addArticle(article);
                }
            });
        }
        log.debug("解析屈原楚辞数据：作者-{}，文章-{}", context.getAuthorMap().size(), context.getArticles().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\chuci";
        ChuciParser parser = new ChuciParser();
        parser.parse(filePath);
    }

}
