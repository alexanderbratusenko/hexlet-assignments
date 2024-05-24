package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/posts")
public class PostsController {
    private final PostRepository postRepository;
    private final CommentRepository commentsRepository;

    public PostsController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentsRepository = commentRepository;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping("/{postId}")
    public Post findPost(@PathVariable long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        commentsRepository.deleteByPostId(postId);
        postRepository.deleteById(postId);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable Long postId, @RequestBody Post post) {
        var existingPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingPost.setBody(post.getBody());
        existingPost.setTitle(post.getTitle());
        return postRepository.save(existingPost);
    }
}