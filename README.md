# VideoBreak 

## Summary 
**App name**: VideoBreak

**Author**: Teunis van der Hofstad 

Time to kill? Use VideoBreak! VideoBreak is the app that let's you create a video playlist based on your interests and time available. 

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

![Alt text](https://github.com/teunisvdh/ProjectApp/blob/master/doc/Screenoverview.jpg)

**Data sources**

The app will use two APIs: 
- The [YouTube Data API](https://developers.google.com/youtube/v3/getting-started) will be used to search channels with a specific theme. It can also be used to search videos on these channels. 
- The [YouTube Android Player API](https://developers.google.com/youtube/android/player/) will be used to play the videos in the playlist. 

**Changes from initial idea** 

The hardest parts of implementing the app are the following: 
- Searching channels based on current mood(s). This can be done with the YouTube search by keyword, but maybe this doesn't take into account synonyms. Hopefully people have already created a list of YouTube channels sorted by theme.  
- Selecting videos from a channel keeping in mind the amount of time that is available and leaving room for variation in videos (videos from other channels). 
