import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.Buffer;

import javazoom.jl.player.advanced.AdvancedPlayer;

public class MusicPlayer {

    private Song currentSong; //creat class song to store songs
    //call the constructor in class "Song"

    //use Jlayer library to create advancedPlayer -->> playing music
    private AdvancedPlayer advancedPlayer;

    //pause: use boolean flag to indicate wether the player has been pause or not
    private boolean isPause;

    //constructor
    public MusicPlayer(){

    }
    

    //functione to load song
    public void loadSong(Song song){
        currentSong = song;

        if(currentSong != null){
            playCurrentSong();
        }
    }

    private void pauseSong(){
        if(advancedPlayer != null){
            //update pause flag
            isPause = true;
            // ==> stop the player
            stopSong();
        }
    }

    public void stopSong(){
        if(advancedPlayer != null){
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }

    //function to play song
    public void playCurrentSong(){
        try{
            //read mp3 audio data
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePATH());    //get filePATH from currentSong
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            // creat a new advanced player
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);

            //start playing music
            startMusicThread();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void startMusicThread(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    //playing music
                    advancedPlayer.play();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
