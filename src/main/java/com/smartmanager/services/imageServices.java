package com.smartmanager.services;


import org.springframework.web.multipart.MultipartFile;


public interface imageServices {

    String uploadImage(MultipartFile contactImage);
    String getURLString(String imageId);
}
