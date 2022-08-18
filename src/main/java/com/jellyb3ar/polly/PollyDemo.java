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
    private static final String SAMPLE = "Congratulations. You have successfully built this working demo" +
            "of Amazon Polly in Java. Have fun building voice enabled apps with Amazon Polly (that's me!), and always" +
            "look at the AWS website for tips and tricks on using Amazon Polly and other great services from AWS";

    public PollyDemo() {
        PollyConfig config = new PollyConfig();
        polly = config.getPolly();
        voice = config.getVoice();
        polly.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
    }

    public InputStream synthesize(String text, OutputFormat format) throws IOException {
        SynthesizeSpeechRequest synthReq =
                new SynthesizeSpeechRequest().withText(text).withVoiceId(voice.getId())
                        .withOutputFormat(format).withEngine("neural");
        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

        return synthRes.getAudioStream();
    }

    public void synthesizeSpeech(String text) {
        String outputFileName = "/Users/yujin/IdeaProjects/speech.mp3";
        text = "Test A B C D E";
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

    public static void saveMP3(String text) {
        PollyDemo pollyFile = new PollyDemo();
//        InputStream speechStream = pollyFile.synthesize(text, OutputFormat.Mp3);
        pollyFile.synthesizeSpeech(text);

    }

    public static void playMP3(String text) throws IOException, JavaLayerException {
        PollyDemo pollyFile = new PollyDemo();
        InputStream speechStream = pollyFile.synthesize(text, OutputFormat.Mp3);

        //create an MP3 player
        AdvancedPlayer player = new AdvancedPlayer(speechStream,
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackStarted(PlaybackEvent evt) {
                System.out.println("Playback started");
                System.out.println(text);
            }

            @Override
            public void playbackFinished(PlaybackEvent evt) {
                System.out.println("Playback finished");
            }
        });

        player.play();
    }
}