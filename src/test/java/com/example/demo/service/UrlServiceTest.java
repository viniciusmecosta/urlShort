package com.example.demo.service;

import com.example.demo.entity.Url;
import com.example.demo.entity.UrlView;
import com.example.demo.exception.*;
import com.example.demo.repository.UrlRepository;
import com.example.demo.repository.UrlViewRepository;
import com.example.demo.to.UrlRankingTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlViewRepository urlViewRepository;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shortenUrl_shouldReturnExistingUrl_whenUrlAlreadyExists() {
        String urlOriginal = "https://example.com";
        String urlShort = "https://abc123.com";
        Url existingUrl = new Url(urlOriginal,urlShort);

        when(urlRepository.findByUrlOriginal(urlOriginal)).thenReturn(existingUrl);


        assertEquals(urlOriginal, existingUrl.getUrlOriginal());
        assertEquals(urlShort, existingUrl.getUrlShort());
        verify(urlRepository, never()).save(any());
    }

    @Test
    void shortenUrl_shouldGenerateNewUrl_whenUrlDoesNotExist() {
        String urlOriginal = "https://example.com";

        when(urlRepository.findByUrlOriginal(urlOriginal)).thenReturn(null);
        when(urlRepository.save(any(Url.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Url url = urlService.shortenUrl(urlOriginal);

        assertEquals(urlOriginal, url.getUrlOriginal());
        assertNotNull(url.getUrlShort());
        verify(urlRepository).save(any(Url.class));
    }

    @Test
    void shortenUrl_shouldThrowException_whenUrlIsInvalid() {
        String invalidUrl = "invalid-url";

        UrlInvalidException exception = assertThrows(UrlInvalidException.class, () -> {
            urlService.shortenUrl(invalidUrl);
        });
        assertEquals("Invalid URL (Example: 'example.com' or 'https://example.com' or 'http://example.com')", exception.getMessage());
    }

    @Test
    void ranking_shouldReturnTop10Urls() {
        List<UrlRankingTO> urlRankingTOS = List.of(
                new UrlRankingTO("url1", 5L),
                new UrlRankingTO("url1", 6L),
                new UrlRankingTO("url2", 9L),
                new UrlRankingTO("url3", 1L)
        );

        when(urlViewRepository.findRankingUrlView()).thenReturn(urlRankingTOS);

        List<UrlRankingTO> ranking = urlService.ranking();

        assertEquals(4, ranking.size());
        assertEquals("url3", ranking.get(3).url());
        assertEquals(5L, ranking.get(0).count() );

    }

    @Test
    void ranking_shouldThrowException_whenNoUrlViewsExist() {
        when(urlViewRepository.findAll()).thenReturn(Collections.emptyList());

        NoUrlViewException exception = assertThrows(NoUrlViewException.class, () -> {
            urlService.ranking();
        });
        assertEquals("No url fetched", exception.getMessage());
    }

    @Test
    void find_shouldReturnOriginalUrl_whenShortUrlExists() {
        String urlShort = "https://abc123.com";
        String urlOriginal = "https://example.com";
        Url url = new Url(urlOriginal, urlShort);

        when(urlRepository.findByUrlShort(urlShort)).thenReturn(url);

        String result = urlService.find(urlShort);

        assertEquals(urlOriginal, result);
        verify(urlViewRepository).save(any(UrlView.class));
    }

    @Test
    void find_shouldThrowException_whenShortUrlDoesNotExist() {
        String urlShort = "nonexistent.com";

        when(urlRepository.findByUrlShort(urlShort)).thenReturn(null);

        UrlNotFoundException exception = assertThrows(UrlNotFoundException.class, () -> {
            urlService.find(urlShort);
        });
        assertEquals("Url not found", exception.getMessage());
    }
}
