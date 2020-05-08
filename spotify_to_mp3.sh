#!/bin/bash

# Author: Pranav Ashok
# Since: 05/07/20
# Version: 1.0
# spotify_to_mp3.sh - A script to locally download playlists or songs from Spotify as mp3 files

# Install dependencies
sudo apt update
sudo apt install ffmpeg
sudo apt install npm
sudo apt-get install java-common
sudo apt-get install default-jdk
npm install youtube-mp3-downloader --save
clear
RED='\033[0;31m'
NC='\033[0m'

# Handle arguments
argNUM=1
for arg in "$@"

    do
        # Help event
        if [ "$arg" == "--help" ] || [ "$arg" == "-h" ]
        then
            printf "\n${RED}(HELP) Here is a list of commands you can use:${NC}"
            printf "\n--help | -h (For a list of commands)"
            printf "\n--playlist playlistURL\n"
        fi

        # Playlist event
        if [ "$arg" == "--playlist" ]
        then
            INDEX=$((argNUM+1)) 
            eval url='$'$INDEX
            if [ -z "$url" ]
            then
                printf "\n${RED}Make sure to enter the playlist URL after '--playlist '!${NC}\n"
            fi
            
            # Get YouTube links for playlist
            javac -cp "./lib/jsoup-1.13.1.jar" SpotifyScraper.java
            YT_URL_TEMP=$(java -cp "./lib/jsoup-1.13.1.jar" SpotifyScraper.java $url)
            YT_URLS=( $YT_URL_TEMP )

            # Download each song as MP3
            for video_id in "${YT_URLS[@]}";
                do echo "$video_id";
            done
        fi

        argNUM=$((argNUM+1)) 
done

if [ $argNUM == 1 ]
then
    printf "\n${RED}(HELP) Here is a list of commands you can use:${NC}"
    printf "\n--help | -h (For a list of commands)"
    printf "\n--playlist playlistURL\n"
fi
