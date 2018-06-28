/* VideoItem is a class for storing information about a YouTube video:
 * id of the video, description, corresponding YouTube channel, title of the video, url of the
 * thumbnail and duration (float) of the video.
 */

package com.example.teunis.projectapp.Items;

public class VideoItem {
    public String id, description, channel, title, image;
    public float duration;

    public VideoItem(String id, String description, String channel, String title, String image, float duration) {
        this.id = id;
        this.description = description;
        this.channel = channel;
        this.title = title;
        this.image = image;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getChannel() {
        return channel;
    }

    public String getTitle() {
        return title;
    }

    public float getDuration() {
        return duration;
    }

    public String getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
