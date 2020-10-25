package com.example.epicture;

import net.azzerial.jmgur.api.entities.Comment;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class UserComment implements Serializable {

    public String authorName;
    public String comment;
    public int up;
    public int down;
    public OffsetDateTime date;

    public UserComment(Comment comment) {
        this.comment = comment.getContent();
        this.authorName = comment.getAuthorName();
        this.up = comment.getUps();
        this.down = comment.getPoints();
        this.date = comment.getCreationDate();
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

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }
}
