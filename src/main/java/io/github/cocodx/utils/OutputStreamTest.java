package io.github.cocodx.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author amazfit
 * @date 2022-08-23 下午11:52
 **/
public class OutputStreamTest {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\a.txt");
        fileOutputStream.write(97);
        fileOutputStream.close();
    }
}
