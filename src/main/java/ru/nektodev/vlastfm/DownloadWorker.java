package ru.nektodev.vlastfm;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import de.umass.lastfm.Track;
import ru.nektodev.vlastfm.model.Settings;
import ru.nektodev.vlastfm.service.TagService;
import ru.nektodev.vlastfm.service.VkService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * File downloader.
 *
 * @author nektodev
 */
public class DownloadWorker implements Runnable {

    private Track track;
    public DownloadWorker(Track track) {
        this.track = track;
    }

    @Override
    public void run() {
        try {
            //Find download URL
            String url = VkService.getURL(track);

            if (url == null || url.isEmpty()) {
                return;
            }

            URL trackUrl = new URL(url);

            ReadableByteChannel rbc = null;
            FileOutputStream fos = null;

            File file = new File(Settings.INSTANCE.getDestDir().getAbsolutePath() + "/" + track.getArtist() + " - " + track.getName() + ".mp3");

            try {
                rbc = Channels.newChannel(trackUrl.openStream());
                fos = new FileOutputStream(file);

                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            } catch (IOException e) {
                e.printStackTrace(); //TODO
            } finally {
                System.out.printf("DONE %s - %s %n", track.getArtist(), track.getName());
                if (fos != null) {
                    try {
                        fos.close();

                        TagService.writeTags(file, track);
                    } catch (IOException e) {
                        //TODO
                        e.printStackTrace();
                    } catch (UnsupportedTagException e) {
                        e.printStackTrace();
                    } catch (InvalidDataException e) {
                        e.printStackTrace();
                    } catch (NotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
