# REPORT

## Summary 
Everyday people have some time left between activities. However, watching a movie most of the times takes too long to pass the time, but the time is too long to keep staring out of the window. A tool that creates entertainment for just exactly the time you have to pass is needed.

VideoBreak will assure that you can watch a personalised YouTube playlist while traveling, waiting for someone, lunch and many more activities. THe dureation won't exceed the time you have.

![Alt text](https://github.com/teunisvdh/ProjectApp/blob/master/doc/KnipselTime.PNG) 

## Short overview functionality
The project consists of 14 classes: 7 activities, 2 adapter classes, 2 item classes and 3 request classes. The request classes assure that the right data is given to the activities. 

![Alt text](https://github.com/teunisvdh/ProjectApp/blob/master/doc/OverviewApp.jpg)

## Project details
### Activities
**SplashActivity**:

- SplashActivity generates a splash screen containing the app logo. It automatically goes on to TimeActivity after 1.5 seconds.

**ProfileActivity:**

- ProfileActivity can be accessed via the menu. Here, a user can add moods or moments. These are stored with SharedPreferences, allowing to obtain them after closing the app. 
 
**TimeActivity:**

- TimeActivity let's the user choose a maximum time for the playlist. A user can select a moment or input a different time in a textbox. Moments can be edited in ProfileActivity. Time must be between 10 and 180 minutes. 

**MoodActivity:**

- MoodActivity gives the user the possibility to choose one or multiple moods/interests for starting a YouTube query to obtain channels. The moods can be edited in ProfileActivity. 

**RecommendActivity:**

- RecommendActivity shows a list of five recommended YouTube channels. These five will be chosen randomly from a list with five channels for each given mood.

**ShowplaylistActivity:**

- ShowplaylistActivity shows the user the generated playlist. It implements the callback for retrieving the video id's and retrieving the information about the videos. For each five channels, 10 videos will be put in a list. This activity chooses random videos from this list. If a video doesn't fit into the time available, it will go to a random next one until the end of the list. If no other video fits, the playlist is final.

**PlaylistActivity:**

- PlaylistActivity is the activity containing a YouTube Android Player. The playlist (a list of video id's) will be loaded into the player. Video's can be viewed, paused and skipped. Rotating the screen will result in a full screen experience.

### Adapters
**ChannelAdapter:**

- ChannelAdapter is an adapter for creating list items for the RecommendActivity.

**VideoAdapter:**

- VideoAdapter is an adapter for creating list items for the ShowPlayListActivity.

### Items
**ChannelItem:**

- ChannelItem is a class for storing information about a YouTube channel: name of the channel, description, url of the channel image and url/id of the channel.

**VideoItem:**

- VideoItem is a class for storing information about a YouTube video: id of the video, description, corresponding YouTube channel, title of the video, url of the thumbnail and duration (float) of the video.

### Requests
**ChannelRequest**:

- ChannelRequest is a request class for retrieving YouTube channels. For each mood it starts a JSON query for retrieving a maximum of 10 channels associated with the mood. For each channel it created a ChannelItem.

**VideoInfoRequest:**

- VideoInfoRequest is a request class for retrieving YouTube video information. For each retrieved id it starts a JSON query for retrieving information. For each video a VideoItem will be created. This item will be added to a list of videos to choose from for the playlist.

**VideoRequest:**

- VideoRequest is a request class for retrieving YouTube video-ids. For each channel it starts a JSON query for retrieving a maximum of 10 videos. With the id an information request can be started.

## Challenges
- A challenge was saving the moment with SharedPreferences. A hashmap can't be stored directly. Therefore, the hashmap had to be converted into a String. After retrieving the String has to be converted again. 
- I have added another request: VideoInfoRequest. This is because retrieving the videos from a channel doesn't show all information about the video. Therefore, you first have to retrieve the video id's and subsequently do a new request with these id's. 
- Using the duration of a video was a challenge too. 1) This is a String at first and most importantly 2) YouTube doen't use one standard format for time. Videos shorter than 1 minute are notated with '...S', only showing the seconds. Videos longer than 1 minute '...M...S'. Videos of exactly whole minutes '...M'. Videos longer than one hour '...H...M...S', etc. 
I solved the problem with a loop and not retrieving videos longer than one hour. 

## Changes 
- As stated above, the VideoInfoRequest class was added. Also a SplashActivity was added. 
- The planning was to include a login screen with Firebase and storing all data on Firebase. Someone advised me to use SharedPreferences instead, because it is much easier to use. 
- I also added a ChannelItem and ChannelAdapter to show the user wich channels will be used to retrieve videos from. 
