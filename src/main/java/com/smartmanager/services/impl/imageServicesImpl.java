package com.smartmanager.services.impl;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.smartmanager.helpers.AppConstants;
import com.smartmanager.services.imageServices;


@Service
public class imageServicesImpl implements imageServices {


    private Cloudinary cloudinary;
    public imageServicesImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary; // Inject Cloudinary instance, could have used autowiring
    }
    

    String imageId=UUID.randomUUID().toString();

    @Override
    public String uploadImage(MultipartFile contactImage) {
        // Implement the logic to upload the image and return the image URL or path
        // For now, returning a placeholder string
        byte[] data;
        try {
            data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, Map.of(
                "public_id", imageId
                
            ));
             return this.getURLString(imageId);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
      

       
    }

    @Override
    public String getURLString(String imageId) {
        // Implement the logic to get the URL string for the image based on its ID
        // For now, returning a placeholder string
        return cloudinary.url()
               .transformation(
                new Transformation<>()
                    .width(AppConstants.CONTACT_IMAGE_WIDTH)
                    .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                    .crop("fill")
                    .gravity("face")
               )
               .generate(imageId);
    }

  

}
