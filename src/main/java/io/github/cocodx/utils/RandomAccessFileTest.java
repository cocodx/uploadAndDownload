package io.github.cocodx.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

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

        //定义一个基于多线程的hashMap
        final Map<Integer,Integer> map = new ConcurrentHashMap<>();

        //读取日志文件中的数据
        File fl = new File("G:/xxx.mp4.log");
        String[] $data = null;
        if (fl.exists()){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fl));
            String datas = bufferedReader.readLine();
            //拆分字符串
            $data = datas.split(",");
            bufferedReader.close();
        }
        final String[] _data = $data;

        for (int i = 0; i < threadNum; i++) {
            final int k = i;
            Thread thread = new Thread(() -> {
                //线程具体做的事情
                RandomAccessFile log = null;
                try {
                    RandomAccessFile readFile = new RandomAccessFile(file, "r");
                    RandomAccessFile writeFile = new RandomAccessFile(file1, "rw");
                    log = new RandomAccessFile("G:/xxx.mp4.log", "rw");
                    //从指定位置读
                    readFile.seek(_data==null ? k * part : Integer.parseInt(_data[k]));
                    writeFile.seek(_data==null ? k * part : Integer.parseInt(_data[k]));

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

                        //将读取的字节数放入map中
                        map.put(k,plen+(_data==null ? k * part : Integer.parseInt(_data[k])));

                        //将读取的数据进行写入
                        writeFile.write(bytes, 0, len);

                        //将map中数据进行写入磁盘上，然后下一次也不知道对应的线程信息
                        log.seek(0);
                        //把每个线程读的数量，扔进去就行
                        StringJoiner joiner = new StringJoiner(",");
                        map.forEach((key,v)->{
                            joiner.add(String.valueOf(v));
                        });
                        log.write(joiner.toString().getBytes(StandardCharsets.UTF_8));

                        //如果当前线程读取，大于等于，下一个线程的起始位置就不用读了
                        if (plen + (_data==null ? k * part : Integer.parseInt(_data[k])) >= (k+1)*part) {
                            break;
                        }
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }finally {
                    try {
                        if (log!=null) {
                            log.close();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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

        //读取完成后，将日志文件删除即可
        new File("G:/xxx.mp4.log").delete();

        long end = System.currentTimeMillis();
        System.out.println("拷贝消耗了，"+(end-begin)/1000);
    }
}
