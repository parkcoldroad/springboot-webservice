package org.example.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.example.domain.posts.PostRepository;
import org.example.domain.posts.Posts;
import org.example.web.dto.PostsSaveRequestDto;
import org.example.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private PostRepository postRepository;

  @After
  public void tearDown() {
    postRepository.deleteAll();
  }

  @Test
  public void Posts_등록된다() {
    String title = "title";
    String content = "content";
    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
        .title(title)
        .content(content)
        .author("author")
        .build();
    String url = "http://localhost:" + port + "/api/v1/posts";

    ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);
    List<Posts> all = postRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(title);
    assertThat(all.get(0).getContent()).isEqualTo(content);
  }

  @Test
  public void Posts_수정된다() {
    Posts savedPosts = postRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());
    Long updateId = savedPosts.getId();
    String expectedTitle = "title2";
    String expectedContent = "content2";

    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
        .title(expectedTitle)
        .content(expectedContent)
        .build();

    String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
    HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

    ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,requestEntity,
        Long.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);
    List<Posts> all = postRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
    assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
  }
}
