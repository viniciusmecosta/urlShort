package com.example.demo.repository;

import com.example.demo.entity.UrlView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlViewRepository extends JpaRepository<UrlView, Long> {

}
