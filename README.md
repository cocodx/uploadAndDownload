# uploadAndDownload
上传与下载

#### 断点续传

RandomAccessFile 即可以当输入流，又可以当输出流，还可以从指定的位置来读文件，从指定的位置来写位置（从任意位置读写）。

一般是从头读到尾，从头写到尾

创建RandomAccessFile的model模式是

* r 读
* rw 读写
* rwd 读写，类似缓冲流
* rws 每write修改一个byte都会直接修改在磁盘中。

```java
//skipBytes(6)
//跳过6个字节，从第七个字节开始读取。相对读取【相对当前的读取文件，比如说hello world，读了he，这时候是从r开始去读】
//seek(6) 而seek是从w开始读取，绝对读取，一直是在原始文件的标准去读



```

#### 分片上传

