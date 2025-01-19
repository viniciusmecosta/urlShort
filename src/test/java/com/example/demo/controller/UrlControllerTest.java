package com.example.demo.controller;

import com.example.demo.to.UrlRankingTO;
import com.example.demo.to.UrlResponseTO;
import com.example.demo.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    @Test
    void create_shouldReturn201AndShortenedUrl_whenUrlIsValid() throws Exception {
        String originalUrl = "https://example.com";
        String shortUrl = "abc123";
        UrlResponseTO responseTO = new UrlResponseTO(originalUrl, shortUrl);

        when(urlService.shortenUrl(originalUrl)).thenReturn(responseTO);

        mockMvc.perform(post("/api/create")
                        .param("url", originalUrl))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.urlOriginal").value(originalUrl))
                .andExpect(jsonPath("$.urlShort").value(shortUrl));

        verify(urlService).shortenUrl(originalUrl);
    }

    @Test
    void find_shouldRedirectToOriginalUrl_whenShortUrlExists() throws Exception {
        String shortUrl = "abc123";
        String originalUrl = "https://example.com";

        when(urlService.find(shortUrl)).thenReturn(originalUrl);

        mockMvc.perform(get("/api/find")
                        .param("url", shortUrl))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", URI.create(originalUrl).toString()));

        verify(urlService).find(shortUrl);
    }

    @Test
    void ranking_shouldReturn200AndListOfUrls_whenRankingIsAvailable() throws Exception {
        List<UrlRankingTO> ranking = List.of(
                new UrlRankingTO("https://example1.com", 5),
                new UrlRankingTO("https://example2.com", 3)
        );

        when(urlService.rankingUrl()).thenReturn(ranking);

        mockMvc.perform(get("/api/ranking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].url").value("https://example1.com"))
                .andExpect(jsonPath("$[0].count").value(5))
                .andExpect(jsonPath("$[1].url").value("https://example2.com"))
                .andExpect(jsonPath("$[1].count").value(3));

        verify(urlService).rankingUrl();
    }

    @Test
    void find_shouldReturn404_whenShortUrlDoesNotExist() throws Exception {
        String shortUrl = "nonexistent";

        when(urlService.find(shortUrl)).thenThrow(new RuntimeException("Url not found"));

        mockMvc.perform(get("/api/find")
                        .param("url", shortUrl))
                .andExpect(status().isNotFound());

        verify(urlService).find(shortUrl);
    }
}
