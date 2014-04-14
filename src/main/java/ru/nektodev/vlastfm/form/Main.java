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
 * Created by nektodev on 04.04.14.
 */
public class Main extends JFrame{
    private JTextPane textPane1;
    private JPanel panel1;
    private JTextField lastFmUsername;
    private JButton closeBtn;
    private JButton searchBtn;
    private JLabel lastFmUsernameLabel;

    public static void main() {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Main() throws HeadlessException {

        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LastFmWorker worker = new LastFmWorker("nektodev");
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
        });
    }
}
