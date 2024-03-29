package com.github.yumyum.chat.repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class testMain {
    public static void main(String[] args) {
        // 이미지 파일 경로 설정
        String filePath = "C:\\Users\\song\\Pictures\\logo\\dog1.jpg";

        try {
            // 이미지 파일을 읽기 위한 FileInputStream 생성
            FileInputStream fis = new FileInputStream(new File(filePath));

            // 이미지 데이터를 저장하기 위한 ByteArrayOutputStream 생성
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            // 이미지 데이터를 읽어와서 ByteArrayOutputStream에 쓰기
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buf)) != -1) {
                bos.write(buf, 0, bytesRead);
            }

            // ByteArrayOutputStream에서 byte 배열로 변환
            byte[] imageBytes = bos.toByteArray();
            System.out.println("imageBytes = " + imageBytes);

            // 사용이 끝난 자원 해제
            fis.close();
            bos.close();

            // 변환된 byte 배열 사용
            // 예: 이미지를 서버로 전송하거나 다른 용도로 사용
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
