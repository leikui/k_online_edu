package com.oyyo.eduService.utils;

import com.oyyo.eduService.vo.SubjectVO;

import java.util.ArrayList;
import java.util.List;

public class MenuTree {
    private List<SubjectVO> menuList = new ArrayList<>();
    public MenuTree(List<SubjectVO> menuList) {
        this.menuList=menuList;
    }

    //建立树形结构
    public List<SubjectVO> builTree(){
        List<SubjectVO> treeMenus =new  ArrayList<>();
        for(SubjectVO menuNode : getRootNode()) {
            menuNode=buildChilTree(menuNode);
            treeMenus.add(menuNode);
        }
        return treeMenus;
    }

    //递归，建立子树形结构
    private SubjectVO buildChilTree(SubjectVO pNode){
        List<SubjectVO> chilMenus =new  ArrayList<>();
        for(SubjectVO menuNode : menuList) {
            if(menuNode.getParentId().equals(pNode.getId())) {
                chilMenus.add(buildChilTree(menuNode));
            }
        }
        pNode.setChildren(chilMenus);
        return pNode;
    }

    //获取根节点
    private List<SubjectVO> getRootNode() {
        List<SubjectVO> rootMenuLists =new  ArrayList<>();
        for(SubjectVO menuNode : menuList) {
            if(menuNode.getParentId().equals("0")) {
                rootMenuLists.add(menuNode);
            }
        }
        return rootMenuLists;
    }
}