package org.example.web.dto;

import lombok.Getter;
import org.example.domain.posts.Posts;

@Getter
public class PostResponseDto {

  private Long id;
  private String title;
  private String content;
  private String author;

  public PostResponseDto(Posts entity){
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
  }
}
