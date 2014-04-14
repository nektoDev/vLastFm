package ru.nektodev.vlastfm.model;

import de.umass.lastfm.Track;

import java.net.URL;

/**
 * Model track class. Consists lastFM track model and download URL
 *
 * @author nektodev
 * @see java.net.URL
 * @see de.umass.lastfm.Track
 */
public class TrackModel {

    private Track lastfmTrack;
    private URL url;

    public TrackModel(Track lastfmTrack, URL url) {
        this.lastfmTrack = lastfmTrack;
        this.url = url;
    }

    public Track getLastfmTrack() {
        return lastfmTrack;
    }

    public void setLastfmTrack(Track lastfmTrack) {
        this.lastfmTrack = lastfmTrack;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getName() {
        return this.lastfmTrack.getArtist() + " - " + this.lastfmTrack.getName();
    }

    public String getArtist() {
        return lastfmTrack == null ? null : lastfmTrack.getArtist();
    }

    public String getTrackName() {
        return lastfmTrack == null ? null : lastfmTrack.getName();
    }
}
