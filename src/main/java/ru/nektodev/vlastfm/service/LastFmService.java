package ru.nektodev.vlastfm.service;

import de.umass.lastfm.*;

import javax.sound.midi.Track;
import java.util.Collection;
import java.util.List;

/**
 * Created by nektodev on 04.04.14.
 */
public class LastFmService {

    public static final String API_KEY = "1bbf154a2157a4ef504ea67909b059bc";

    public static Collection<Artist> getTopArtistByUser(String username, Integer limit) {
        Result result = Caller.getInstance().call("user.getTopArtists", API_KEY, "user", username, "limit", limit.toString());
        return ResponseBuilder.buildCollection(result, Artist.class);
    }

    public static Collection<de.umass.lastfm.Track> getTopTrackByArtist(Artist artist, Integer limit) {
        Result result = Caller.getInstance().call("artist.getTopTracks", API_KEY, "artist", artist.getName(), "limit", limit.toString());
        return ResponseBuilder.buildCollection(result, de.umass.lastfm.Track.class);
    }
}
