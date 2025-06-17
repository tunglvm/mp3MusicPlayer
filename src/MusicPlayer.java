import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.Buffer;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class MusicPlayer extends PlaybackListener {

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

    void pauseSong(){
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
            advancedPlayer.setPlayBackListener(this);
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

    @Override //this method gets called when the song is finished or if player gets closer
    public void playbackStarted(PlaybackEvent evt) {
        System.out.println("Play Back Started");

    }

    @Override //this method gets called in the beginning of the song
    public void playbackFinished(PlaybackEvent evt) {
        System.out.println("Play Back Finished");

        System.out.println("Stopped @" + evt.getFrame()); //countting miliseconds ==>>  know where user pause the song 
    }


    

    
}
