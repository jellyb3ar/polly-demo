package com.jellyb3ar.polly;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.*;

import com.jellyb3ar.polly.config.PollyConfig;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.springframework.stereotype.Component;

@Component
public class PollyDemo {

    private final AmazonPollyClient polly;
    private final Voice voice;

    public PollyDemo() {
        PollyConfig config = new PollyConfig();
        polly = config.getPolly();
        voice = config.getVoice();
        polly.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
    }

    public void synthesizeSpeech(String text, long num) {
        String outputFileName = "/tmp/polly_"+num+".mp3";
        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
                .withOutputFormat(OutputFormat.Mp3)
                .withVoiceId(VoiceId.Joanna)
                .withText(text)
                .withEngine("neural");

        try (FileOutputStream outputStream = new FileOutputStream(new File(outputFileName))) {
            SynthesizeSpeechResult synthesizeSpeechResult = polly.synthesizeSpeech(synthesizeSpeechRequest);
            byte[] buffer = new byte[2 * 1024];
            int readBytes;

            try (InputStream in = synthesizeSpeechResult.getAudioStream()){
                while ((readBytes = in.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readBytes);
                }
            }
        } catch (Exception e) {
            System.err.println("Exception caught: " + e);
        }
    }

    public static void saveMP3(String text, long num) {
        PollyDemo pollyFile = new PollyDemo();
        pollyFile.synthesizeSpeech(text, num);
        System.out.println("Save Finished");
    }
}