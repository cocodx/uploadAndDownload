package io.github.cocodx.utils;

import java.io.*;
import java.util.ArrayList;

/**
 * @author amazfit
 * @date 2022-08-23 下午11:09
 **/
public class RandomAccessFileTest {

    public static void main1(String[] args)throws Exception {
        File file = new File("E:\\fuck\\vec-380.mp4");

        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream("G:\\11.mp4");

        byte[] bytes = new byte[1024 * 8];
        int len;
        long start = System.currentTimeMillis();
        while ((len = fileInputStream.read(bytes))!=-1){
            fileOutputStream.write(bytes,0,len);
        }
        fileInputStream.close();
        fileOutputStream.close();

        long end = System.currentTimeMillis();
        System.out.println((end-start)/1000);
    }

    public static void main(String[] args)throws Exception {
        File file = new File("E:\\fuck\\vec-380.mp4");
        File file1 = new File("G:\\11.mp4");
        //开启5个线程
        int threadNum = 5;

        long length = file.length();
        //向上取整
        int part = (int) Math.ceil(length / threadNum);
        //阻塞主线程，当子线程都走完了，再走主线程

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            final int k = i;
            Thread thread = new Thread(() -> {
                //线程具体做的事情
                try {
                    RandomAccessFile readFile = new RandomAccessFile(file, "r");
                    RandomAccessFile writeFile = new RandomAccessFile(file1, "rw");
                    //从指定位置读
                    readFile.seek(k * part);
                    writeFile.seek(k * part);

                    byte[] bytes = new byte[1024 * 8];
                    int len = -1, plen = 0;
                    while (true) {
                        len = readFile.read(bytes);
                        //读完了，退出循环
                        if (len == -1) {
                            break;
                        }
                        //如果不等于-1,则累加求和
                        plen = plen + len;

                        //将读取的数据进行写入
                        writeFile.write(bytes, 0, len);

                        if (plen >= part) {
                            break;
                        }
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            //把这5个线程保存到集合中
            threads.add(thread);
        }
        long begin = System.currentTimeMillis();
        for (Thread thread:threads){
            thread.join();//将线程加入，并阻塞主线程
        }

        long end = System.currentTimeMillis();
        System.out.println("拷贝消耗了，"+(end-begin)/1000);
    }
}
