package com.example.teunis.projectapp.Items;

import java.io.Serializable;

public class ChannelItem implements Serializable {

    public String name, description, imageUrl, url;

    public ChannelItem(String name, String description, String imageUrl, String url) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public String getName() {return name;}
    public String getDescription() {return name;}
    public String getImageUrl() {return name;}
    public String getUrl() {return name;}
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
    public void setUrl(String url) {this.url = url;}

}
