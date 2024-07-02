package com.medison.mysql.bookmark.controller;

import com.medison.mysql.bookmark.domain.Bookmark;
import com.medison.mysql.bookmark.service.BookmarkService;
import com.medison.pacs.study.domain.Study;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final StudyService studyService;

    @PostMapping("/bookmark/save")
    @ResponseBody
    public ResponseEntity<String> saveBookmark(@RequestBody Bookmark bookmark) {
        try {
            bookmarkService.saveBookmark(bookmark);
            return ResponseEntity.ok("북마크가 저장되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("북마크 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @DeleteMapping("/bookmark/delete/{code}")
    @ResponseBody
    public ResponseEntity<String> deleteBookmark(@PathVariable int code) {
        try {
            bookmarkService.deleteBookmark(code);
            return ResponseEntity.ok("북마크가 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("북마크 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/bookmark/list")
    @ResponseBody
    public ResponseEntity<List<Bookmark>> listBookmarks(@RequestParam String userId) {
        try {
            List<Bookmark> bookmarks = bookmarkService.getBookmarksByUserId(userId);
            return ResponseEntity.ok(bookmarks);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/bookmark/myList")
    public String myListBookmarks(@RequestParam String userId, Model model) {
        List<Bookmark> bookmarks = bookmarkService.getBookmarksByUserId(userId);
        List<Study> allStudies = studyService.getAllStudies();

        // Study 데이터를 studykey를 기준으로 Map에 저장
        Map<Long, Study> studyMap = allStudies.stream()
                .collect(Collectors.toMap(Study::getStudykey, study -> study));

        // Bookmark와 Study 데이터를 조합하여 List에 저장
        List<Map<String, Object>> bookmarkWithStudies = bookmarks.stream().map(bookmark -> {
            Map<String, Object> map = new HashMap<>();
            map.put("bookmark", bookmark);
            map.put("study", studyMap.get((long)bookmark.getStudykey()));
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("bookmarkWithStudies", bookmarkWithStudies);
        model.addAttribute("userId", userId);
        return "bookmark";
    }
}
