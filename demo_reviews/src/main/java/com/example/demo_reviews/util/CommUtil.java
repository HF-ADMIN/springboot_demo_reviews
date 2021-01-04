package com.example.demo_reviews.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class CommUtil {
    /**
     * @methodName  fileToString
     * @return      String
     * @throws      Exception
     * @description File을 Base64로 인코딩하여 String형으로 변환하는 메소드
     */
    public static String fileToString(File file) throws Exception{
        String fileString = null;
        FileInputStream inputStream =  null;
        ByteArrayOutputStream byteOutStream = null;

        try {
            inputStream = new FileInputStream(file);
            byteOutStream = new ByteArrayOutputStream();

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) != -1) {
                byteOutStream.write(buf, 0, len);
            }
            Base64.Encoder encoder = Base64.getEncoder();

            byte[] fileArray = byteOutStream.toByteArray();
            fileString = new String(encoder.encode(fileArray));       
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            inputStream.close();
            byteOutStream.close();
        }

        return fileString;
    }

    public static String genUUID() {
        return String.valueOf(UUID.randomUUID());
    }

    public static Date getCurrentDate() throws Exception {
        return new Date();
    }
}
