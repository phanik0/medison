package com.medison.pacs.image.service;

import com.medison.pacs.image.domain.Image;
import com.medison.pacs.image.domain.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public List<Image> findByStudykey(long studykey){
        return imageRepository.findByStudykey(studykey);
    };
}
