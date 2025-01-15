package com.example.demo.service;

import com.example.demo.TO.UrlRankingTO;
import com.example.demo.TO.UrlRequestTO;
import com.example.demo.TO.UrlResponseTO;
import com.example.demo.entity.UrlShort;
import com.example.demo.entity.UrlView;
import com.example.demo.repository.UrlRepository;
import com.example.demo.repository.UrlShortRepository;
import com.example.demo.repository.UrlViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    private UrlShortRepository urlShortRepository;

    private UrlShort urlShort;

    private UrlResponseTO urlResponseTO;

    @Autowired
    private UrlViewRepository urlViewRepository;

    public UrlResponseTO shortenUrl(UrlRequestTO urlRequestTO) {


        return urlResponseTO;
    }

    public List<UrlRankingTO> rankingUrl() {
        List<UrlView> urlViewList = urlViewRepository.findAll();

        Map<String, Long> urlCountMap = new HashMap<>();
        for (UrlView urlView : urlViewList) {
            String url = urlView.getUrl();
            urlCountMap.put(url, urlCountMap.getOrDefault(url, 0L) + 1);
        }
        List<UrlRankingTO> top10Urls = urlCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> new UrlRankingTO(entry.getKey(), entry.getValue().intValue()))
                .toList();
        return new ArrayList<>(top10Urls);
    }
}

