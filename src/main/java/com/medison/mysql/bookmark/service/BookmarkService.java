package com.medison.mysql.bookmark.service;

import com.medison.mysql.bookmark.domain.Bookmark;
import com.medison.mysql.bookmark.domain.BookmarkRepository;
import com.medison.pacs.study.domain.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final StudyRepository studyRepository;

    public void saveBookmark(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    public void deleteBookmark(int code) {
        bookmarkRepository.deleteById(code);
    }

    public List<Bookmark> getBookmarksByUserId(String userId) {
        return bookmarkRepository.findByUserId(userId);
    }
}
