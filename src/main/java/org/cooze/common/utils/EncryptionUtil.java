package org.cooze.common.utils;

import java.io.*;
import java.security.MessageDigest;
import java.util.Enumeration;

/**
 * @author coocze
 * @version 1.0.0
 * @desc
 * @date 2017/3/22
 */
public class EncryptionUtil {
    private EncryptionUtil(){}

    private String MD5 = "MD5";
    private String SHA1 = "SHA1";

    /**
     *
     * @param contents
     * @return
     * @throws Exception
     */
    public static String getMd5(String contents,String encrType)throws Exception{
        StringBuffer encry = new StringBuffer();
        MessageDigest md5 =  MessageDigest.getInstance(encrType);
        byte[] resultBytes = md5.digest(contents.getBytes());
        for(int i = 0;i<resultBytes.length;i++){
            encry.append(Integer.toHexString(
                    (0x000000ff & resultBytes[i])
                            | 0xffffff00).substring(6));
        }
        return encry.toString();
    }


    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String getMd5(File file ,String encrType) throws Exception{
            return getMd5(new FileInputStream(file),encrType);
    }


    /**
     *
     * @param in
     * @return
     */
    public static String getMd5(InputStream in,String encrType){
        StringBuffer encry = new StringBuffer();

        try{
            MessageDigest messageDigest = MessageDigest.getInstance(encrType);
            byte[] dataBytes = new byte[1024];

            int len = 0;
            while ((len = in.read(dataBytes)) != -1) {
                messageDigest.update(dataBytes, 0, len);
            }
            byte[] mdbytes = messageDigest.digest();

            for (int i = 0; i < mdbytes.length; i++) {
                encry.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        }catch (Exception e){
            try {
                if(in!=null)in.close();
            } catch (IOException e1) {
            }
        }
        return encry.toString();
    }

}
