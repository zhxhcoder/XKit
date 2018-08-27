package com.zhxh.xlibkit.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FileUtil {

    /********************************遍历匹配的文件*********************************/

    /**
     * 输出顺序是从树最深层遍历 深度优先 逆序
     *
     * @param resultList 输出位置
     * @param isNest     是否嵌套
     * @param dirPath    目录位置
     * @param regRex     匹配的正则
     *                   能匹配的文件名
     */
    public static List<String> getMatchFiles(List<String> resultList, boolean isNest, String dirPath, String regRex) {
        if (resultList == null) {
            resultList = new ArrayList<>();
        }
        File fileDirectory = new File(dirPath);
        File[] fileList = fileDirectory.listFiles();
        if (fileList == null) {
            return resultList;
        }
        for (File file : fileList) {
            if (!file.isDirectory()) {
                String fileName = file.getName();
                if (null == regRex || regRex.length() == 0) {
                    resultList.add(fileName);
                } else {
                    Pattern pattern = Pattern.compile(regRex);
                    Matcher matcher = pattern.matcher(fileName);
                    if (matcher.find()) {
                        resultList.add(fileName);
                    }
                }
            } else {
                if (isNest) {
                    getMatchFiles(resultList, isNest, file.getAbsolutePath(), regRex);
                }
            }
        }
        return resultList;
    }

    public static List<String> getMatchFiles(String dirPath) {
        return getMatchFiles(dirPath, null);
    }

    public static List<String> getMatchFiles(String dirPath, String regRex) {
        return getMatchFiles(null, false, dirPath, regRex);
    }

    /********************************重命名匹配的文件*********************************/

    /**
     * 重命名匹配的文件
     *
     * @param isNest  是否嵌套
     * @param dirPath 目录位置
     * @param regRex  匹配的正则
     * @param replace 替换的字符串
     *                能匹配的文件名
     */
    public static void renameMatchFiles(boolean isNest, String dirPath, String regRex, String replace) {

        File fileDirectory = new File(dirPath);
        File[] fileList = fileDirectory.listFiles();
        if (fileList == null) {
            return;
        }
        for (File file : fileList) {
            if (!file.isDirectory()) {
                String fileName = file.getName();
                if (null == regRex || regRex.length() == 0) {
                    return;
                } else {
                    Pattern pattern = Pattern.compile(regRex);
                    Matcher matcher = pattern.matcher(fileName);
                    if (matcher.find()) {
                        if (file.renameTo(new File(file.getParent() + File.separator + fileName.replaceAll(regRex, replace)))) {
                            //重命名成功
                        }
                    }
                }
            } else {
                if (isNest) {
                    renameMatchFiles(true, file.getAbsolutePath(), regRex, replace);
                }
            }
        }
    }

    public static void renameMatchFiles(String dirPath, String regRex, String replace) {
        renameMatchFiles(false, dirPath, regRex, replace);
    }
    /********************************删除匹配的文件*********************************/

    /**
     * 删除匹配的文件
     *
     * @param isNest  是否嵌套
     * @param dirPath 目录位置
     * @param regRex  匹配的正则
     *                能匹配的文件名
     */
    public static void delMatchFiles(boolean isNest, String dirPath, String regRex) {

        File fileDirectory = new File(dirPath);
        File[] fileList = fileDirectory.listFiles();
        if (fileList == null) {
            return;
        }
        for (File file : fileList) {
            if (!file.isDirectory()) {
                String fileName = file.getName();
                if (null == regRex || regRex.length() == 0) {
                    return;
                } else {
                    Pattern pattern = Pattern.compile(regRex);
                    Matcher matcher = pattern.matcher(fileName);
                    if (matcher.find()) {
                        if (file.delete()) {
                            //删除成功
                        }
                    }
                }
            } else {
                if (isNest) {
                    delMatchFiles(true, file.getAbsolutePath(), regRex);
                }
            }
        }
    }

    public static void delMatchFiles(String dirPath, String regRex) {
        delMatchFiles(false, dirPath, regRex);
    }

    /********************************拷贝匹配的文件*********************************/
    /**
     * 拷贝匹配的文件
     *
     * @param isNest  是否嵌套
     * @param dirPath 目录位置
     * @param regRex  匹配的正则
     * @param destDir 拷贝的位置
     *                能匹配的文件名
     */
    public static void copyMatchFiles(boolean isNest, String dirPath, String regRex, String destDir) {

        File fileDirectory = new File(dirPath);
        File[] fileList = fileDirectory.listFiles();
        if (fileList == null) {
            return;
        }
        for (File file : fileList) {
            if (!file.isDirectory()) {
                String fileName = file.getName();
                if (null == regRex || regRex.length() == 0) {
                    return;
                } else {
                    Pattern pattern = Pattern.compile(regRex);
                    Matcher matcher = pattern.matcher(fileName);
                    if (matcher.find()) {
                        try {
                            copy(file, destDir);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (isNest) {
                    copyMatchFiles(true, file.getAbsolutePath(), regRex, destDir);
                }
            }
        }
    }

    /********************************移动匹配的文件*********************************/

    /**
     * 移动匹配的文件
     *
     * @param isNest  是否嵌套
     * @param dirPath 目录位置
     * @param regRex  匹配的正则
     * @param destDir 移动到的位置
     *                能匹配的文件名
     */
    public static void moveMatchFiles(boolean isNest, String dirPath, String regRex, String destDir) {

        File fileDirectory = new File(dirPath);
        File[] fileList = fileDirectory.listFiles();
        if (fileList == null) {
            return;
        }
        for (File file : fileList) {
            if (!file.isDirectory()) {
                String fileName = file.getName();
                if (null == regRex || regRex.length() == 0) {
                    return;
                } else {
                    Pattern pattern = Pattern.compile(regRex);
                    Matcher matcher = pattern.matcher(fileName);
                    if (matcher.find()) {
                        if (file.renameTo(new File(destDir + File.separator + fileName))) {
                            //重命名成功
                        }
                    }
                }
            } else {
                if (isNest) {
                    moveMatchFiles(true, file.getAbsolutePath(), regRex, destDir);
                }
            }
        }
    }


    /********************************基本文件操作*********************************/

    /**
     * 新建目录
     *
     * @param folderPath String  如  c:/fqf
     *                   boolean
     */
    public void newFolder(String folderPath) {
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    public void newFile(String filePathAndName, String fileContent) {

        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();  //取的路径及文件名
            File myFilePath = new File(filePath);
            /**如果文件不存在就建一个新文件*/
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter(myFilePath);  //用来写入字符文件的便捷类, 在给出 File 对象的情况下构造一个 FileWriter 对象
            PrintWriter myFile = new PrintWriter(resultFile);  //向文本输出流打印对象的格式化表示形式,使用指定文件创建不具有自动行刷新的新 PrintWriter。
            String strContent = fileContent;
            myFile.println(strContent);
            resultFile.close();

        } catch (Exception e) {
            System.out.println("新建文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 删除文件
     *
     * @param filePathAndName String  文件路径及名称  如c:/hello.txt
     *                        boolean
     */
    public void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myDelFile = new File(filePath);
            myDelFile.delete();

        } catch (Exception e) {
            System.out.println("删除文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 复制文件（夹）到一个目标文件夹
     *
     * @param resFile 源文件（夹）
     * @param destDir 目标文件夹
     * @throws IOException 异常时抛出
     */
    public static void copy(File resFile, String destDir) throws IOException {
        File destFolder = new File(destDir);

        System.out.println("copy " + resFile + "\n" + destFolder.getAbsolutePath());

        if (!resFile.exists()) return;
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
        if (resFile.isFile()) {
            File objFile = new File(destFolder.getPath() + File.separator + resFile.getName());
            //复制文件到目标地
            InputStream ins = new FileInputStream(resFile);
            FileOutputStream outs = new FileOutputStream(objFile);
            byte[] buffer = new byte[1024 * 512];
            int length;
            while ((length = ins.read(buffer)) != -1) {
                outs.write(buffer, 0, length);
            }
            ins.close();
            outs.flush();
            outs.close();
        } else {
            String objFolder = destFolder.getPath() + File.separator + resFile.getName();
            File _objFolderFile = new File(objFolder);
            _objFolderFile.mkdirs();
            for (File sf : resFile.listFiles()) {
                copy(sf, objFolder);
            }
        }
    }

}
