package com.example.cropconnoisseur.Model;

public class Post {

    private String postId;
    private String question;
    private String publisherName;
    private String publisherId;
    private boolean hasImage;
    private String imageUrl;
    private String topic;
    private String date;

    public Post() {
    }

    public Post(String postId, String question, String publisherName, String publisherId,
                boolean hasImage, String imageUrl, String topic, String date) {
        this.postId = postId;
        this.question = question;
        this.publisherName = publisherName;
        this.publisherId = publisherId;
        this.hasImage = hasImage;
        this.imageUrl = imageUrl;
        this.topic = topic;
        this.date = date;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
