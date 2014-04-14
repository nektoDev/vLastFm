package ru.nektodev.vlastfm.service;

import de.umass.lastfm.*;

import javax.sound.midi.Track;
import java.util.Collection;
import java.util.List;

/**
 * Service to work with LastFM API
 *
 * @author nektodev
 */
public class LastFmService {

    //TODO remove from code
    public static final String API_KEY = "1bbf154a2157a4ef504ea67909b059bc";

    /**
     * Return top artists by LastFM user
     * @param username lastFm user name
     * @param limit max size of returning coollection
     * @return collection of Artist
     *
     * @see de.umass.lastfm.Artist
     */
    public static Collection<Artist> getTopArtistByUser(String username, Integer limit) {
        Result result = Caller.getInstance().call("user.getTopArtists", API_KEY, "user", username, "limit", limit.toString());
        return ResponseBuilder.buildCollection(result, Artist.class);
    }

    /**
     * Return top artists tracks
     *
     * @param artist
     * @param limit
     * @return collection of Track
     *
     * @see de.umass.lastfm.Track
     * @see de.umass.lastfm.Artist
     */

    public static Collection<de.umass.lastfm.Track> getTopTrackByArtist(Artist artist, Integer limit) {
        Result result = Caller.getInstance().call("artist.getTopTracks", API_KEY, "artist", artist.getName(), "limit", limit.toString());
        return ResponseBuilder.buildCollection(result, de.umass.lastfm.Track.class);
    }
}
