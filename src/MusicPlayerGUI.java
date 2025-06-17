import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

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

    private JLabel songTitle, songArtist;

    private JPanel playbackButton;

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
        JSlider playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
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
                jFileChooser.showOpenDialog(MusicPlayerGUI.this);
                File selectedFile = jFileChooser.getSelectedFile();

                if(selectedFile != null){

                    //creat a song based on selected file
                    Song song = new Song(selectedFile.getPath());

                    //load song in musicplayer
                    musicPlayer.loadSong(song);

                    //update song title and artist
                    updateSongTitleAndArtist(song);

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
        prevButton.setBorderPainted(false);
        prevButton.setBackground(null);
        playbackButton.add(prevButton);

        //play button
        JButton playButton = new JButton(loadImage("D:\\musucPlayer\\mp3MusicPlayer\\src\\assets\\play.png"));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        playbackButton.add(playButton);

        //pause butoon
        JButton pauseButton = new JButton(loadImage("D:\\musucPlayer\\mp3MusicPlayer\\src\\assets\\pause.png"));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        playbackButton.add(pauseButton);

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
