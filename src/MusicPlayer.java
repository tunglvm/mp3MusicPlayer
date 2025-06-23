import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.Buffer;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class MusicPlayer extends PlaybackListener {

    //music slider only active when isPause is false
    //sometime isPause = true ==>> not sync
    //use to update isPause more synchronously
    private static final Object playSignal = new Object();

    //this is the reference to update GUI in this class
    private MusicPlayerGUI musicPlayerGUI;

    private Song currentSong; //creat class song to store songs
    //tupe: song
    public Song getCurrentSong(){
        return currentSong;
    }

    //use Jlayer library to create advancedPlayer -->> playing music
    private AdvancedPlayer advancedPlayer;

    //pause: use boolean flag to indicate wether the player has been pause or not
    private boolean isPause;

    //store the last frame when the playback is finished
    private int currentFrame;
    public void setCurrentFrame(int frame){
        currentFrame = frame;
    }

    //track how many miliseconds has passed since playing song
    //use for updating slider
    public int currentTimeInMiliseconds;
    public void setCurrentTimeInMiliseconds(int timeInMiliseconds){
        currentTimeInMiliseconds = timeInMiliseconds;
    }

    //constructor
    public MusicPlayer(MusicPlayerGUI musicPlayerGUI){
        this.musicPlayerGUI = musicPlayerGUI;
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

            //star playback slider thread
            startPlaybackSliderThread();
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
                        synchronized(playSignal){
                            //bcause update slider thread will not work unless isPause is false
                            //==>>  update flag
                            isPause = false;

                            //notify the other thread to continue
                            //make sure isPause is false properly
                            playSignal.notify();
                        }

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

    //creat thread that handle updating slider
    private void startPlaybackSliderThread(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                if(isPause){
                    try{
                        //wait till its gets notified bt other thread to continue
                        //make sure isPause boolean flag is false properly before continuing
                        synchronized(playSignal){
                            playSignal.wait();
                            //this(startPlaybackSliderThread) thread will wait
                            //while plauMusicThread will execute ==>> this(startMusicThread) will update isPause flag to false
                            //notify the update slider thread to continue
                            //then both 2 thread will continue
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                }

                System.out.println("isPause: " + isPause);

                while(!isPause){   //set the condition is when it is not pause
                    try{
                    //increment current time in miliseconds
                    currentTimeInMiliseconds++;

                    //caculate into frame value
                    //this value is not accurate
                    //value of currentTimeInMiliseconds() is not matching with getFrame()
                    //==>> need to mutiply to 2.08 to make its accurate (but not 100% accurate)
                    int calculatedFrame = (int) ((double) currentTimeInMiliseconds * 2.08 * currentSong.getFramRatePerMiliseconds());

                    //update GUI
                    //need a reference to the GUI class
                    musicPlayerGUI.setPlaybackSliderValue(calculatedFrame);

                    //mimic 1 miliseconds using thread.sleep
                    Thread.sleep(1);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
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
