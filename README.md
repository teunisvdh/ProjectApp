# ProjectApp

## Summary 
**App name**: VideoTrain

**Author**: Teunis van der Hofstad 

VideoTrain is for everyone that doesn't know what to do while traveling by train. It can create a video playlist based on your interests and travel time. 

## Problem Statement 
Everyday millions of people travel by train to go to work or school. 
For a traveler this trip can be very boring. 
The period of time spent in the train usually is too short for doing a lot of work, but too much for doing nothing. 
Most people reach out to their phones, but even then don't know what to do exactly: watching a part of a Netflix series or browsing through Facebook until reaching the destination? 

## Solution 
This app will assure that you can watch a personalised YouTube playlist while traveling, adjusted to the time you are going to travel.  


**Functions for the MVP**
- Put in time to travel
- Check boxes for video themes
- Load possible YouTube channels 
- Check boxes for channels 
- Create playlist 
- Start/stop playlist 

![Alt text](https://github.com/teunisvdh/ProjectApp/blob/master/doc/jpg_Tekengebied%201%404x-100.jpg)

**Optional functions**
- Create account 
- Add interests to account 
- Store travel time of mostly used trips. For example home-university, home-work. 

## Prerequisites 

**Data source**

The app will use two APIs: 
- The [YouTube Data API](https://developers.google.com/youtube/v3/getting-started) will be used to search channels with a specific theme. It can also be used to search videos on these channels. 
- The [YouTube Android Player API](https://developers.google.com/youtube/android/player/) will be used to play the videos in the playlist. 

**External components** 
- For storing the interests/moods [Firebase](https://firebase.google.com/) will be used. 

**Similar mobile apps**

- The YouTube app assures you can make playlists of videos. These playlist aren't based on interests immediately and can't be made with a maximum playtime automatically. 
- Spotify creates music playlists based on your favourite genres automatically. The app won't take in account your maximum time that is available. 

**Hardest parts** 

The hardest parts of implementing the app are the following: 
- Searching channels with current mood(s). This can be done with the YouTube search by keyword, but maybe this doesn't take into account synonyms. Maybe people have already created a list of YouTube channels by theme.  
- ...
