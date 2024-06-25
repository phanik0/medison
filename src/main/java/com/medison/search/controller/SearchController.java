package com.medison.search.controller;

import com.medison.detail.domain.Study;
import com.medison.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class SearchController {
    private final SearchService searchService;

    // 모든 스터디 목록을 가져와 HTML 페이지를 반환하는 메소드
    @GetMapping("/main")
    public String getAllStudies(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Study> studyPage = searchService.findAllWithPage(pageable);
        model.addAttribute("studies", studyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studyPage.getTotalPages());
        return "main"; // src/main/resources/templates/main.html 파일을 렌더링
    }

    // 특정 페이지의 스터디 목록을 가져와 HTML 페이지를 반환하는 메소드
    @GetMapping("/main/{page}")
    public String getAllStudies(@PathVariable int page, @PageableDefault (size = 5) Pageable pageable, Model model) {
        Pageable requestedPage = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());
        Page<Study> studyPage = searchService.findAllWithPage(requestedPage);
        model.addAttribute("studies", studyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studyPage.getTotalPages());
        return "main"; // src/main/resources/templates/main.html 파일을 렌더링
    }
}
