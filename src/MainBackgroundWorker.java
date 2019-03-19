/*
 * PadlockJ - a text encryption application written in Java that uses the AES-128 algorithm.
 * Copyright (C) 2017 UnexomWid

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.unexomwid.padlockj;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SuppressWarnings("all")
public class MainBackgroundWorker implements Runnable {

    private Thread mainThread;

    public void run() {
        try {
            this.disableWindow();

            String key = new String(MainForm.keyBox.getPassword());

            MainForm.progressBar.setValue(33);

            String data = (MainForm.decryptRadio.isSelected() ? new String(Helper.RunAES(Helper.CryptoAction.Decrypt, Base64.getDecoder().decode(MainForm.inputBox.getText()), key), StandardCharsets.UTF_8) : Base64.getEncoder().encodeToString(Helper.RunAES(Helper.CryptoAction.Encrypt, MainForm.inputBox.getText().getBytes("UTF-8"), key)));
            MainForm.progressBar.setValue(66);

            MainForm.outputBox.setText(data);
            MainForm.progressBar.setValue(100);

            this.enableWindow();
        } catch (Exception ex) {
            this.enableWindow();

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exc = sw.toString();

            MainForm.progressBar.setValue(0);
            javax.swing.JOptionPane.showMessageDialog(null, "An error has occurred.Make sure that the input is valid and the key is correct.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public void start() {
        if (mainThread == null) {
            mainThread = new Thread(this);
            mainThread.start();
        }
    }

    private void disableWindow() {
        MainForm.inputBox.setEnabled(false);

        MainForm.keyBox.setEnabled(false);

        MainForm.encryptRadio.setEnabled(false);

        MainForm.decryptRadio.setEnabled(false);

        MainForm.inputBox.setEnabled(false);

        MainForm.startButton.setEnabled(false);

        MainForm.progressBar.setValue(0);
    }

    private void enableWindow() {
        MainForm.inputBox.setEnabled(true);

        MainForm.keyBox.setEnabled(true);

        MainForm.encryptRadio.setEnabled(true);

        MainForm.decryptRadio.setEnabled(true);

        MainForm.inputBox.setEnabled(true);

        MainForm.startButton.setEnabled(true);
    }
}
