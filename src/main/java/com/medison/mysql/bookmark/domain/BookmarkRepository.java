package com.medison.mysql.bookmark.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    List<Bookmark> findByUserId(String userId);
    Page<Bookmark> findByUserId(String userId, Pageable pageable);
    Bookmark findByUserIdAndStudykey(String userId, Long studykey);
    Bookmark findByUserIdAndStudykey(String userId, int studykey);

}
