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

    //store the last frame when the playback is finished
    private int currentFrame;

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
        if(currentSong == null){
            return; // prevent user press "play" before load songs
        }
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
                    if(isPause){
                        //resume music from the last frame when music is pause
                        advancedPlayer.play(currentFrame, Integer.MAX_VALUE);
                    }                                          //MAX to capture all lengths of songs
                    else{
                        //playing music from start
                        advancedPlayer.play();
                    }
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

        
         
        if(isPause){
            // ||evt.getFrame() x (total frame / ms(song length)) = current frame||
            currentFrame += (int) ((double) evt.getFrame() * currentSong.getFramRatePerMiliseconds()); 
            //"The play (int start, int end)"
            //JLayer ADVANDCEDPLAYER expect "frame", not "miliseconds" value ==>> convert milisecond to frame

            
        }
    }
}
