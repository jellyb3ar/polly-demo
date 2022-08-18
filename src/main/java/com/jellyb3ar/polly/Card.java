package com.jellyb3ar.polly;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private long id;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "text")
    private String cartText;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "mp3_url")
    private String mp3Url;
}
