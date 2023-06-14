package com.example.board111;

public class Board {

    private String id;
    private String title;
    private String context;
    private String name;

    public Board() {
    }

    public Board(String id, String title, String context, String name) {
        this.id = id;
        this.title = title;
        this.context = context;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
