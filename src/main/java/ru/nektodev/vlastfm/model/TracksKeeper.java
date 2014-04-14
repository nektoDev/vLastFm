package ru.nektodev.vlastfm.model;

import de.umass.lastfm.Track;

import java.util.List;

/**
 * Created by nektodev on 06.04.14.
 */
public class TracksKeeper {

    private List<Track> tracksFromLF;

    private List<TrackModel> tracksWithURL;


    public List<Track> getTracksFromLF() {
        return tracksFromLF;
    }

    public void setTracksFromLF(List<Track> tracksFromLF) {
        this.tracksFromLF = tracksFromLF;
    }

    public List<TrackModel> getTracksWithURL() {
        return tracksWithURL;
    }

    public void setTracksWithURL(List<TrackModel> tracksWithURL) {
        this.tracksWithURL = tracksWithURL;
    }
}
