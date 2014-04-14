package ru.nektodev.vlastfm;

import ru.nektodev.vlastfm.form.Main;

/**
 * Created by nektodev on 03.04.14.
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
