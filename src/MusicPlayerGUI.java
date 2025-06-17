import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class MusicPlayerGUI extends JFrame{

    //color configuration
    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;

    private MusicPlayer musicPlayer;

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
        JLabel songTitle = new JLabel("Song Title");
        songTitle.setBounds(0, 285, getWidth() - 10, 30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);

        //song artist
        JLabel songArtist = new JLabel("Artist");
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
        JPanel playbackButton = new JPanel();
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
