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
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
