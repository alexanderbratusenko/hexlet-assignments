package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostRepository postRepository;
    @Autowired
    private final CommentRepository commentRepository;

    public PostsController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping
    List<PostDTO> getPosts() {
        return postRepository.findAll().stream().map(this::mapToPostDTO).toList();
    }

    @GetMapping("/{id}")
    PostDTO getPosts(@PathVariable long id) {
        return postRepository
                .findById(id)
                .map(this::mapToPostDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
    }

    private PostDTO mapToPostDTO(Post post) {
        List<CommentDTO> comments = commentRepository
                .findByPostId(post.getId()).stream()
                .map(this::mapToCommentDTO).toList();
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setComments(comments);
        return dto;
    }

    private CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        return dto;
    }
}