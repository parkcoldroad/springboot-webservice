package org.example.web;

import lombok.RequiredArgsConstructor;
import org.example.service.posts.PostsService;
import org.example.web.dto.PostResponseDto;
import org.example.web.dto.PostsSaveRequestDto;
import org.example.web.dto.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

  private final PostsService postsService;

  @PostMapping("/api/v1/posts")
  public Long save(@RequestBody PostsSaveRequestDto requestDto){
    return postsService.save(requestDto);
  }

  @PutMapping
  public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
    return postsService.update(id,requestDto);
  }

  public PostResponseDto findById(@PathVariable Long id){
    return postsService.findById(id);
  }

}
