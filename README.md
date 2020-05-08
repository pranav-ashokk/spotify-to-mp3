***
**NOTE**

This software is still being worked on and currently has limited capability. The following limitations will be addressed in the next release:
* Only runs on Linux (Any flavor)
* It can only perform downloads for up to 30 songs in a playlist. 
  If there are more than 30 songs in the playlist, it will download the first 30 songs.
***

# Spotify MP3 Downloader
## Overview
With this CLI application, you can locally download your Spotify playlists (or songs) as MP3 files. It is recommended that you have some experience with using the Linux command line. 
<!---
You can also download YouTube videos as MP3 files. Additionally, this sofware also provides unique features such as downloading your playlists/songs in an 8D version (some songs may not supported) or in an instrumental/karaoke version.
-->

## Getting Started
### Installation
To checkout the project from Github to a local folder, you can use the following git command in the terminal:

`git clone https://github.com/pranav-ashokk/spotify-to-mp3.git`

NOTE: You can checkout the project in any local directory.

### Dependencies (Automatic Installation)
Dependencies can be automatically installed by simply running the setup.sh script. Before you can run the setup script, you will need to make the script executable on your system. First, navigate to the folder where you checked out the project to (in your console). Then, use the following command:

`chmod +x setup.sh`

Then, simply execute the script to automatically install the dependencies:

`./setup.sh`

### Dependencies (Manual Installation)
If you have any issues with the automatic installation or prefer to install them manually, then you can use the download links below.
* #### FFmpeg
  https://www.ffmpeg.org/download.html
  Or, download FFmpeg in the terminal with this command:

  `sudo apt install ffmpeg`
* #### node.js
  https://nodejs.org/en/download/
* #### youtube-mp3-downloader
  Download youtube-mp3-downloader in the terminal:

  `npm install youtube-mp3-downloader`
* #### Java Runtime Environment (JRE)
  https://www.oracle.com/java/technologies/javase-jre8-downloads.html

### Setup
Before you can run the main script, you will need to make the script executable on your system. First, navigate to the folder where you checked out the project to (in your console). Then, use the following command:

`chmod +x spotify_to_mp3.sh`

## Running
Navigate to the folder where you checked out the project to (in your console). Now use the following command to see the different options/features of the application.

`./spotify_to_mp3.sh --help`

### Downloading playlists
Use the `--playlist playlistURL` option to download a Spotify playlist. To get the URL for a playlist, open the playlist you want to download and click on the three dots towards the top. Go to 'Share' -> 'Copy Playlist link' and paste it directly into the terminal.

## Issues
If you find any issues, please file a report ![here](https://github.com/pranav-ashokk/spotify-to-mp3/issues).
## Packages
Third party packages/libraries:
* jsoup
  https://jsoup.org/
* youtube-mp3-downloader
  https://github.com/ytb2mp3/youtube-mp3-downloader
