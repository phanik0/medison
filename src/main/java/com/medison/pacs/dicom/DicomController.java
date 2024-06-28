package com.medison.pacs.dicom;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DicomController {

//    @GetMapping("/dicom-file")
//    public ResponseEntity<Resource> getDicomFile(@RequestParam String path) {
//        try {
//            path = path.replace("%2F", "/").replace("%5C", "/").replace("%3A","/");
//            System.out.println("path: " + path);
//            String basePath = "Z:/";
//            Resource file = new UrlResource("file:" + basePath + path);
//            if (file.exists() || file.isReadable()) {
//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
//                        .body(file);
//            } else {
//                throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다.");
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("파일을 읽는 중 오류가 발생했습니다.", e);
//        }
//    }
    @GetMapping("/dicom-file")
    public ResponseEntity<Resource> getDicomFile(@RequestParam String path) {
        try {
            Path filePath = Paths.get("Z:/").resolve(path).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println("file path: " + filePath);

            if (resource.exists() && resource.isReadable()) {
                System.out.println("File is readable");
                System.out.println("FileName : " + resource.getFilename());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException("파일을 읽는 중 오류가 발생했습니다.", e);
        }
    }
}
