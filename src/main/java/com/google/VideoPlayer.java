package com.google;

import java.util.List;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video currentVideo;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.currentVideo = null;
  }

  public void numberOfVideos() {
      System.out.printf("%s videos in the library%n" , videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("\"Here's a list of all available videos:");

    List<Video> videosList = videoLibrary.getVideos();

    //sort in lexicographical order by title
    videosList.sort((video1, video2) -> video1.getTitle().compareTo(video2.getTitle()));

    //print video details line by line
    for (Video video : videosList){
      //Video Title
      System.out.printf (video.getTitle());
      System.out.printf(" (");

      //Video ID
      System.out.printf (video.getVideoId());
      System.out.printf (") [");

      //Video Tags
      for (int i = 0; i < video.getTags().size(); i++) {
        System.out.printf (video.getTags().get(i));

        if (i < (video.getTags().size()- 1) ) {
          System.out.printf (" ");
        }
      }
      System.out.println("]");
    }
  }

  public void playVideo(String videoId) {
    Boolean videoFound = false;

    for (Video chosenVideo : videoLibrary.getVideos()){
      if (chosenVideo.getVideoId().equals(videoId)){
        if (currentVideo != null){
          stopVideo();
        }
        System.out.println("Playing video: " + chosenVideo.getTitle());
        currentVideo = chosenVideo;
        videoFound = true;
      }
    }
    if (videoFound == false){
      System.out.println ("Cannot play video: Video does not exist");
    }

  }

  public void stopVideo() {
    if (currentVideo != null){
      System.out.println("Stopping video: " + currentVideo.getTitle());
      currentVideo = null;
    }
    else {
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }

  public void playRandomVideo() {
    System.out.println("playRandomVideo needs implementation");
  }

  public void pauseVideo() {
    System.out.println("pauseVideo needs implementation");
  }

  public void continueVideo() {
    System.out.println("continueVideo needs implementation");
  }

  public void showPlaying() {
    System.out.println("showPlaying needs implementation");
  }

  public void createPlaylist(String playlistName) {
    System.out.println("createPlaylist needs implementation");
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println("addVideoToPlaylist needs implementation");
  }

  public void showAllPlaylists() {
    System.out.println("showAllPlaylists needs implementation");
  }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}