import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobName;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MusicPlayerGUI extends JFrame{

    //color configuration
    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;

    private MusicPlayer musicPlayer; //creat music player

    //use file explorer in app
    private JFileChooser jFileChooser;

    //creat panel to display title and artist
    private JLabel songTitle, songArtist;

    //creat button
    private JPanel playbackButton;

    //creat slider
    private JSlider playbackSlider;

    public MusicPlayerGUI(){
        //calls JFrame constructor out GUI and set header to "musicPlayer"
        super("Music Player");

        //set width and height
        setSize( 400, 600);

        //end process when app close
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //launch the app at the center of screen
        setLocationRelativeTo(null);

        //prevent app from resize
        setResizable(false);

        setLayout(null);
        
        //Change th frame color
        getContentPane().setBackground(FRAME_COLOR);

        musicPlayer = new MusicPlayer();

        jFileChooser = new JFileChooser();
        //set a default PATH for filr explorer
        jFileChooser.setCurrentDirectory(new File("mp3MusicPlayer\\test song"));
        //fiter file chooser to only show .mp3 file
        jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));

        addGuiComponents();

    }
    private void addGuiComponents(){
        //add toolbar
        addToolBar();

        //load record image
        JLabel songImage = new JLabel(loadImage("D:\\musucPlayer\\mp3MusicPlayer\\src\\assets\\record.png"));
        songImage.setBounds(0, 50, getWidth() - 20, 225 );
        add(songImage);

        //Song title
        songTitle = new JLabel("Song Title");
        songTitle.setBounds(0, 285, getWidth() - 10, 30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);

        //song artist
        songArtist = new JLabel("Artist");
        songArtist.setBounds(0, 315, getWidth() - 10, 30);        //set bound
        songArtist.setFont(new Font("Dialog", Font.PLAIN, 24));    //set font
        songArtist.setForeground(TEXT_COLOR);                                //set text color
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);            //set algnment
        add(songArtist);

        //Playback slider
        playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(getWidth()/2 - 300/2, 365, 300, 40 );
        playbackSlider.setBackground(null);
        add(playbackSlider);

        //playback button
        addPlaybackButtons();
    }

    private void addToolBar(){
        JToolBar toolBar = new JToolBar();                    //Creat a "toolbar"
        toolBar.setBounds(0, 0, getWidth(), 20);   //Set tthe "toolbar"

        //prevent toolbar from being moved
        toolBar.setFloatable(false);

        JMenuBar menuBar = new JMenuBar();                    //creat a "menu bar"  
        toolBar.add(menuBar);                                 //add "menu bar" to the "tool bar"



        //add song menu => loading option
        //add drop-down menu name "song"
        JMenu songMenu = new JMenu("Song");                  //creat a "song" button 
        menuBar.add(songMenu);                                 //add the "song" button to "menu bar"

        //add "load song" option in the menu
        JMenuItem loadSong = new JMenuItem("Load Song");  //creat"load song" option 
        loadSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //on int is return to let progeam know what user did ==> prevent load song when user "cancel"
                int result = jFileChooser.showOpenDialog(MusicPlayerGUI.this); 
                File selectedFile = jFileChooser.getSelectedFile();  //creat file which is choosen

                //use result to check if user click the "open" button
                if(result == JFileChooser.APPROVE_OPTION && selectedFile != null){

                    //creat a song based on selected file
                    Song song = new Song(selectedFile.getPath());

                    //load song in musicplayer
                    musicPlayer.loadSong(song);

                    //update song title and artist
                    updateSongTitleAndArtist(song);

                    //update playback slider
                    updatePlaybackSlider(song);

                    //toggle on pause button and turn on play button
                    enablePauseButtonDisablePlayButton();


                }
            }
        });
        songMenu.add(loadSong);                                //add "load song" option to "song" drop-down menu
        
        

        //add song menu => loading option
        //add drop-down menu name "playlist"
        JMenu playlistMenu = new JMenu("Playlist");          //creat a "playlist" button 
        menuBar.add(playlistMenu);                             //add the "playlist" button to "menu bar"

        //add items to playlist menu
        JMenuItem creatPlaylist = new JMenuItem("Creat Playlist");  //creat "creat playlist" option 
        playlistMenu.add(creatPlaylist);                                 //add "creat playlist" option to "play list" drop-down menu
        
        JMenuItem loadPlaylist = new JMenuItem("Load Playlist");    //creat "load playlist" option 
        playlistMenu.add(loadPlaylist);                                  //add "load playlist" option to "play list" drop-down menu

        add(toolBar);   
    }

    private void addPlaybackButtons(){
        //creat and add button
        playbackButton = new JPanel();
        playbackButton.setBounds(0, 435, getWidth() - 10, 80);
        playbackButton.setBackground(null);

        //previous button
        JButton prevButton = new JButton(loadImage("D:\\musucPlayer\\mp3MusicPlayer\\src\\assets\\previous.png"));
        prevButton.setBorderPainted(false);  //set none to border
        prevButton.setBackground(null);     //set none background
        playbackButton.add(prevButton);        //show the button

        //play button
        JButton playButton = new JButton(loadImage("D:\\musucPlayer\\mp3MusicPlayer\\src\\assets\\play.png"));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        playButton.addActionListener(new ActionListener() {  //CREAT RESUME FUNCTION
            @Override 
            public void actionPerformed(ActionEvent e){
                //toggle off the "Play Button" and toogle of the "Pause Button"
                enablePauseButtonDisablePlayButton();

                //play or resume song
                musicPlayer.playCurrentSong();
            }
        });
        playbackButton.add(playButton);       //show the button

        //pause butoon
        JButton pauseButton = new JButton(loadImage("D:\\musucPlayer\\mp3MusicPlayer\\src\\assets\\pause.png"));
        pauseButton.setBorderPainted(false); 
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        pauseButton.addActionListener(new ActionListener() {  //CREAT PAUSE FUNCTION
            @Override
            public void actionPerformed(ActionEvent e){
                //toggle off the "Pause Button" and toogle of the "Play Button"
                enablePlayButtonDisablePauseButton();

                //pause the song
                musicPlayer.pauseSong();
            }
        });
        playbackButton.add(pauseButton);             //show the button

        //next button
        JButton nextButton = new JButton(loadImage("D:\\musucPlayer\\mp3MusicPlayer\\src\\assets\\next.png"));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        playbackButton.add(nextButton);

        add(playbackButton);
    }   

    private void updateSongTitleAndArtist(Song song){
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }

    private void updatePlaybackSlider(Song song){
        //update MAX count for slider
        //set the MAX value of slider == song's length
        playbackSlider.setMaximum(song.getMp3File().getFrameCount()); 

        //creat song length lable by using "Hashtable" and "JLabel"
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

        //set the beginning of song is 00:00
        JLabel labelBeginning = new JLabel("00:00");
        labelBeginning.setFont(new Font("Dialog", Font.BOLD, 18));
        labelBeginning.setForeground(TEXT_COLOR);

        //set the end of song
        JLabel labelEnd = new JLabel(song.getSpngLenght());
        labelEnd.setFont(new Font("Dialog", Font.BOLD, 18));
        labelEnd.setForeground(TEXT_COLOR);

        //place coponents in table
        labelTable.put(0, labelBeginning);
        labelTable.put(song.getMp3File().getFrameCount(), labelEnd);

        playbackSlider.setLabelTable(labelTable);
        playbackSlider.setPaintLabels(true);

    }

    private void enablePauseButtonDisablePlayButton(){
        //retrieve reference to play button from playbackbutton panel
        JButton playButton = (JButton) playbackButton.getComponent(1);
        JButton pauseButton = (JButton) playbackButton.getComponent(2);

        //turn off play button when music is playing
        playButton.setVisible(false);    //set the visible of playButton to off
        playButton.setEnabled(false);        //set the enable of playButton to off

        //turn on pause button 
        pauseButton.setVisible(true);    //set the visible of pauseButton to on
        pauseButton.setEnabled(true);        //set the enable of pauseButton to on
    }

     private void enablePlayButtonDisablePauseButton(){
        //retrieve reference to play button from playbackbutton panel
        JButton playButton = (JButton) playbackButton.getComponent(1);
        JButton pauseButton = (JButton) playbackButton.getComponent(2);

        //turn off play button when music is playing
        playButton.setVisible(true);    //set the visible of playButton to off
        playButton.setEnabled(true);        //set the enable of playButton to off

        //turn on pause button 
        pauseButton.setVisible(false);    //set the visible of pauseButton to on
        pauseButton.setEnabled(false);        //set the enable of pauseButton to on
    }


    private ImageIcon loadImage(String imagePATH){
        try{

            //read the image file from the PATH
            BufferedImage image = ImageIO.read(new File(imagePATH));

            //return image  icon
            return new ImageIcon(image);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //could not find result
        return null;
    }
    
}
