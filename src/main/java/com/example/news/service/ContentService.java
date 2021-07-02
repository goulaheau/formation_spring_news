package com.example.news.service;

import com.example.news.model.Content;
import java.util.List;

public interface ContentService {
  List<Content> getAll();
  Content get(long id);
  Content save(Content content);
  void delete(long id);
}
