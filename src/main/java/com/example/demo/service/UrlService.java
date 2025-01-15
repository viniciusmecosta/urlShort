package com.example.demo.service;

import com.example.demo.TO.*;
import com.example.demo.entity.UrlShort;
import com.example.demo.entity.UrlView;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Map<String, Long> urlCountMap = urlViewList.stream()
                .collect(Collectors.groupingBy(UrlView::getUrl, Collectors.counting()));

        List<UrlRankingTO> top10Urls = urlCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> new UrlRankingTO(entry.getKey(), entry.getValue().intValue()))
                .toList();

        return new ArrayList<>(top10Urls);
    }
}

