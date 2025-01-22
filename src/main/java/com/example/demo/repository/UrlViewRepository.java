package com.example.demo.repository;

import com.example.demo.entity.UrlView;
import com.example.demo.to.UrlRankingTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlViewRepository extends JpaRepository<UrlView, Long> {

    @Query("SELECT new com.example.demo.to.UrlRankingTO(uv.url, COUNT(uv)) FROM UrlView uv GROUP BY uv.url ORDER BY COUNT(uv) DESC")
    List<UrlRankingTO> findRankingUrlView();
}
