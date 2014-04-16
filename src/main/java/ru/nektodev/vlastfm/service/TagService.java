package ru.nektodev.vlastfm.service;

import com.mpatric.mp3agic.*;
import de.umass.lastfm.Track;
import ru.nektodev.vlastfm.model.Settings;


import java.io.File;
import java.io.IOException;

/**
 * Service to work with file ID3tags
 *
 * @author nektodev
 */
public class TagService {

    public static void writeTags(File file, Track track) throws IOException, InvalidDataException, UnsupportedTagException, NotSupportedException {

        File destinationDir = getDestinationDir(track);
        if (destinationDir == null) return;

        Mp3File song = new Mp3File(file.getAbsolutePath());

        ID3v2 id3v2Tag;
        if (song.hasId3v2Tag()) {
            id3v2Tag = song.getId3v2Tag();
        } else {
            id3v2Tag = new ID3v24Tag();
        }

        id3v2Tag.setAlbumArtist(track.getArtist());
        id3v2Tag.setOriginalArtist(track.getArtist());
        //Add setting to rename album
        id3v2Tag.setAlbum(track.getArtist() + " last.fm top");
        id3v2Tag.setTitle(track.getName());
        id3v2Tag.setArtist(track.getArtist());

        song.setId3v2Tag(id3v2Tag);

        song.save(destinationDir.getAbsolutePath() + "/" + track.getArtist() + " - " + track.getName() + ".mp3");

        file.delete();
    }

    private static File getDestinationDir(Track track) {
        File artistDir = new File(Settings.INSTANCE.getDestDir().getAbsolutePath() + "/" + track.getArtist());
        if (!artistDir.exists()) {
            boolean isOk = artistDir.mkdirs();
            if (!isOk) {
                return null;
            }
        }
        return artistDir;
    }

}
