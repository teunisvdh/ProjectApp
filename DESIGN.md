## MVP

![Alt text](https://github.com/teunisvdh/ProjectApp/blob/master/doc/Project_MVP_detail.jpg)

## Full Application

![Alt text](https://github.com/teunisvdh/ProjectApp/blob/master/doc/Project_final_detail.jpg)

## Diagram

![Alt text](https://github.com/teunisvdh/ProjectApp/blob/master/doc/Diagram%20app.jpg)

## Data sources/APIs 

The app will make use of two APIs: 
- The [YouTube Data API](https://developers.google.com/youtube/v3/getting-started) will be used to search channels with a specific theme. It will also be used for searching videos on these channels. It could be used for making a playlist (possibility).  
- The [YouTube Android Player API](https://developers.google.com/youtube/android/player/) will be used to play the videos in the playlist.

## Datbase

While the MVP won't make use of a database the full app will. The database will be accessed to store (and retrieve) user accounts, and moods and journeys for these users. 

Firebase will contain three tables:

- UserAccounts: id, email (Google account) and hashed password

- Moods: id, name 

- Journeys: id, destination1, destination2, travelTime 
