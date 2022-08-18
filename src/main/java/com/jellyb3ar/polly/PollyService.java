package com.jellyb3ar.polly;

import com.amazonaws.services.s3.AmazonS3;
import javazoom.jl.decoder.JavaLayerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PollyService {

    private final PollyDemo demo;
    private final CardRepository repository;
    private final AmazonS3 amazonS3;

    public void playFile(long num) throws IOException, JavaLayerException {
//        if(!repository.existsById(num)){
//            // make mp3 and send to S3
//
//        }

        // play mp3 file

        demo.saveMP3("test Test");
    }

//    public String upload(File uploadFile, String filePath, String saveFileName) {
//        String fileName = filePath + "/" + saveFileName;
//        amazonS3.putObject(new PutObjectRequest("ourney-s3", fileName, uploadFile)
//                .withCannedAcl(CannedAccessControlList.PublicRead)); // public 권한으로 설정
//
//        return fileName;
//    }
}
