package exercise.controller.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

@RestController
@RequestMapping("/api/users/")
class PostsController {

    private final List<Post> posts = Data.getPosts();

    @GetMapping("/{userId}/posts")
    List<Post> getPosts(@PathVariable int userId) {
        return posts.stream().filter(post -> post.getUserId() == userId).toList();
    }

    @PostMapping("/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    Post createPost(@PathVariable int id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return post;
    }
}
