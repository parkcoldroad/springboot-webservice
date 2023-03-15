package org.example.web.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.example.domain.posts.PostRepository;
import org.example.domain.posts.Posts;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

  @Autowired
  PostRepository postRepository;

  @After
  public void cleanup() {
    postRepository.deleteAll();
  }

  @Test
  public void get_contents() {
    String title = "테스트 게시글";
    String content = "테스트 본문";

    postRepository.save(Posts.builder()
        .title(title)
        .content(content)
        .author("changil.com")
        .build());

    List<Posts> postsList = postRepository.findAll();

    Posts posts = postsList.get(0);
    assertThat(posts.getTitle()).isEqualTo(title);
    assertThat(posts.getContent()).isEqualTo(content);
  }

  @Test
  public void BaseTimeEntity_등록(){
    LocalDateTime now = LocalDateTime.of(2023,3,15,0,0,0);
    postRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());

    List<Posts> postsList = postRepository.findAll();

    Posts posts = postsList.get(0);

    System.out.println(">>>>> createDate=" + posts.getCreatedDate() + ""
        + "modifiedDate = " + posts.getModifiedDate());

    assertThat(posts.getCreatedDate()).isAfter(now);
    assertThat(posts.getModifiedDate()).isAfter(now);
  }
}
