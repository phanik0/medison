package com.medison.pacs.image.service;

import com.medison.pacs.image.domain.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;


}
