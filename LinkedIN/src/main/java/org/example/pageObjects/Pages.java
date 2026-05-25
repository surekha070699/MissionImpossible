package org.example.pageObjects;

public enum Pages {
    HOME("/home"),
    LOGIN("login/");

    private String name;

    Pages(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
