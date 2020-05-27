package com.caigen.graduationproject.model;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-20 20:15
 * @description
 */
public class ItemsBean {
    private String items = "";

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void addItem(String item){
        this.items += item;
    }
}
