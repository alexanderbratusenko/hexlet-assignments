package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/post/{id}")
    Optional<Post> getPost(@PathVariable String id) {
        return posts.stream().filter(post -> post.getId().equals(id)).findFirst();
    }

    @GetMapping("/posts")
    List<Post> getPosts(@RequestParam(defaultValue = "10") Integer limit,
                        @RequestParam(defaultValue = "1") Integer page) {
        long skip = (long) (page - 1) * limit;
        return posts.stream().skip(skip).limit(limit).toList();
    }

    @PutMapping("/post/{id}")
    Optional<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        var existed = posts.stream().filter((ex) -> ex.getId().equals(id)).findFirst();
        existed.ifPresent(value -> {
            value.setTitle(post.getTitle());
            value.setBody(post.getBody());
        });
        return existed;
    }

    @DeleteMapping("/post/{id}")
    void deletePost(@PathVariable String id) {
        posts.removeIf((post) -> post.getId().equals(id));
    }
}
