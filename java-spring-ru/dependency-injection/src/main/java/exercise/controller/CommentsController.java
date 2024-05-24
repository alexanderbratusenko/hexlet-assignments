package exercise.controller;

import exercise.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    private final CommentRepository commentRepository;

    public CommentsController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    @GetMapping("/{commentId}")
    public Comment findComment(@PathVariable long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));
    }


    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable long commentId) {
        commentRepository.deleteById(commentId);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@RequestBody Comment comment, @PathVariable long commentId) {
        var existingComment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingComment.setBody(comment.getBody());
        existingComment.setPostId(comment.getPostId());
        return commentRepository.save(existingComment);
    }
}