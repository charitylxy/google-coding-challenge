package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private ArrayList <VideoPlaylist> videoPlaylists;
  private Video currentVideo;
  private Video pausedVideo;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.videoPlaylists = new ArrayList<>();
    this.currentVideo = null;
    this.pausedVideo = null;
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
      printDetails(video);
    }
  }

  public void printDetails (Video video){
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
    if (pausedVideo == null) {
      System.out.printf("]");
    }
    else {
      System.out.printf("] - PAUSED");
    }

    if (video.getFlagStatus() == true){
      System.out.printf (" - FLAGGED (reason: " + video.getFlagReason() + ")");
    }

    System.out.println();
  }

  private boolean checkVideoExist (String videoId){
    boolean exist = false;
    for (Video chosenVideo : videoLibrary.getVideos()){
      if (chosenVideo.getVideoId().equals(videoId)){
        exist = true;
      }
    }
    return exist;
  }

  public void playVideo(String videoId) {
    if (checkVideoExist(videoId)){
      Video selectedVideo = videoLibrary.getVideo(videoId);
      if (selectedVideo.getFlagStatus() == false){
        if (currentVideo != null){
          stopVideo();
        }
        System.out.println("Playing video: " + selectedVideo.getTitle());
        currentVideo = selectedVideo;
        pausedVideo = null;
      }
      else {
        System.out.println("Cannot play video: Video is currently flagged (reason: " + selectedVideo.getFlagReason() + ")");
      }

    }
    else{
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

  private boolean checkAllFlaggedVideos (){
    boolean check = true;
    for (Video video : videoLibrary.getVideos()){
      if (video.getFlagStatus() == false){
        check = false;
      }
    }
    return check;
  }

  public void playRandomVideo() {
    Video selectedVideo = null;
    if (videoLibrary.getVideos().isEmpty()){
      System.out.println("No available videos");
    }
    else {
      if (!checkAllFlaggedVideos()){
        while (selectedVideo == null){
          Random rand = new Random();
          Video selectRandom = videoLibrary.getVideos().get(rand.nextInt(videoLibrary.getVideos().size()));
          if (selectRandom.getFlagStatus() == false){
            selectedVideo = selectRandom;
          }
        }
        playVideo (selectedVideo.getVideoId());
      }
      else {
        System.out.println("No videos available");
      }
    }

  }

  public void pauseVideo() {
    if (currentVideo == null){
      System.out.println("Cannot pause video: No video is currently playing");
    }
    else {
      if (currentVideo != pausedVideo) {
        System.out.println("Pausing video: " + currentVideo.getTitle());
        pausedVideo = currentVideo;
      }
      else {
        System.out.println("Video already paused: " + currentVideo.getTitle());
      }
    }
  }

  public void continueVideo() {
    if ( currentVideo !=null && pausedVideo == null){
      System.out.println("Cannot continue video: Video is not paused" );
    }
    else if (currentVideo == null){
      System.out.println("Cannot continue video: No video is currently playing" );
    }
    else {
      System.out.println("Continuing video: " + pausedVideo.getTitle());
      pausedVideo = null;
    }
  }

  public void showPlaying() {
    if (currentVideo == null){
      System.out.println("No video is currently playing");
    }
    else {
      System.out.printf("Currently playing: " );
      printDetails(currentVideo);
    }
  }

  private boolean checkPlaylist (String playlistName){
    boolean exist = false;

    for (VideoPlaylist playlist : videoPlaylists){
      if (playlistName.equalsIgnoreCase(playlist.getName())){
        exist = true;
      }
    }
    return  exist;
  }

  private VideoPlaylist getVideoPlaylist (String playlistName){
    VideoPlaylist selectedPlaylist = null;
    for (VideoPlaylist playlist : videoPlaylists){
      if (playlistName.equalsIgnoreCase(playlist.getName())){
        selectedPlaylist = playlist;
      }
    }
    return selectedPlaylist;
  }

  public void createPlaylist(String playlistName) {
    if (checkPlaylist(playlistName)== false){
      System.out.println("Successfully created new playlist: " + playlistName);
      videoPlaylists.add(new VideoPlaylist(playlistName));
    }
    else {
      System.out.println("Cannot create playlist: A playlist with the same name already exists" );
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    if (checkPlaylist(playlistName)){
      VideoPlaylist selectedPlaylist = getVideoPlaylist(playlistName);

      if (checkVideoExist(videoId)){
        Video selectedVideo = videoLibrary.getVideo(videoId);
        if (selectedVideo.getFlagStatus()== false){
          if (selectedPlaylist.getVideos().contains(selectedVideo)){
            System.out.println("Cannot add video to " + playlistName + ": Video already added");
          }
          else {
            System.out.println("Added video to " + playlistName + ": " + selectedVideo.getTitle());
            selectedPlaylist.getVideos().add(selectedVideo);
          }
        }
        else {
          System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged (reason: " + selectedVideo.getFlagReason() + ")");
        }
      }

      else {
        System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      }
    }
    else {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    }

  }

  public void showAllPlaylists() {
    if (videoPlaylists.isEmpty()){
      System.out.println("No playlists exist yet ");
    }
    else {
      System.out.println("Showing all playlists: ");

      videoPlaylists.sort((video1, video2) -> video1.getName().compareTo(video2.getName()));
      for (VideoPlaylist playlist : videoPlaylists){
        System.out.println(playlist.getName());
      }
    }
  }

  public void showPlaylist(String playlistName) {
    if (checkPlaylist(playlistName)){
      VideoPlaylist selectedPlaylist = getVideoPlaylist(playlistName);
      System.out.println("Showing playlist: " + playlistName);

      if (selectedPlaylist.getVideos().isEmpty()){
        System.out.println("No videos here yet " );
      }
      else{
        for (Video video : selectedPlaylist.getVideos()){
          printDetails(video);
        }
      }

    }
    else {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    }

  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if (checkPlaylist(playlistName)){
      VideoPlaylist selectedPlaylist = getVideoPlaylist(playlistName);
      if (checkVideoExist(videoId)){
        Video selectedVideo = videoLibrary.getVideo(videoId);

        if (selectedPlaylist.getVideos().contains(selectedVideo)){
          System.out.println("Removed video from " + playlistName +": "  +  selectedVideo.getTitle());
          selectedPlaylist.getVideos().remove(selectedVideo);
        }
        else {
          System.out.println("Cannot remove video from " + playlistName +": Video is not in playlist");
        }
      }

      else {
        System.out.println("Cannot remove video from " + playlistName +": Video does not exist");
      }

    }
    else {
      System.out.println("Cannot remove video from " + playlistName +": Playlist does not exist");
    }
  }

  public void clearPlaylist(String playlistName) {
    if (checkPlaylist(playlistName)) {
      VideoPlaylist selectedPlaylist = getVideoPlaylist(playlistName);
      selectedPlaylist.getVideos().clear();
      System.out.println("Successfully removed all videos from " + playlistName);
    }
    else {
      System.out.println("Cannot clear playlist " + playlistName +": Playlist does not exist");
    }
  }

  public void deletePlaylist(String playlistName) {
    if (checkPlaylist(playlistName)){
      VideoPlaylist selectedPlaylist = getVideoPlaylist(playlistName);
      videoPlaylists.remove(selectedPlaylist);
      System.out.println("Deleted playlist: " + playlistName );
    }
    else {
      System.out.println("Cannot delete playlist " + playlistName +": Playlist does not exist");
    }

  }

  private void playSearchedVideo (ArrayList<Video> videos){
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
            "If your answer is not a valid number, we will assume it's a no.");

    Scanner input = new Scanner(System.in);

    try {
      int videoNum = Integer.parseInt(input.nextLine());
      if (videoNum > 0 && videoNum <= videos.size()){
        playVideo(videos.get(videoNum-1).getVideoId());
      }
    }
    catch (NumberFormatException exception){

    }

  }
  public void searchVideos(String searchTerm) {
    ArrayList<Video> searchedVideos = new ArrayList<Video>();

    for (Video video : videoLibrary.getVideos()){
      if (video.getTitle().toLowerCase(Locale.ROOT).contains(searchTerm.toLowerCase(Locale.ROOT))){
        if (video.getFlagStatus() == false){
          searchedVideos.add(video);
        }
      }
    }

    //sort in lexicographical order by title
    searchedVideos.sort((video1, video2) -> video1.getTitle().compareTo(video2.getTitle()));

    if (searchedVideos.isEmpty()){
      System.out.println("No search results for " + searchTerm );

    }
    else {
      System.out.println("Here are the results for " + searchTerm + ":");

      for (int i = 0 ; i < searchedVideos.size(); i++){
        System.out.printf( (i+1) + ") ") ;
        printDetails(searchedVideos.get(i));
      }

      playSearchedVideo(searchedVideos);
    }
  }

  public void searchVideosWithTag(String videoTag) {
    ArrayList<Video> searchedVideos = new ArrayList<Video>();

    for (Video video : videoLibrary.getVideos()){
      for (String tag : video.getTags()){
        if (tag.equalsIgnoreCase(videoTag.toLowerCase(Locale.ROOT))){
          if (video.getFlagStatus() == false){
            searchedVideos.add(video);
          }
        }
      }
    }
    //sort in lexicographical order by title
    searchedVideos.sort((video1, video2) -> video1.getTitle().compareTo(video2.getTitle()));

    if (searchedVideos.isEmpty()){
      System.out.println("No search results for " + videoTag );
    }

    else {
      System.out.println("Here are the results for " + videoTag + ":");

      for (int i = 0 ; i < searchedVideos.size(); i++){
        System.out.printf( (i+1) + ") ") ;
        printDetails(searchedVideos.get(i));
      }

      playSearchedVideo(searchedVideos);
    }
  }
  public void flagVideo(String videoId) {
    if (checkVideoExist(videoId)){
      Video video = videoLibrary.getVideo(videoId);
      if (video.getFlagStatus() == false){
        videoLibrary.getVideo(videoId).setFlagStatus(true);
        System.out.println("Successfully flagged video: " + video.getTitle() + " (reason: " + video.getFlagReason() + ")");
      }
      else {
        System.out.println("Cannot flag video: Video is already flagged");
      }
    }
    else {
      System.out.println("Cannot flag video: Video does not exist");
    }
  }

  public void flagVideo(String videoId, String flagReason) {
    if (checkVideoExist(videoId)){
      Video video = videoLibrary.getVideo(videoId);
      if (video.getFlagStatus() == false){
        if (currentVideo == video){
          stopVideo();
        }
        videoLibrary.getVideo(videoId).setFlagStatus(true);
        videoLibrary.getVideo(videoId).setFlagReason(flagReason);
        System.out.println("Successfully flagged video: " + video.getTitle() + " (reason: " + video.getFlagReason() + ")");
      }
      else {
        System.out.println("Cannot flag video: Video is already flagged");
      }
    }
    else {
      System.out.println("Cannot flag video: Video does not exist");
    }
  }


  public void allowVideo(String videoId) {
    if (checkVideoExist(videoId)){
      Video video = videoLibrary.getVideo(videoId);
      if (video.getFlagStatus() == true) {
        videoLibrary.getVideo(videoId).setFlagStatus(false);
        videoLibrary.getVideo(videoId).setFlagReason("Not supplied");
        System.out.println("Successfully removed flag from video: " + video.getTitle());
      }
      else {
        System.out.println("Cannot remove flag from video: Video is not flagged");
      }
    }
    else {
      System.out.println("Cannot remove flag from video: Video does not exist");
    }
  }
}