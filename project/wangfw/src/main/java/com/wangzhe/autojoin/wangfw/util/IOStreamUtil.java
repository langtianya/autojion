package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author wzw
 */
public class IOStreamUtil {

    private static final Logger log = Logger.getLogger(IOStreamUtil.class.getName());

    public static final InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static final byte[] input2byte(InputStream inStream) {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc = 0;
        try {
            while ((rc = inStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    /**
     * 从输入流中获取数�?
     *
     * @param inStream 输入�?
     * @return 描述
     */
    public static byte[] readStreamToByte(InputStream inStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
        return outStream.toByteArray();
    }

    /**
     * 从输入流中获取数�?
     *
     * @param inStream 输入�?
     * @return 描述
     */
    public static String readStreamToString(InputStream inStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
        return outStream.toString();
    }

    /**
     * 将输入流转化成某字符编码的String
     *
     * @param inStream 输入�?
     * @param encoding 编码
     * @return 描述
     */
    public static String readStreamToString(InputStream inStream, String encoding) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        String str = "";
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            str = new String(outStream.toByteArray(), encoding);
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
        return str;
    }

    /* 
     *  实现字节数组向十六进制的转换方法�? 
     */
    public static String byte2HexStr(byte[] b) {
        if (b == null) {
            return null;
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /* 
     *实现字节数组向十六进制的转换的方法二 
     */
    public static String bytesToHexString(byte[] src) {

        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();

    }

    /**
     * 十六进制转成图片
     *
     * @param src 描述
     * @param output 描述
     */
    public void saveToImgFile(String src, String output) {
        if (src == null || src.length() == 0) {
            return;
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(output));
            byte[] bytes = src.getBytes();
            for (int i = 0; i < bytes.length; i += 2) {
                out.write(charToInt(bytes[i]) * 16 + charToInt(bytes[i + 1]));
            }
            out.close();
        } catch (Exception e) {
            log.error(e);
        }
    }

    private int charToInt(byte ch) {
        int val = 0;
        if (ch >= 0x30 && ch <= 0x39) {
            val = ch - 0x30;
        } else if (ch >= 0x41 && ch <= 0x46) {
            val = ch - 0x41 + 10;
        }
        return val;
    }

    public static byte[] ToByte(String url) {
        byte[] b = null;
        try {
            b = IOUtils.toByteArray((new URL(url)).openStream());
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            log.error(ex);
        }
        return b;
    }

    public static byte[] toByte(String url) {
        File f = new File(url);
        if (!f.exists()) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = fis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            //得到图片的字节数�?  
            return bos.toByteArray();
        } catch (IOException ex) {
            log.error(ex);
        }
        return null;
    }

  

    public static String readTxtFile(File file, String encoding) {
        StringBuilder sb = new StringBuilder();
        try {
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格�?
                BufferedReader bufferedReader = new BufferedReader(read);
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) {
                    sb.append(temp).append("\n");
                }
//                if (sb.length() > 0) {
//                    sb.delete(sb.length() - 2, sb.length());
//                }
                read.close();
                bufferedReader.close();
                read.close();
            } else {
                log.error("找不到指定的文件");
                return null;
            }
        } catch (IOException ex) {
            log.error(ex);
        }
        return sb.toString();
    }

 
    public static List<String> readTxtFileList(String filePath, String encoding) {

        List<String> list = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = null;
                if (encoding.equalsIgnoreCase("utf-8")) {
                    read = getUtf8Charset(file);//考虑到编码格�?
                } else {
                    read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格�?
                }
                BufferedReader bufferedReader = new BufferedReader(read);
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) {
                    list.add(temp);
                }
                read.close();
                bufferedReader.close();
            } else {
                log.error("找不到指定的文件");
                return null;
            }
        } catch (IOException ex) {
            log.error(ex);
        }
        return list;
    }

    public static InputStreamReader getUtf8Charset(File file) {
        String charset = "utf-8";
        int BOM_SIZE = 4;
        InputStreamReader inputStreamReader = null;
        try {
            PushbackInputStream internalIn = new PushbackInputStream(new FileInputStream(file), BOM_SIZE);

            byte bom[] = new byte[BOM_SIZE];
            int n, unread;
            n = internalIn.read(bom, 0, bom.length);

            if ((bom[0] == (byte) 0x00) && (bom[1] == (byte) 0x00)
                    && (bom[2] == (byte) 0xFE) && (bom[3] == (byte) 0xFF)) {
                charset = "UTF-32BE";
                unread = n - 4;
            } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)
                    && (bom[2] == (byte) 0x00) && (bom[3] == (byte) 0x00)) {
                charset = "UTF-32LE";
                unread = n - 4;
            } else if ((bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB)
                    && (bom[2] == (byte) 0xBF)) {
                charset = "UTF-8";
                unread = n - 3;
            } else if ((bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF)) {
                charset = "UTF-16BE";
                unread = n - 2;
            } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)) {
                charset = "UTF-16LE";
                unread = n - 2;
            } else {
                // Unicode BOM mark not found, unread all bytes  
                unread = n;
            }
            if (unread > 0) {
                internalIn.unread(bom, (n - unread), unread);
            }
            // Use given encoding  
            if (charset != null) {
                inputStreamReader = new InputStreamReader(internalIn, charset);
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return inputStreamReader;
    }

    public static boolean saveTxtFile(String fileUrl, String body) {
        return saveTxtFile(new File(fileUrl), body);
    }

    public static boolean saveTxtFile(File file, String body) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file, "utf-8");
            pw.append(body);
            pw.flush();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            log.error(ex);
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return true;
    }

   

    public static List<String> getImportList(File openFile) {
        if (openFile != null) {
            FileInputStream fis = null;
            InputStreamReader input = null;
            BufferedReader br = null;
            try {
                fis = new FileInputStream(openFile);
                input = new InputStreamReader(fis, "utf-8");
                br = new BufferedReader(input);
                String value = "";
                List<String> list = new ArrayList<>();//放所有导入的网址
                while ((value = br.readLine()) != null) {
                    if (value.isEmpty()) {
                        continue;
                    }
                    if (!value.contains("&nbsp")) {
                        list.add(value);
                    }
                }
                return list;
            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                log.error(ex);
            } catch (IOException ex) {
                log.error(ex);
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException ex) {
                    log.error(ex);
                }
            }
        }
        return null;
    }

    public static void saveImage(byte[] buf, String outURL) {
        try {
            InputStream inputStream = byte2Input(buf);
            OutputStream out = new FileOutputStream(new File(outURL));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            inputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static File getFile(String fileUrl) {
        File saveFile = new File(fileUrl);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        return saveFile;
    }

    public static File getNewFileName(File file, String fileName) {
        return getNewFileName(file.getPath(), fileName);
    }

    /**
     * 获取文件夹中不重复的命名
     *
     * @param url
     * @param filename 如abc.txt
     * @return
     */
    public static File getNewFileName(String url, String filename) {
        String Suffix = getFileSuffix(filename);
        filename = getFileName(filename);
        return getNewFileName(url, filename, Suffix);
    }

    /**
     * 如果已有相同文件名，则一直命名取到不同的文件�?
     *
     * @param url
     * @param filename �? abc
     * @param Suffix �? .txt
     * @return
     */
    public static File getNewFileName(String url, String filename, String Suffix) {
        int i = 0;
        File outFile = null;
        while (true) {
            if (i == 0) {
                outFile = new File(url + "/" + filename + Suffix);
            } else {
                outFile = new File(url + "/" + filename + "(" + i + ")" + Suffix);
            }
            if (outFile.exists()) {
                i++;
            } else {
                break;
            }
        }
        return outFile;
    }

    public static File getNewDirectoryName(String url, String filename) {
        int i = 0;
        File outFile = null;
        while (true) {
            if (i == 0) {
                outFile = new File(url + "/" + filename);
            } else {
                outFile = new File(url + "/" + filename + "(" + i + ")");
            }
            if (outFile.isDirectory()) {
                i++;
            } else {
                break;
            }
        }
        return outFile;
    }

    /**
     * 获取除后�?的名�?
     *
     * @return
     */
    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 获取后缀�?
     *
     * @param fileName
     * @return �?.txt
     */
    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String gbkToBig5(String gbk) {

        return null;
    }

}
