package com.example.eblog.dto;

import com.example.eblog.model.Category;
import com.example.eblog.model.Comment;
import com.example.eblog.model.Post;
import com.example.eblog.model.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private String title;
    private String body;
    private String category;
    private List<String> tags;
}
