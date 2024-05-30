package exercise.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class PostDTO {
    private Long id;
    private String title;
    private String body;
    private List<CommentDTO> comments;
}