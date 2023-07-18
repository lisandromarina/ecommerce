package com.ecommerce.service.impl;

import com.ecommerce.service.ImageService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageKitServiceImpl implements ImageService {
    @Autowired
    ImageKit imageKit;
    @Override
    public String uploadImage(byte[] imageBytes, String imageName) {
        try {
            FileCreateRequest fileCreateRequest = new FileCreateRequest(imageBytes, imageName);
           return imageKit.upload(fileCreateRequest).getUrl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
