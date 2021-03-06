package com.opensourceteams.modules.product.项目.n_03_多线程下载工具.n_01_多线程下载工具;

import com.opensourceteams.modules.common.java.algorithm.bean.DownloadBytesBean;
import com.opensourceteams.modules.common.java.util.net.URLUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URLConnection;
import java.util.Vector;

/**
 * Created by hadoop on 16/3/24.
 */
public class Download_URLUtil extends URLUtil {


    public static boolean globalIsSuspend = false;

    public static boolean globalIsStop = false;


    public static byte[] getBytesIsSuspend( DownloadBytesBean downloadBytesBean) throws Exception {

        URLConnection urlConnection = openConnection(downloadBytesBean.getUrlStr());
        if(urlConnection == null){
            return  null;
        }else{
            urlConnection.setRequestProperty("Range","bytes="+ downloadBytesBean.getBeginIndex()+"-" + (downloadBytesBean.getEndIndex() -1));
            if(urlConnection.getContentLength() <= 0){
                return  null;
            }
        }


        Vector<String> vector = new Vector<String>();
        vector.add("application/octet-stream");
        vector.add("application/zip");
        vector.add("binary/octet-stream");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(true){
            //支持直接读写的方式,其它压缩处理的文件,需要相应的解压处理,不然,下载后用不了

            byte[] buffer = new byte[1024];
            try {
                InputStream is = urlConnection.getInputStream();
                int len = 0 ;
                while ((len = is.read(buffer)) != -1){
                    while (globalIsSuspend){
                        System.out.println(Thread.currentThread().getName() + "暂停");
                        Thread.sleep(1000);

                    }

                    while (globalIsStop){
                        System.out.println(Thread.currentThread().getName() +"停止");
                        return null;
                    }

                    baos.write(buffer,0,len);
                    //记录当前累记的下载量
                    downloadBytesBean.setAmount(downloadBytesBean.getAmount() + len);


                }


            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(baos != null){
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            throw new Exception("该文件类型,不支持" + urlConnection.getContentType());
        }


        return baos.toByteArray();
    }

    public static boolean download( DownloadBytesBean downloadBytesBean) throws Exception {

        URLConnection urlConnection = openConnection(downloadBytesBean.getUrlStr());
        InputStream is = null;
        if(urlConnection == null){
            return  false;
        }else{
            urlConnection.setRequestProperty("Range","bytes="+ downloadBytesBean.getBeginIndex()+"-" + (downloadBytesBean.getEndIndex() -1));
            if(urlConnection.getContentLength() <= 0){
                return  false;
            }
        }

        RandomAccessFile raf = new RandomAccessFile(downloadBytesBean.getSaveFilePath(),"rw");
        raf.seek(downloadBytesBean.getBeginIndex() + downloadBytesBean.getAmount());

        Vector<String> vector = new Vector<String>();
        vector.add("application/octet-stream");
        vector.add("application/zip");
        vector.add("binary/octet-stream");



        if(true){
            //支持直接读写的方式,其它压缩处理的文件,需要相应的解压处理,不然,下载后用不了

            byte[] buffer = new byte[1024];
            try {
                is = urlConnection.getInputStream();
                int len = 0 ;
                while ((len = is.read(buffer)) != -1){
                    while (globalIsSuspend){
                        System.out.println(Thread.currentThread().getName() + "暂停");
                        Thread.sleep(1000);

                    }

                    while (globalIsStop){
                        System.out.println(Thread.currentThread().getName() +"停止");
                        return false;
                    }

                    raf.write(buffer,0,len);
                    //记录当前累记的下载量
                    downloadBytesBean.setAmount(downloadBytesBean.getAmount() + len);


                }


            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(raf != null){
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(is != null){
                    is.close();
                }
            }
        }else{
            throw new Exception("该文件类型,不支持" + urlConnection.getContentType());
        }


        return true;
    }

}
