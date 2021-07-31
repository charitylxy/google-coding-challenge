package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private final String name;
    private final ArrayList<Video> videos;

    VideoPlaylist(String name){
        this.name = name;
        this.videos = new ArrayList<>();
    }

    String getName(){
        return name;
    }

    ArrayList<Video> getVideos(){
        return videos;
    }
}
