package com.jack.e_book.utils;

import android.util.Log;

import com.jack.e_book.entity.Book;
import com.jack.e_book.entity.Chapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设计此类的目的是便于统一管理，从资源文件中读取数据。 app中的所有数据来源都可以通过这个类来提供，这样在将来重用代码的时候，也方便修改。
 * 而且在各个类的逻辑任务上也清晰可见。 其目前所做的任务包括，初始化Book类，从资源文件中读取章节内容来初始化Chapter对象。
 *
 * @author MJZ
 */
public class IOHelper {

    private static Book book;
    // private static Chapter chapter;//章
    private static List<Chapter> list = new ArrayList<>();
    ; //所以章节的信息
    private static IOHelper ioHelper = new IOHelper();
    int temp = 0;

    public static IOHelper getInstence() {
        return ioHelper;
    }

    private StringBuilder stringBuilder;
    private Map<String, String> map;
    private String flag = "";
    private int maxLineNum = 0;

    private IOHelper() {
        stringBuilder = new StringBuilder();
        map = new HashMap<>();
        map.put("白话易经", "(^第[\\u4e00-\\u9fa5]{1,3}卦：)|(·白话$)");
        map.put("盗墓笔记", "^第[\\u4e00-\\u9fa5]{1,3}章");
        map.put("发个微信去三国","^第[\\u4e00-\\u9fa5]{1,5}章");
        map.put("漫宇世界中的修仙者","^第[\\u4e00-\\u9fa5]{1,5}章");
        map.put("随遇而安", "^第[\\u4e00-\\u9fa5]{1,3}章");
    }

    public int getMaxLineNum() {
        return maxLineNum;
    }

    /**
     * 初始化Book类的唯一对象。 这个函数一般只会调用一次。
     * 由于从文件中读取资源，则需要通过Activity 来提供。因此在Activity调用此函数的时候，会传入 this。
     *
     * @param bookName
     * @return
     */
    public  Book getBook(String bookName,File sourceFile, String encoding) throws IOException {
        book = Book.getInstance();
        book.setBookname(bookName);
        book.setAuthor(getAuthor(sourceFile,encoding));
        book.setBookurl(sourceFile);
        book.setChapterList(getAllChapter(sourceFile,encoding,getChapterRegex(bookName)));
        book.setMaxLineNum(getMaxLineNum());
        Log.d("test","--------"+list.size());
        return book;
    }
    public void setBook(Book book){
        IOHelper.book = book;
        IOHelper.list = book.getChapterList();
    }

    /**
     * 读取所有的章节信息，字符未优化。
     * <p/>
     * 要读取的章节的顺序。
     * 通过context来得到 Resources 对象，从而获取资源。
     *
     * @return
     */
    public  List<Chapter> getAllChapter(File sourceFile, String encoding, String regular) throws IOException {
        list.clear();//清空集合
        list.add(new Chapter("", 1, 0, "首页", "简叙"));
        if (encoding == null) {
            encoding = "utf-8";
        }
        FileInputStream in = new FileInputStream(sourceFile);
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(in, encoding));
        String line = reader.readLine();
       // Log.d("lines", "--------" + line);
        if (line != null) {
            reader.setLineNumber(1);
            int order = 0;
            while ((line = reader.readLine()) != null) {
                Pattern pattern = Pattern.compile(regular);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    order++;
                    Chapter chapter = toSaveChapter(line, order, flag,reader.getLineNumber());
                    list.add(chapter);
                    //Log.d("lines", "----" + reader.getLineNumber() + "----" + chapter.toString());
                }else if(!line.equals("")){
                    stringBuilder.append(line+"\n");
                }
            }
            maxLineNum = reader.getLineNumber();
            list.get(list.size()-1).setContent(stringBuilder.toString());
            stringBuilder.delete(0,stringBuilder.length());
            reader.close();
            in.close();
            return list;
        }
        return null;
    }
    private Chapter toSaveChapter(String line, int order, String flg,int currentLineNum) {
        if (flg.equals("白话易经")) {
            Chapter chapter = new Chapter();
            chapter.setTitleId(line.split("：|·")[0]);
            chapter.setLineNum(currentLineNum);
            chapter.setOrder(order);
            list.get(list.size()-1).setContent(stringBuilder.toString());
            stringBuilder.delete(0,stringBuilder.length());
            if (line.contains("》")) {
                chapter.setTitleName(line.split("：")[1].substring(0, line.split("：")[1].indexOf("》") + 1));
            } else {
                chapter.setTitleName(line.split("：|·")[1]);
            }
            return chapter;

        }else if(flg.equals("盗墓笔记")||flg.equals("发个微信去三国")||flg.equals("漫宇世界中的修仙者")||flg.equals("随遇而安")){
            Chapter chapter = new Chapter();
            chapter.setTitleId(line.split("章")[0]+"章");
            chapter.setTitleName(line.split("章")[1]);
            chapter.setLineNum(currentLineNum);
            chapter.setOrder(order);
            list.get(list.size()-1).setContent(stringBuilder.toString());
            stringBuilder.delete(0,stringBuilder.length());
            return chapter;
        }
        return null;
    }

    /**
     * 通过索引获取章节视图类
     *
     * @param index
     * @return
     */
    public Chapter getChapter(int index) {
        if (index<0||index>list.size()-1) {
            return null;
        }
        return list.get(index);
    }

    public String getAuthor(File sourceFile, String encoding) throws IOException {
        if (encoding == null) {
            encoding = "utf-8";
        }
        FileInputStream in = new FileInputStream(sourceFile);
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(in, encoding));
        String line = reader.readLine();
        if (line != null) {
            while ((line = reader.readLine()) != null) {
                Pattern pattern = Pattern.compile("作者：");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return line.split("：")[1];
                }
            }
        }
        return null;

    }

    /**
     * 通过书名返回相应的章节信息正则表达式规则
     * @param bookName
     * @return
     */
    private String getChapterRegex(String bookName) {
        if (map != null) {
            flag = bookName;
            return map.get(bookName);
        }
        return null;
    }

    /**
     * 通过指定行数读取数据
     * 此方法太消耗性能已过期
     * @return 返回字符串类型数据
     * @throws IOException
     */
    /*
    public String readLineToString(File sourceFile, String encoding, int startLineNumber, int endLineNumber) throws IOException {
        if (encoding==null){
            encoding="utf-8";
        }
        temp++;
        StringBuffer stringBuffer = new StringBuffer();
        FileInputStream in = new FileInputStream(sourceFile);
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(in, encoding));
        reader.setLineNumber(1);
        String line;
        //按行读文件，跳过行
        while ((line=reader.readLine())!=null&& reader.getLineNumber() <= endLineNumber)
        {
            if (!line.equals("")&&startLineNumber <= reader.getLineNumber()) {
                // Log.d("test", "--" + temp + "--" + reader.getLineNumber() + "--" + line);
                stringBuffer.append(line+"\n");
            }
        }
        reader.close();
        in.close();
        return stringBuffer.toString();
    }*/


    private String toUpperCaseNum(int d) {
        // 定义中国传统钱数数组
        String[] digit = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

        // 定义中国传统二维单位数组
        String[][] unit = {{"", "万", "亿", "万亿"}, {"", "十", "百", "千"}};

        // 判断钱数是否为负数，如果为负数，记录头字段
        String head = d > 0 ? "" : "负";

        String p = ""; // 定义字符串p用于存储转换后的字符拼接

        d = Math.abs(d); // 绝对值运算，对负数进行过滤

        // 外for循环，对万，亿的单位进行换算
        for (int x = 0; x < unit[0].length && d > 0; x++) {
            String s = "";
            // 内循环对个，十，百，千位进行转换
            for (int y = 0; y < unit[1].length; y++) {
                s = digit[(int) (d % 10)] + unit[1][y] + s;
                //s = digit[(int) (d % 10)] + unit[1][y] + s;
                d = d / 10;
            }
            // 正则替换，将结尾后面”零佰零拾...零"的字符替换为空，中间去零
            p = s.replaceAll("(零.)*零$", "") + unit[0][x] + p;
            //System.out.println(p);
        }

        // 各种情况的修正正则，内测暂且没出问题，如还有其他不合理的情况还需修正
        p = head + p.replaceFirst("(零.)+", "零");

        return p.replaceFirst("^零", "").replaceAll("亿万", "").replaceAll("(零.){2,}", "零").
                replaceAll("零(拾|佰|仟|万|亿)", "零").replaceAll("^负零", "负").replaceAll("^一十", "十");
    }

}
