import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This web scraper program extracts spotify playlist data from the playlist URL and accurately
 * finds the youtube URL for every song in the playlist
 * 
 * NOTE: For now, this program has limited capability. It can only perform conversions for up to 30
 * songs in a playlist. If there are more than 30 songs in the playlist, it will convert the first
 * 30 songs.
 *
 * @author Pranav Ashok
 * @version 1.0
 * @since 2020-05-01
 */

public class SpotifyScraper {
  static int numTracks = 0;

  /**
   * Reads input and gets the Youtube URLs for the song(s) provided by the user
   * 
   * @param args an array that holds user input (url of their song/playlist)
   * 
   */
  public static void main(String[] args) {
    // obtain user input (should be a song or playlist URL)
    String url = args[0];

    // run methods in order to eventually get the youtube URLs of the song(s)
    String[][] info = getPlaylistInfo(url);
    String[] searchURLs = getYoutubeSearchURLs(info);

    getYoutubeURLs(info, searchURLs);
  }

  /**
   * Creates a 2d array with track names, artists and albums for the given song(s)
   * 
   * @param url the URL provided by the user (Spotify song/playlist url)
   * @return a 2d array containing the track name, artist and album for the given song(s)
   * 
   */
  private static String[][] getPlaylistInfo(String url) {
    String[][] info = new String[30][3];

    try {
      Document doc = Jsoup.connect(url).userAgent("Mozilla/17.0").get();

      Elements tracks = doc.select("span.track-name");

      Elements temp = doc.select("span.artists-albums");

      System.out.println(tracks.size());
      for(Element track : tracks) {
        System.out.println(track.text());
      }
      int index = 0;
      for (Element artistAndAlbum : temp) {
        ArrayList<String> artistsAndAlbums =
            (ArrayList<String>) artistAndAlbum.getAllElements().eachText();
        int numArtists = artistsAndAlbums.size();
        String artists = artistsAndAlbums.get(2);

        for (int i = 3; i < numArtists - 2; i += 2) {
          artists = artists + ", " + artistsAndAlbums.get(i);
        }
        String album = artistsAndAlbums.get(numArtists - 1);
        info[index][0] = artists;
        info[index][1] = album;

        System.out.println("Number of elements: " + numArtists);
        System.out.println("Elements: " + artistsAndAlbums);
        System.out.println("Artist: " + artists);
        System.out.println("Album: " + album);
        System.out.println(info[0][1]);
        index++;
      }


      for (Element track : tracks) {
        String trackName = track.getAllElements().text();

        info[numTracks][2] = trackName;
        numTracks++;

        System.out.println(numTracks + ") " + trackName);
      }
    }

    catch (IOException e) {
      System.out.println("You have entered an invalid URL.");
    }
    return info;
  }

  /**
   * Creates the URLs of youtube searches for the given song(s)
   * 
   * @param info the 2d String array that contains info about the given song(s) (track name, artist,
   *             album)
   * @return a String array containing the URLs of youtube searches for the given song(s)
   * 
   */
  private static String[] getYoutubeSearchURLs(String[][] info) {
    String[][] infoYT = info;
    int rows = info.length;
    int cols = info[0].length;
    System.out.println("# of tracks: " + numTracks);
    for (int row = 0; row < numTracks; row++) {
      for (int col = 0; col < cols; col++) {
        String str = info[row][col];
        if (str != null) {
          int chars = str.length();

          for (int letter = 0; letter < chars - 1; letter++) {
            if (str.charAt(letter) == ' ') {
              str = str.substring(0, letter) + "+" + str.substring(letter + 1);
            }
            infoYT[row][col] = str;

          }
        }
      }
    }

    String searchURL = "https://www.youtube.com/results?search_query=";
    String[] searchURLs = new String[numTracks];

    for (int i = 0; i < numTracks; i++) {
      if (infoYT[i][0] == null) {
        infoYT[i][0] = "";
      } else {
        infoYT[i][0] = infoYT[i][0] + "+";
      }
      String url = searchURL + infoYT[i][0] + infoYT[i][2];
      searchURLs[i] = url;
    }
    for (int i = 0; i < numTracks; i++) {
      System.out.println(searchURLs[i]);
    }
    return searchURLs;
  }

  /**
   * Creates the URLs of youtube searches for the given song(s)
   * 
   * @param info the 2d String array that contains info about the given song(s) (track name, artist,
   *             album)
   * @return a String array containing the URLs of youtube searches for the given song(s)
   * 
   */
  private static String[] getGoogleSearchURLs(String[][] info) {
    String[][] infoYT = info;
    int rows = info.length;
    int cols = info[0].length;
    System.out.println(numTracks);
    for (int row = 0; row < numTracks; row++) {
      for (int col = 0; col < cols; col++) {
        String str = info[row][col];
        if (str != null) {
          int chars = str.length();

          for (int letter = 0; letter < chars - 1; letter++) {
            if (str.charAt(letter) == ' ') {
              str = str.substring(0, letter) + "+" + str.substring(letter + 1);
            }
            infoYT[row][col] = str;

          }
        }
      }
    }

    String searchURL = "https://google.com/search?q=";
    String[] searchURLs = new String[numTracks];

    for (int i = 0; i < numTracks; i++) {
      if (infoYT[i][0] == null) {
        infoYT[i][0] = "";
      } else {
        infoYT[i][0] = infoYT[i][0] + "+";
      }
      String url = searchURL + infoYT[i][0] + infoYT[i][2] + "+" + "youtube";
      searchURLs[i] = url;
    }
    for (int i = 0; i < numTracks; i++) {
      System.out.println(searchURLs[i]);
    }
    return searchURLs;
  }

  /**
   * gets the youtube url(s) for the song(s) provided by the user
   * 
   * @param n the value passed into the two methods in ComparisonMethods as the parameter
   * @return a formatted string that includes: n, the time it took for the bruteForce method to run,
   *         and the time it took for the constantTime method to run.
   * @throws NoSuchElementException with error message if the two methods don't return the same
   *                                value
   * 
   */
  public static String[] getYoutubeURLs(String[][] info, String[] searchURLs) {
    String[] youtubeURLs = new String[numTracks];
    int index = 0;
    int count = 0;
    int maxTries = 3;
    for (String url : searchURLs) {
      while (true) {
        try {
          Document doc = Jsoup.connect(url).userAgent("Mozilla").ignoreHttpErrors(true).get();
          Elements linkHref = doc.select("body > script");
          String videoId = linkHref.get(8).data();
          videoId = videoId.substring(3000, 3500);
          for (int i = 0; i < videoId.length() - 9; i++) {
            if (videoId.substring(i, i + 10).equals("\"videoId\":")) {
              videoId = videoId.substring(i + 11, i + 22);
            }
          }
          if(videoId.length()==11) {
            //System.out.println(videoId);
            youtubeURLs[index] = videoId;
            index++;
            break;
          }     
        } catch (StringIndexOutOfBoundsException e) {
          if(++count == maxTries) {
            throw e;
          }
        } catch (IOException e) {
          System.out.println("Couldn't fetch youtube URL.");
          e.printStackTrace();
        }
      }
    }
    return youtubeURLs;
  }
}
