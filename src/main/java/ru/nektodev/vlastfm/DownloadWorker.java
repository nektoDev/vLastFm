package ru.nektodev.vlastfm;

import de.umass.lastfm.Track;
import ru.nektodev.vlastfm.model.Settings;
import ru.nektodev.vlastfm.service.VkService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by nektodev on 09.04.14.
 */
public class DownloadWorker implements Runnable {

    private Track track;
    public DownloadWorker(Track track) {
        this.track = track;
    }

    @Override
    public void run() {
        try {
            String url = VkService.getURL(track.getArtist() + " - " + track.getName());

            if (url == null || url.isEmpty()) {
                return;
            }

            URL trackUrl = new URL(url);

            ReadableByteChannel rbc = null;
            FileOutputStream fos = null;

            File artistDir = new File(Settings.INSTANCE.getDestDir().getAbsolutePath() + "/" + track.getArtist());
            if (!artistDir.exists()) {
                boolean isOk = artistDir.mkdirs();
                if (!isOk) {
                    return; //TODO
                }
            }

            File file = new File(artistDir.getAbsolutePath() + "/" + track.getArtist() + " - " + track.getName() + ".mp3");

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
                    } catch (IOException e) {
                        //TODO

                    }
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
