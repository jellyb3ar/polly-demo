package com.jellyb3ar.polly;

import javazoom.jl.decoder.JavaLayerException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PollyController {
    private final PollyService pollyService;

    @GetMapping("/")
    public List<PollyResponse> getList() throws IOException, JavaLayerException {
        System.out.println("all");
        List<PollyResponse> response = pollyService.getList();
        return response;
    }

    @GetMapping("/makeFile")
    public PollyResponse makeFile(@RequestParam("num") long num) throws IOException, JavaLayerException {
        System.out.println(num);
        PollyResponse response = pollyService.playFile(num);
        return response;
    }

}
