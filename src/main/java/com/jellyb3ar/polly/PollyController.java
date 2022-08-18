package com.jellyb3ar.polly;

import javazoom.jl.decoder.JavaLayerException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PollyController {
    private final PollyService pollyService;

    @GetMapping("/play")
    public String playFile(@RequestParam("num") long num) throws IOException, JavaLayerException {
        System.out.println(num);
        pollyService.playFile(num);
        return "OK";
    }

}
