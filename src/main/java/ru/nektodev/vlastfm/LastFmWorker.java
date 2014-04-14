package ru.nektodev.vlastfm;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Track;
import ru.nektodev.vlastfm.service.LastFmService;

import java.util.ArrayList;
import java.util.List;

/**
 * Worker to find Tracks on lastFM
 *
 * @author nektodev
 */
public class LastFmWorker implements Runnable {

    private String username;
    private ArrayList<Track> tracks;

    public LastFmWorker(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        ArrayList<Artist> topArtistByUser = (ArrayList<Artist>) LastFmService.getTopArtistByUser(username, 1);
        tracks = new ArrayList<>();
        for (Artist artist : topArtistByUser) {
            tracks.addAll(LastFmService.getTopTrackByArtist(artist, 10));
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Track> getTracks() {
        return tracks;
    }
}
