var musicDir = process.argv[2];
var ID = process.argv[3];

var YoutubeMp3Downloader = require("youtube-mp3-downloader");

var YD = new YoutubeMp3Downloader({"ffmpegPath": "/usr/bin/ffmpeg", "outputPath": musicDir, "youtubeVideoQuality": "highest", "queueParallelism": 3, "progressTimeout": 2000});

//Download video and save as MP3 file
YD.download(ID);
YD.on("finished", function(err, data) {
console.log(JSON.stringify(data));
console.log("Song successfully downloaded.");
});

YD.on("error", function(error) {
console.log("An error has occured converting youtube to mp3. Please try again. Error: " + error);
});

YD.on("progress", function(progress) {
console.log(JSON.stringify(progress));
});