/**
 * PadlockJ (https://github.com/UnexomWid/PadlockJ)
 *
 * This project is licensed under the MIT license.
 * Copyright (c) 2017-2019 UnexomWid (https://uw.exom.dev)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
