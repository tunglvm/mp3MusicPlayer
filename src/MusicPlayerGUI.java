import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

public class MusicPlayerGUI extends JFrame{
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

        addGuiComponents();

    }
    private void addGuiComponents(){
        //add toolbar
        addToolBar();
    }

    private void addToolBar(){
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, getWidth(), 20);

        //prevent toolbar from being moved
        toolBar.setFloatable(false);

        //add drop-down menu
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        //add song menu
        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);
        
        add(toolBar);
    }
}
