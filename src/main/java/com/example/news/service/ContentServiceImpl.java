package com.example.news.service;

import com.example.news.model.Content;
import com.example.news.repository.ContentRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContentServiceImpl implements ContentService{
  @Autowired
  private ContentRepository contentRepository;

  @Override
  public List<Content> getAll() {
    return contentRepository.findAll();
  }

  @Override
  public Content get(long id) {
    return contentRepository.getById(id);
  }

  @Override
  public Content save(Content content) {
    return contentRepository.save(content);
  }

  @Override
  public void delete(long id) {
    contentRepository.deleteById(id);
  }
}
