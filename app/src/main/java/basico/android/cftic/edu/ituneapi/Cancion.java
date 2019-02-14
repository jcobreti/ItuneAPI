package basico.android.cftic.edu.ituneapi;
import java.io.Serializable;

public class Cancion implements Serializable {
    //Los nombre tienen que coincidir con el JSON

    private String artistName;
    private String trackName;
    private String trackId;
    private int artistId;
    private String artworkUrl100;
    private String previewUrl;
    private String collectionName;


//Constructores (2) vacio y con parametros
    public Cancion() {}

    public Cancion(String artistName, String trackName, int artistId, String artworkUrl100, String previewUrl, String tracKId, String collectionName) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.artistId = artistId;
        this.trackId=tracKId;
        this.artworkUrl100 = artworkUrl100;
        this.previewUrl=previewUrl;
        this.collectionName=collectionName;
    }

    //Como tiene los GETTER y SETTER es un BEAN
    public String getArtistName() { return artistName;}

    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getTrackId() { return trackId; }

    public void setTrackId(String trackId) { this.trackId = trackId; }

    public String getPreviewUrl() { return previewUrl; }

    public void setPreviewUrl(String previewUrl) { this.previewUrl = previewUrl; }

    public String getTrackName() { return trackName; }

    public void setTrackName(String trackName) { this.trackName = trackName; }

    public int getArtistId() { return artistId; }

    public void setArtistId(int artistID) { this.artistId = artistID; }

    public String getArtworkUrl100() { return artworkUrl100; }

    public void setArtworkUrl100(String artworkUrl100) { this.artworkUrl100 = artworkUrl100; }

    public String getCollectionName() {return collectionName;}

    public void setCollectionName(String collectionName) {this.collectionName = collectionName;}
}
