package com.jellyb3ar.polly;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PollyResponse {
    private long id;
    private String cardText;
    private String mp3Url;
    private String imgUrl;

    @Builder
    public PollyResponse(long id, String cardText, String mp3Url, String imgUrl) {
        this.id = id;
        this.cardText = cardText;
        this.mp3Url = mp3Url;
        this.imgUrl = imgUrl;
    }
}
