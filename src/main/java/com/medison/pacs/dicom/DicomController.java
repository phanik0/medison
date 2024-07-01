package com.medison.pacs.dicom;

import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DicomController {



    @GetMapping("/dicom-file")
    public ResponseEntity<Resource> getDicomFile(@RequestParam String path) {
        try {
            String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8.name());
            // 실제 파일 시스템 경로로 변환
            Path filePath = Paths.get("Z:\\").resolve(decodedPath).normalize();

            // 파일이 존재하고 읽을 수 있는지 확인
            if (Files.exists(filePath) && Files.isReadable(filePath)) {
                // 파일을 바이트 배열로 읽기
                byte[] fileBytes = Files.readAllBytes(filePath);
                ByteArrayResource resource = new ByteArrayResource(fileBytes);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName().toString() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException("파일을 읽는 중 오류가 발생했습니다.", e);
        }
//        try {
//            Path filePath = Paths.get("Z:\\").resolve(path).normalize();
//            InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath.toFile()));
//            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
//
//            while (br.ready()) {
//                System.out.println(br.readLine());
//
//            }
//            System.out.println("file path: " + filePath);
//            //byte 를 들고와야함
//
//            if (resource.exists() && resource.isReadable()) {
//                System.out.println("File is readable");
//                System.out.println("FileName : " + resource.getFilename());
//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//            } else {
//                throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다.");
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("파일을 읽는 중 오류가 발생했습니다.", e);
//        }


    }
}
