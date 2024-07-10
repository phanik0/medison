package com.medison.pacs.dicom;

import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            Path filePath = Paths.get("Z:\\").resolve(decodedPath).normalize();
            if (Files.exists(filePath) && Files.isReadable(filePath)) {
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
    }
}
