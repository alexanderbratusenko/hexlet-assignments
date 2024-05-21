package exercise;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/posts")
    ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(posts.size())).body(posts);
    }

    @GetMapping("/posts/{id}")
    ResponseEntity<Post> getPostById(@PathVariable String id) {
        var post = posts.stream().filter((p) -> p.getId().equals(id)).findFirst();
        return post.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/posts")
    ResponseEntity<Post> createPost(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/posts/{id}")
    ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        var existingPost = posts.stream().filter((p) -> p.getId().equals(id)).findFirst();
        if (existingPost.isPresent()) {
            existingPost.get().setTitle(post.getTitle());
            existingPost.get().setBody(post.getBody());
            return ResponseEntity.ok().body(existingPost.get());
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(post);
        }
    }

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
