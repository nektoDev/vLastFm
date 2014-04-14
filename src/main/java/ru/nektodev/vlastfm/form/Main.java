package ru.nektodev.vlastfm.form;

import de.umass.lastfm.Track;
import ru.nektodev.vlastfm.DownloadWorker;
import ru.nektodev.vlastfm.LastFmWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main form
 *
 * @author nektodev
 */
public class Main extends JFrame{
    private JTextPane textPane1;
    private JPanel panel1;
    private JTextField lastFmUsername;
    private JButton closeBtn;
    private JButton searchBtn;
    private JLabel lastFmUsernameLabel;
    private JSpinner artistCount;
    private JSpinner trackCount;

    public static void main() {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Main() throws HeadlessException {

        this.artistCount.setValue(10);
        this.trackCount.setValue(50);

        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBtnAction();
            }
        });
    }


    /**
     * Search button action listener. Search user's top artists,
     * get theirs top tracks and download them from VK
     */
    private void searchBtnAction() {
        LastFmWorker worker = new LastFmWorker(this.lastFmUsername.getText(), (Integer) artistCount.getValue(), (Integer) trackCount.getValue());
        worker.run();

        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (Track track : worker.getTracks()) {
            pool.submit(new DownloadWorker(track));
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
