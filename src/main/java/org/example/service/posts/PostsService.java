package org.example.service.posts;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.posts.PostRepository;
import org.example.web.dto.PostsSaveRequestDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {


  private final PostRepository postRepository;

  @Transactional
  public Long save(PostsSaveRequestDto requestDto) {
    return postRepository.save(requestDto.toEntity()).getId();
  }
}
