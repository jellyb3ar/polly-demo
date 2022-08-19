package com.jellyb3ar.polly;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PollyService {
    private final PollyDemo demo;
    private final CardRepository repository;
    private final AmazonS3 amazonS3;
    private static String S3URL = "https://ourney-s3.s3.ap-northeast-2.amazonaws.com";
    private static String bucket = "ourney-s3";

    public List<PollyResponse> getList() {
        List<Card> cardList = repository.findAll();
        List<PollyResponse> responses = new LinkedList<>();
        for(Card c: cardList){
            responses.add(PollyResponse.builder()
                    .id(c.getId())
                    .cardText(c.getCartText())
                    .mp3Url(c.getMp3Url())
                    .imgUrl(c.getImgUrl())
                    .build());
        }
        return responses;
    }

    @Transactional
    public PollyResponse playFile(long num) {
        Card c = repository.findById(1L).orElseThrow(() -> new IllegalArgumentException());
        if(c.getMp3Url().equals("")){
            demo.saveMP3(c.getCartText(), num);
            upload(new File("/tmp/polly_" + num + ".mp3"), "mp3", "polly_"+num+".mp3");
            c.updateMp3Url(S3URL+"/mp3/polly_"+num+".mp3");
        }
        PollyResponse response = PollyResponse.builder()
                .id(c.getId())
                .cardText(c.getCartText())
                .mp3Url(c.getMp3Url())
                .imgUrl(c.getImgUrl())
                .build();
        return response;
//        playMp3(num);
    }

    public String upload(File uploadFile, String filePath, String saveFileName) {
        String fileName = filePath + "/" + saveFileName;
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead)); // public 권한으로 설정
        return fileName;
    }

    public void playMp3(long num) throws IOException, JavaLayerException {
        S3Object object = amazonS3.getObject(new GetObjectRequest(bucket, "mp3/polly_"+num+".mp3"));
        InputStream objectData = object.getObjectContent();
// Process the objectData stream.
        AdvancedPlayer player = new AdvancedPlayer(objectData,
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackStarted(PlaybackEvent evt) {
                System.out.println("Playback started");
            }

            @Override
            public void playbackFinished(PlaybackEvent evt) {
                System.out.println("Playback finished");
            }
        });
        player.play();
        objectData.close();
    }
}
