package org.example.service.posts;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.posts.PostRepository;
import org.example.domain.posts.Posts;
import org.example.web.dto.PostResponseDto;
import org.example.web.dto.PostsListResponseDto;
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

  @Transactional
  public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts posts = postRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    posts.update(requestDto.getTitle(), requestDto.getContent()); //dirty checking
    return id;
  }


  public PostResponseDto findById(Long id) {
    Posts entity = postRepository.findById(id)
        .orElseThrow(() ->
            new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    return new PostResponseDto(entity);
  }

  @Transactional
  public List<PostsListResponseDto> findAllDesc(){
    return postRepository.findAllDesc().stream()
        .map(posts -> new PostsListResponseDto(posts))
        .collect(Collectors.toList());
  }

  @Transactional
  public void delete(Long id){
    Posts post = postRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
    postRepository.delete(post);
  }
}