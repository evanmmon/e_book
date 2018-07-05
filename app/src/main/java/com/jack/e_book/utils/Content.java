package com.jack.e_book.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;

/**这是一个用户存储大文件的存储类
 * Created by Administrator on 2016/11/5.
 */
public class Content {
    private static Content content = new Content();

    private Content() {
    }

    public static Content getContent() {
        return content;
    }

    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }




    /**
     * 读的方法
     *
     * @param path      文件路径
     * @param startLine 开始读取的行数
     * @param endLine   结束时的行数
     **/
    public void randomRed(String path, int startLine, int endLine, String charset) {
        if (charset == null)
            charset = "utf-8";
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "r");
            //获取RandomAccessFile对象文件指针的位置，初始位置是0
            System.out.println("RandomAccessFile文件指针的初始位置:" + raf.getFilePointer());
            raf.seek(startLine);//移动文件指针位置
            byte[] buff = new byte[1024];
            //用于保存实际读取的字节数
            int hasRead = 0;
            //循环读取
            while ((hasRead += raf.read(buff)) <=endLine) {
                //打印读取的内容,并将字节转为字符串输入
                System.out.println(new String(buff, 0, hasRead, charset));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] lineNum2byteNum(File file, String charset, int startLine, int endLine) throws IOException {
        if (charset == null) charset = "utf-8";
        int[] nums = new int[2];
        StringBuffer stringBuffer = new StringBuffer();
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), charset));
        reader.setLineNumber(1);
        String line;
        //按行读文件，跳过行
        while ((line = reader.readLine()) != null && reader.getLineNumber() <= endLine) {
            stringBuffer.append(line+"\n");
            if ( startLine == reader.getLineNumber()) {
                // Log.d("test", "--" + temp + "--" + reader.getLineNumber() + "--" + line);
                nums[0] = stringBuffer.toString().getBytes("utf-8").length;
            }
        }
        nums[1] = stringBuffer.toString().getBytes("utf-8").length;
        reader.close();
        return nums;
    }


}
