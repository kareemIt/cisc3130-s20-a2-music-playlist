import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

public class MusicPlayer {

    private History history;
    private Playlist playlist;
    private Song currentSong;

    public MusicPlayer(String[] files) throws IOException{
    	LinkedList<Song> list1 = readFile(files[0]);
        LinkedList<Song> list2 = readFile(files[1]);

        LinkedList<Song> songList = mergeLists(list1, list2);
        
        history = new History();
        playlist = new Playlist(songList);
    }
      //merges the lists to make one playlist.
    public LinkedList<Song> mergeLists(LinkedList<Song> list1, LinkedList<Song> list2) {
        LinkedList<Song> newList = new LinkedList<>();
        int minSize = Integer.min(list1.size(), list2.size());
        int counter1 = 0;
        int counter2 = 0;
        while(counter1 != minSize && counter2 != minSize) {
            Song song1 = list1.get(counter1);
            Song song2 = list2.get(counter2);
            
            if(song1.getName().compareToIgnoreCase(song2.getName()) == 0) {
            	newList.add(song1);
            	counter1++;
            	counter2++;
            } else if(song1.getName().compareToIgnoreCase(song2.getName()) < 0) {
                newList.add(song1);
                counter1++;
            } else {
                newList.add(song2);
                counter2++;
            }
        }
        for(int i = counter1; i < list1.size(); i++) {
            newList.add(list1.get(i));
        }

        for(int j = counter2; j < list2.size(); j++) {
            newList.add(list2.get(j));
        }

        return newList;
    }
      //translates CSV file into a string arr for the queue.
    public LinkedList<Song> readFile(String filename) throws IOException {
        File file = new File(filename);
        LinkedList<Song> songs = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine();
        reader.readLine();
        String line = reader.readLine();
        String[] arr;
        //skips all the commas in the CSV file.
        do {
            arr = line.split(",");
            String songName = arr[1].replace("\"", "");
            String artist = arr[2].replace("\"", "");
            songs.add(new Song(songName, artist));
            line = reader.readLine();
        } while(line != null);

        reader.close();

        Collections.sort(songs);

        return songs;

    }

    //gets the next song in the playlist.
    public Song nextSong() {
        history.addSong(currentSong);
        currentSong = playlist.nextSong();
        return currentSong;
    }
    //Gets the prevoius song played.
    public Song prevSong() {
        playlist.addSongToFront(currentSong);
        currentSong = history.prevSong();
        return currentSong;
    }
    //returns playlist 
    public Playlist getPlaylist() {
        return playlist;
    }
    //Gets the history of what songs were played.
    public History getHistory() {
        return history;
    }
    //returns currentsong.
    public Song getCurrentSong() {
        return currentSong;
    }

    public static void main(String[] args) throws IOException {
        String[] files = {"src/week1.csv", "src/week2.csv"};
        MusicPlayer player = new MusicPlayer(files);
        //Songplaylist.
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        
        System.out.println("------------------------");
        
        System.out.println("Now Playing(Prev Song):" + player.prevSong());
        System.out.println("Now Playing(Prev Song):" + player.prevSong());
        System.out.println("Now Playing(Prev Song):" + player.prevSong());
        System.out.println("Now Playing(Prev Song):" + player.prevSong());
        System.out.println("Now Playing(Prev Song):" + player.prevSong());
        
        System.out.println("------------------------");
        
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        System.out.println("Now Playing(Next Song): " + player.nextSong());
        System.out.println("Now Playing(Next Song): " + player.nextSong());
     
        

    }

}

class Song implements Comparable{
    //this compares the artists/songs.
    private String name, artist;

    public Song(String name, String artist) {
            this.name = name;
            this.artist = artist;
    }
    //returns name of the song
    public String getName() {
        return name;
    }
    //returns artists name.
    public String getArtist() {
        return artist;
    }

    public String toString() {
        return name + " " + artist;
    }
    //this overrides the compareTo method so that so it compares names against over names.
    @Override
    public int compareTo(Object o) {
        return name.compareToIgnoreCase(((Song)o).name);
    }

}

class History {

    Deque<Song> history;

    public History() {
        history = new LinkedList<>();
    }
    
     //returns the last song listened to.  
    public Song prevSong() {
        return history.pop();
    }
    // adds a song to the history.
    
    public void addSong(Song s) {
        history.push(s);
    }

}

class Playlist {
    //Linkedlist for the playlist
    LinkedList<Song> playlist;

    public Playlist(LinkedList<Song> list) {
        playlist = list;
    }
    //plays the next song in the list.
    public Song nextSong() {
        return playlist.poll();
    }
    //This adds the song to the back of the list.
    public void addSongToBack(Song s) {
        playlist.offer(s);
    }
    //This adds the song to the front of the list.
    public void addSongToFront(Song s) {
        playlist.push(s);
    }
    
}
