package com.example.eblog.dto;

import com.example.eblog.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Long id;
    private String title;
    private String body;
    private String category;
    private List<String> tags;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getHead();
        this.body = post.getBody();
        this.category = getCategoryName(post);
        this.tags = getTagsName(post);
    }

    private List<String> getTagsName(Post post) {
        List<Tag> tagList = post.getTags();
        return tagList.stream().map(Tag::getName).collect(Collectors.toList());
    }

    private String getCategoryName(Post post) {
        Category categoryName = post.getCategory();
        return categoryName.getName();
    }
}
