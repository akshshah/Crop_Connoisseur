package com.example.cropconnoisseur.Model;

public class Comment {

    private String comment;
    private String commentId;
    private String date;
    private String postId;
    private String publisherId;

    public Comment() {
    }

    public Comment(String comment, String commentId, String date, String postId, String publisherId) {
        this.comment = comment;
        this.commentId = commentId;
        this.date = date;
        this.postId = postId;
        this.publisherId = publisherId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
}
