package com.example.eblog.dto;

import com.example.eblog.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private String username;
    private String body;


    public CommentDTO(Comment comment) {
        this.username = comment.getUser().getUsername();
        this.body = comment.getBody();
    }
}
