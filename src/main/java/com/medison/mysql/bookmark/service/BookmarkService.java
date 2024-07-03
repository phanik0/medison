package com.medison.mysql.bookmark.service;

import com.medison.mysql.bookmark.domain.Bookmark;
import com.medison.mysql.bookmark.domain.BookmarkRepository;
import com.medison.pacs.study.domain.Study;
import com.medison.pacs.study.domain.StudyRepository;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final StudyRepository studyRepository;
    private final StudyService studyService;

    public void saveBookmark(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    public void deleteBookmark(int code) throws Exception {
        Optional<Bookmark> bookmark = bookmarkRepository.findById(code);
        if (bookmark.isPresent()) {
            bookmarkRepository.deleteById(code);
        } else {
            throw new Exception("No Bookmark entity with id " + code + " exists!");
        }
    }

    public List<Bookmark> getBookmarksByUserId(String userId) {
        return bookmarkRepository.findByUserId(userId);
    }

    public Page<Bookmark> getBookmarksByUserId(String userId, Pageable pageable) {
        return bookmarkRepository.findByUserId(userId, pageable);
    }
}