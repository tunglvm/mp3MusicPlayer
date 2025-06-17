import com.mpatric.mp3agic.Mp3File;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import java.io.File;
import javax.sound.sampled.AudioFileFormat;

//class used to describe song
public class Song {
    private String songTitle;
    private String songArtist;
    private String spngLenght;
    private String filePATH;


    /**
     * use jaudiotagger(lib) to creat an audiofile obj to read mp3 file's information
     * @param filePATH
     */
    public Song(String filePATH){ //constructor for "Song"
        this.filePATH = filePATH;
        try{
            //use jaudiotagger(lib) to creat an audiofile obj to read mp3 file's information
            AudioFile audioFile = AudioFileIO.read(new File(filePATH));

            //read throught the meta data of the audiofile
            Tag tag = audioFile.getTag();
            if(tag != null){
                songTitle = tag.getFirst(FieldKey.TITLE);
                songArtist = tag.getFirst(FieldKey.ARTIST);
            }
            else{ //could not read meta data of song 
                songTitle = "N/A";
                songArtist = "N/A";
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //getters
    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSpngLenght() {
        return spngLenght;
    }

    public String getFilePATH() {
        return filePATH;
    }
    
}
