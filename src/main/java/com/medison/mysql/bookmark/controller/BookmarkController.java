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

import java.util.List;

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
            return ResponseEntity.status(500).body("북마크 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/bookmark/list")
    public String getBookmarkList(Model model, @RequestParam String userId) {
        List<Bookmark> bookmarks = bookmarkService.getBookmarksByUserId(userId);
        model.addAttribute("bookmarks", bookmarks);
        return "bookmark";
    }
}
