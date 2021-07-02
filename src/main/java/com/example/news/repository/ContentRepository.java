package com.example.news.repository;

import com.example.news.model.Content;
import com.example.news.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {}
