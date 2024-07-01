package com.medison.mysql.bookmark.service;

import com.medison.mysql.bookmark.domain.Bookmark;
import com.medison.mysql.bookmark.domain.BookmarkRepository;
import com.medison.pacs.study.domain.Study;
import com.medison.pacs.study.domain.StudyRepository;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Book;
import java.util.List;
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

    public void deleteBookmark(int code) {
        bookmarkRepository.deleteById(code);
    }

    public List<Bookmark> getBookmarksByUserId(String userId) {
        return bookmarkRepository.findByUserId(userId);
    }

    public List<Study> getStudiesForBookmarksByUserId(String userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);
        return bookmarks.stream()
                .map(bookmark -> studyService.getStudyByStudyKey(bookmark.getStudykey()))
                .collect(Collectors.toList());
    }

}
