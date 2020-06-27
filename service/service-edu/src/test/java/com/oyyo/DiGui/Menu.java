package com.oyyo.DiGui;

import lombok.Data;

import java.util.List;

@Data
public class Menu {
    private String id;
    private String parentId;
    private String text;
    private List<Menu> children;
    public Menu(String id,String parentId,String text) {
        this.id=id;
        this.parentId=parentId;
        this.text=text;
    }
        /*省略get\set*/
}