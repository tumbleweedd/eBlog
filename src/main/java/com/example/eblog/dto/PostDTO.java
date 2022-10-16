package com.example.eblog.dto;

import com.example.eblog.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.*;
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
    private Map<String, List<String>> comment;
    private String date;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.date = dateFormat(post);
        this.title = post.getHead();
        this.body = post.getBody();
        this.category = getCategoryName(post);
        this.tags = getTagsName(post);
        this.comment = getComment(post);
    }

    private String dateFormat(Post post) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(post.getDateCreation());
    }

    private List<String> getTagsName(Post post) {
        List<Tag> tagList = post.getTags();
        return tagList.stream().map(Tag::getName).collect(Collectors.toList());
    }

    private Map<String, List<String>> getComment(Post post) {
        Map<String, List<String>> commentMap = new HashMap<>();
        List<Comment> commentList = post.getComments();


        for (Comment c : commentList) {
            commentMap.put(c.getUser().getUsername(),
                    commentList.stream()
                            .filter(e -> Objects.equals(e.getUser().getId(), c.getUser().getId()))
                            .map(Comment::getBody)
                            .collect(Collectors.toList()));
        }

        return commentMap;
    }

    private String getCategoryName(Post post) {
        Category categoryName = post.getCategory();
        return categoryName.getName();
    }
}
