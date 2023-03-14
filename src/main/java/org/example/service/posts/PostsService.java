package org.example.service.posts;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.posts.PostRepository;
import org.example.domain.posts.Posts;
import org.example.web.dto.PostsSaveRequestDto;
import org.example.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {


  private final PostRepository postRepository;

  @Transactional
  public Long save(PostsSaveRequestDto requestDto) {
    return postRepository.save(requestDto.toEntity()).getId();
  }

  public Long update(Long id, PostsUpdateRequestDto requestDto){
    Posts posts = postRepository.findById(id).orElseThrow(
        ()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    posts.update(requestDto.getTitle(),requestDto.getContent());
    return id;
  }
}