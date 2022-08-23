package io.github.cocodx.utils;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author amazfit
 * @date 2022-08-23 下午11:09
 **/
public class RandomAccessFileTest {

    public static void main(String[] args)throws Exception {
        File file = new File("D:\\hello.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "r");

        //已经读取了三个字节
        int a = raf.read();
        System.out.println((char)a);
        raf.read();
        raf.read();


        //从文件的第6个字节开始
        raf.skipBytes(5);//相对位置 当前字符串。原文本读一下就少一下
//        raf.seek(5);//绝对位置

        byte[] bytes = new byte[1024 * 8];

        //读取文件的内容，一次读8kb
        int length = raf.read(bytes);

        System.out.println(new String(bytes,0,length));
    }
}
