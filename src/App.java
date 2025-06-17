import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                new MusicPlayerGUI().setVisible(true);

                //call Song song constructor
                // Song song = new Song("mp3MusicPlayer\\test song\\Anh Thanh Nien - HuyR (HQ).mp3"); 
                // System.out.println(song.getSongTitle());
                // System.out.println(song.getSongArtist());
            }
        });
    }
}

