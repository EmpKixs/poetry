package com.kixs.poetry.parser.mengxue;

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
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 蒙学解析
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 16:57
 */
@Slf4j
@Component(ParserConstant.MENGXUE)
public class MengxueParser implements PoetryParser {

    @Override
    public String dynasty() {
        return null;
    }

    @Override
    public ParseContext parse(String filePath) {
        ParseContext context = new ParseContext();

        CompletableFuture<Void> parseBaijiaxing = CompletableFuture.runAsync(() -> parseBaijiaxing(context, filePath));
        CompletableFuture<Void> parseDizigui = CompletableFuture.runAsync(() -> parseDizigui(context, filePath));
        CompletableFuture<Void> parseGuwenguanzhi = CompletableFuture.runAsync(() -> parseGuwenguanzhi(context, filePath));
        CompletableFuture<Void> parseQianjiashi = CompletableFuture.runAsync(() -> parseQianjiashi(context, filePath));
        CompletableFuture<Void> parseQianziwen = CompletableFuture.runAsync(() -> parseQianziwen(context, filePath));
        CompletableFuture<Void> parseSanzijing = CompletableFuture.runAsync(() -> parseSanzijing(context, filePath));
        CompletableFuture<Void> parseShenglvqimeng = CompletableFuture.runAsync(() -> parseShenglvqimeng(context, filePath));
        CompletableFuture<Void> parseTangshisanbaishou = CompletableFuture.runAsync(() -> parseTangshisanbaishou(context, filePath));
        CompletableFuture<Void> parseWenzimengqiu = CompletableFuture.runAsync(() -> parseWenzimengqiu(context, filePath));
        CompletableFuture<Void> parseYouxueqionglin = CompletableFuture.runAsync(() -> parseYouxueqionglin(context, filePath));
        CompletableFuture<Void> parseZengguangxianwen = CompletableFuture.runAsync(() -> parseZengguangxianwen(context, filePath));
        CompletableFuture<Void> parseZhuzijiaxun = CompletableFuture.runAsync(() -> parseZhuzijiaxun(context, filePath));

        CompletableFuture<Void> allOf = CompletableFuture.allOf(parseBaijiaxing, parseDizigui, parseGuwenguanzhi,
                parseQianjiashi, parseQianziwen, parseSanzijing, parseShenglvqimeng, parseTangshisanbaishou,
                parseWenzimengqiu, parseYouxueqionglin, parseZengguangxianwen, parseZhuzijiaxun);
        allOf.join();

        log.debug("解析蒙学数据：作者-{}，文章-{}", context.getAuthorMap().size(), context.getArticles().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\mengxue";
        MengxueParser parser = new MengxueParser();
        parser.parse(filePath);
    }

    /**
     * 百家姓
     *
     * @param context 解析上下文
     */
    private void parseBaijiaxing(ParseContext context, String filePath) {
        String pattern = "^baijiaxing.json$";
        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).forEach(file -> {
                String data = FileUtils.read(file);
                Baijiaxing baijiaxing = JSON.parseObject(data, Baijiaxing.class);
                // 作者解析
                Author author = new Author();
                author.setId(IdWorker.getIdStr());
                author.setName(baijiaxing.getAuthor());
                author.setDynasty(baijiaxing.getTags());
                context.putAuthor(author);
                // 典籍解析
                Book book = new Book();
                book.setId(IdWorker.getIdStr());
                book.setName(baijiaxing.getTitle());
                book.setAuthorId(author.getId());
                book.setTag(ParserEnum.MENGXUE.getDesc());
                context.addBook(book);

                Article article = new Article();
                article.setId(IdWorker.getIdStr());
                article.setBookId(book.getId());
                article.setTitle(baijiaxing.getTitle());
                article.setAuthorId(author.getId());
                article.setContent(baijiaxing.getParagraphs());
                // 解析姓氏--->地域数据
                article.setNotes(baijiaxing.originConvert());
                context.addArticle(article);

            });
        }
    }

    /**
     * 弟子规
     *
     * @param context 解析上下文
     */
    private void parseDizigui(ParseContext context, String filePath) {
        String pattern = "^dizigui.json$";
        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).forEach(file -> {
                String data = FileUtils.read(file);
                Dizigui dizigui = JSON.parseObject(data, Dizigui.class);
                // 作者解析
                Author author = new Author();
                author.setId(IdWorker.getIdStr());
                author.setName(dizigui.getAuthor());
                context.putAuthor(author);
                // 典籍解析
                Book book = new Book();
                book.setId(IdWorker.getIdStr());
                book.setName(dizigui.getTitle());
                book.setAuthorId(author.getId());
                book.setTag(ParserEnum.MENGXUE.getDesc());
                context.addBook(book);
                dizigui.getContent().stream().parallel().forEach(e -> {
                    Article article = new Article();
                    article.setId(IdWorker.getIdStr());
                    article.setBookId(book.getId());
                    article.setTitle(e.getChapter());
                    article.setAuthorId(author.getId());
                    article.setContent(e.getParagraphs());
                    context.addArticle(article);
                });
            });
        }
    }

    /**
     * TODO 古文观止
     *
     * @param context 解析上下文
     */
    private void parseGuwenguanzhi(ParseContext context, String filePath) {

    }

    /**
     * TODO 千家诗
     *
     * @param context 解析上下文
     */
    private void parseQianjiashi(ParseContext context, String filePath) {

    }

    /**
     * 千字文
     *
     * @param context 解析上下文
     */
    private void parseQianziwen(ParseContext context, String filePath) {
        String pattern = "^qianziwen.json$";
        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).forEach(file -> {
                String data = FileUtils.read(file);
                Qianziwen qianziwen = JSON.parseObject(data, Qianziwen.class);
                // 作者解析
                Author author = new Author();
                author.setId(IdWorker.getIdStr());
                author.setName(qianziwen.getAuthor());
                author.setDynasty(qianziwen.getTags());
                context.putAuthor(author);
                // 典籍解析
                Book book = new Book();
                book.setId(IdWorker.getIdStr());
                book.setName(qianziwen.getTitle());
                book.setAuthorId(author.getId());
                book.setTag(ParserEnum.MENGXUE.getDesc());
                context.addBook(book);
                Article article = new Article();
                article.setId(IdWorker.getIdStr());
                article.setBookId(book.getId());
                article.setTitle(qianziwen.getTitle());
                article.setAuthorId(author.getId());
                article.setContent(qianziwen.getParagraphs());
                article.setNotes(qianziwen.getSpells());
                context.addArticle(article);
            });
        }
    }

    /**
     * 三字经（新版和传统版）
     *
     * @param context 解析上下文
     */
    private void parseSanzijing(ParseContext context, String filePath) {
        String patternNew = "^sanzijing-new.json$";
        // 作者解析
        Author author = new Author();
        author.setId(IdWorker.getIdStr());
        context.putAuthor(author);
        // 典籍解析
        Book book = new Book();
        book.setId(IdWorker.getIdStr());
        book.setAuthorId(author.getId());
        book.setTag(ParserEnum.MENGXUE.getDesc());
        context.addBook(book);

        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(patternNew, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).forEach(file -> {
                String data = FileUtils.read(file);
                Sanzijing sanzijing = JSON.parseObject(data, Sanzijing.class);
                author.setName(sanzijing.getAuthor());
                book.setName(sanzijing.getTitle());
                Article article = new Article();
                article.setId(IdWorker.getIdStr());
                article.setBookId(book.getId());
                article.setVolume(sanzijing.getTags());
                article.setVolumeSort(1);
                article.setTitle(sanzijing.getTitle());
                article.setAuthorId(author.getId());
                article.setContent(sanzijing.getParagraphs());
                context.addArticle(article);
            });
        }

        String patternTraditional = "^sanzijing-traditional.json$";
        files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(patternTraditional, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).forEach(file -> {
                String data = FileUtils.read(file);
                Sanzijing sanzijing = JSON.parseObject(data, Sanzijing.class);
                author.setName(sanzijing.getAuthor());
                book.setName(sanzijing.getTitle());
                Article article = new Article();
                article.setId(IdWorker.getIdStr());
                article.setBookId(book.getId());
                article.setVolume(sanzijing.getTags());
                article.setVolumeSort(1);
                article.setTitle(sanzijing.getTitle());
                article.setAuthorId(author.getId());
                article.setContent(sanzijing.getParagraphs());
                context.addArticle(article);
            });
        }
    }

    /**
     * 声律启蒙
     *
     * @param context 解析上下文
     */
    private void parseShenglvqimeng(ParseContext context, String filePath) {

    }

    /**
     * 唐诗三百首
     *
     * @param context 解析上下文
     */
    private void parseTangshisanbaishou(ParseContext context, String filePath) {

    }

    /**
     * 文字蒙求
     *
     * @param context 解析上下文
     */
    private void parseWenzimengqiu(ParseContext context, String filePath) {

    }

    /**
     * 幼学琼林
     *
     * @param context 解析上下文
     */
    private void parseYouxueqionglin(ParseContext context, String filePath) {

    }

    /**
     * 增广贤文
     *
     * @param context 解析上下文
     */
    private void parseZengguangxianwen(ParseContext context, String filePath) {

    }

    /**
     * 朱子家训
     *
     * @param context 解析上下文
     */
    private void parseZhuzijiaxun(ParseContext context, String filePath) {

    }

}
