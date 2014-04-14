package ru.nektodev.vlastfm;

import ru.nektodev.vlastfm.form.Main;

/**
 * Main class
 *
 * @author nektodev
 */
public class VLastFm {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main.main();
            }
        });
    }
}
