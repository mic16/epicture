package com.example.epicture;

import net.azzerial.jmgur.api.entities.Comment;

import java.io.Serializable;

public class UserComment implements Serializable {

    public String authorName;
    public String comment;

    public UserComment(Comment comment) {
        this.comment = comment.getContent();
        this.authorName = comment.getAuthorName();
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
