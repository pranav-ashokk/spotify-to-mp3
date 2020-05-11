package spotify_mp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
* This web scraper program extracts spotify playlist data from the playlist URL
* and accurately finds the youtube URL for every song in the playlist
* 
* NOTE: For now, this program has limited capability. It can only perform conversions for up to 30 songs in a playlist.
* 		If there are more than 30 songs in the playlist, it will convert the first 30 songs.
*
* @author  Pranav Ashok
* @version 1.0
* @since   2020-05-01 
*/

public class SpotifyScraper{
	static int numTracks = 0;

	public static void main(String[] args) {
		String url = args[0];

		getYoutubeURLs(url);
	}

	private static String[][] getPlaylistInfo(String url) {
		String[][] info = new String[30][3];

		try {
			Document doc = Jsoup.connect(url).userAgent("Mozilla/17.0").get();

			Elements tracks = doc.select("span.track-name"); 
			//ArrayList<String> trackNames = new ArrayList<String>();

			Elements temp = doc.select("span.artists-albums");
			ArrayList<String> artistsAlbums = new ArrayList<String>();
			//ArrayList<String> artists = new ArrayList<String>();
			//ArrayList<String> albums = new ArrayList<String>();

			int index = 0;
			for(Element artistAndAlbum:temp) {			
				ArrayList<String> artistsAndAlbums = new ArrayList<String>();
				artistsAndAlbums = (ArrayList<String>) artistAndAlbum.getAllElements().eachText();
				int numArtists = artistsAndAlbums.size();
				String artists = artistsAndAlbums.get(2);

				for(int i = 3; i<numArtists-2; i+=2) {
					artists = artists + ", " + artistsAndAlbums.get(i);
				}
				String album = artistsAndAlbums.get(numArtists-1);
				info[index][0] = artists;
				info[index][1] = album;

				//System.out.println("Number of elements: " + numArtists);
				//System.out.println("Elements: " + artistsAndAlbums);
				//System.out.println("Artist: " + artists);
				//System.out.println("Album: " + album);
				index++;
			}


			for(Element track:tracks) {
				String trackName = track.getAllElements().text();

				info[numTracks][2] = trackName;
				numTracks++;

				//System.out.println(numTracks + ") " + trackName);
			}
		}

		catch (IOException e) {
			System.out.println("You have entered an invalid URL.");
		}
		return info;
	}


	//possibly deprecate and simply use numTracks as num of rows when getting youtube URLs
	private static String[][] cleanUp(String[][] info){
		int cols = info[0].length;
		String[][] newArray = new String[numTracks][cols];

		for(int row=0; row<numTracks; row++) {
			for(int col=0; col<cols; col++) {
				newArray[row][col] = info[row][col];
			}
		}
		return newArray;
	}

	private static String[] getYoutubeSearchURLs(String[][] info){
		String[][] infoYT = info;
		int rows = info.length;
		int cols = info[0].length;

		for(int row=0; row<rows; row++) {
			for(int col=0; col<cols; col++) {
				String str = info[row][col];
				int chars = str.length();

				for(int letter = 0; letter<chars-1; letter++) {
					if(str.charAt(letter) == ' ') {
						str = str.substring(0, letter) + "+" + str.substring(letter+1);
					}
				infoYT[row][col] = str;

				}
			}
		}

		String searchURL = "https://www.youtube.com/results?search_query=";
		String[] searchURLs = new String[infoYT.length];

		for(int i=0; i<searchURLs.length; i++) {
			String url = searchURL + infoYT[i][0] + "+" + infoYT[i][2];
			searchURLs[i] = url;
		}

		return searchURLs;
	}

	public static String[] getYoutubeURLs(String playlistURL){
		String[][] info = getPlaylistInfo(playlistURL);
		String[] searchURLs = getYoutubeSearchURLs(info);
		String[] youtubeURLs = new String[searchURLs.length];

		Document doc = null;

		int index = 0;
		for(String url : searchURLs) {
			try {
				doc = Jsoup.connect(url).userAgent("Mozilla/17.0").get();
				Elements linkHref = doc.select("a[href^=/watch]");
				while(linkHref.eachText().size() ==0) {
					doc = Jsoup.connect(url).userAgent("Mozilla/17.0").get();
					linkHref = doc.select("a[href^=/watch]");
				}
				String link = linkHref.get(0).attr("href").substring(9); 
				youtubeURLs[index] = link;
				System.out.println(link);
				index++;
			} 
			catch (IOException e) {
				System.out.println("Couldn't fetch youtube URL.");
				e.printStackTrace();
			}
		}
		return youtubeURLs;
	}

}
