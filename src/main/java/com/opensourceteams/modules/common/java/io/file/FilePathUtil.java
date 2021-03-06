package com.opensourceteams.modules.common.java.io.file;

import java.io.File;
import java.util.List;


/**
 * 开发者:刘文  Email:372065525@qq.com
 * 16/3/11  上午6:00
 * 功能描述:
 * ).创建多级目录
 * ).创建多级目录下的文件
 */

public class FilePathUtil {


    /**
     * 创建多级目录,如果目录存在就不创建
     * @param path 需要创建目录的路径
     */
    public static void mkdir(String path) {
        File fd = null;
        try {
            fd = new File(path);
            if (!fd.exists()) {
                fd.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fd = null;
        }
    }


    /**
     * 创建文件
     * @param path
     * @throws Exception
     */
    public static File  createNewFile(String path){
        if (path == null || "".equals(path)) {
            return null;
        }
        File f = null;
        try {
            // 获得文件对象
             f = new File(path);
            if (f.exists()) {
                return f;
            }

            //如果是相对路径
            if(f.getParentFile() == null){
                f.createNewFile();
            }else{
                // 如果路径不存在,则创建
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }
                f.createNewFile();
            }

        } catch (Exception e) {
            //log.error("创建文件错误.path=" + path, e);
            e.printStackTrace();
        }
        return  f;
    }



    /**
     * 递归查找子文件路径
     * @param list
     * @param f
     */
    public static void subRecursionListFiles(List<String> list, File f ){
        if(f!= null ){
            if(f.isDirectory()){
                for (File subF : f.listFiles()){
                    list.add(subF.getAbsolutePath());

                    if(subF.isDirectory()){
                        subRecursionListFiles(list,subF);
                    }
                }
            }else if(f.isFile()){
                list.add(f.getAbsolutePath());
            }


        }

    }


    /**
     * 递归查找子文件路径
     * @param list
     * @param f
     */
    public static void subRecursionListFilesSort(List<String> list, File f ){
        subRecursionListFiles(list,f);

        /**
         * 对结果进行排序
         */
/*        list.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
                if(o1 == null && o2 == null){
                    return 0 ;
                }else if(o1 == null){
                    return -1 ;
                }else if(o2 == null){
                    return 1 ;
                }else{
                    return o1.compareTo(o2);
                }
            }
        });*/

    }

    public static boolean deleteFile(String path){
        File file = new File(path);
        if(file != null && file.exists()){
            file.delete();
        }

        return  false;
    }

    /**
     * 判断文件是否存在
     * @param path
     * @return
     */
    public static boolean exist(String path){
        File file = new File(path);
        if(file != null && file.exists() ){
           return true;
        }

        return  false;
    }
}
