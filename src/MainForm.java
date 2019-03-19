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

import java.awt.EventQueue;
import javax.swing.JFrame;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("all")
public class MainForm {

    public static JFrame frmPadlockj;
    public static JTextField inputBox;
    public static JPanel modePanel;
    public static JPasswordField keyBox;
    public static JTextField outputBox;
    public static JRadioButton encryptRadio;
    public static JRadioButton decryptRadio;
    public static JProgressBar progressBar;
    public static JButton startButton;

    public MainForm() {
        initForm();
        Helper.initCiphers();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainForm window = new MainForm();
                    window.frmPadlockj.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initForm() {
        class InvalidInputException extends Exception {
            public InvalidInputException() {
            }

            public InvalidInputException(String message) {
                super(message);
            }
        }
        class InvalidKeyException extends Exception {
            public InvalidKeyException() {
            }

            public InvalidKeyException(String message) {
                super(message);
            }
        }
        frmPadlockj = new JFrame();
        frmPadlockj.setTitle("PadlockJ");
        frmPadlockj.setResizable(false);
        frmPadlockj.setBounds(100, 100, 289, 227);
        frmPadlockj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(10, 11, 161, 40);
        inputPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Input", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));

        inputBox = new JTextField();
        inputBox.setColumns(10);
        GroupLayout gl_inputPanel = new GroupLayout(inputPanel);
        gl_inputPanel.setHorizontalGroup(
                gl_inputPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_inputPanel.createSequentialGroup()
                                .addComponent(inputBox, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_inputPanel.setVerticalGroup(
                gl_inputPanel.createParallelGroup(Alignment.LEADING)
                        .addComponent(inputBox, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
        );
        inputPanel.setLayout(gl_inputPanel);

        modePanel = new JPanel();
        modePanel.setBounds(180, 11, 93, 66);
        modePanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Mode", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel keyPanel = new JPanel();
        keyPanel.setBounds(10, 52, 161, 40);
        keyPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Key", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        encryptRadio = new JRadioButton("Encrypt");
        encryptRadio.setSelected(true);

        decryptRadio = new JRadioButton("Decrypt");
        frmPadlockj.getContentPane().setLayout(null);
        frmPadlockj.getContentPane().add(keyPanel);

        ButtonGroup bg1 = new ButtonGroup();

        bg1.add(encryptRadio);
        bg1.add(decryptRadio);

        keyBox = new JPasswordField();
        GroupLayout gl_keyPanel = new GroupLayout(keyPanel);
        gl_keyPanel.setHorizontalGroup(
                gl_keyPanel.createParallelGroup(Alignment.LEADING)
                        .addComponent(keyBox, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
        );
        gl_keyPanel.setVerticalGroup(
                gl_keyPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_keyPanel.createSequentialGroup()
                                .addComponent(keyBox, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        keyPanel.setLayout(gl_keyPanel);
        frmPadlockj.getContentPane().add(inputPanel);
        frmPadlockj.getContentPane().add(modePanel);
        GroupLayout gl_modePanel = new GroupLayout(modePanel);
        gl_modePanel.setHorizontalGroup(
                gl_modePanel.createParallelGroup(Alignment.LEADING)
                        .addComponent(encryptRadio)
                        .addComponent(decryptRadio)
        );
        gl_modePanel.setVerticalGroup(
                gl_modePanel.createParallelGroup(Alignment.LEADING)
                        .addComponent(encryptRadio, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_modePanel.createSequentialGroup()
                                .addGap(19)
                                .addComponent(decryptRadio))
        );
        modePanel.setLayout(gl_modePanel);

        startButton = new JButton("Start");
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                try {
                    if (inputBox.getText() == null || inputBox.getText().isEmpty())
                        throw new InvalidInputException();
                    if (keyBox.getPassword() == null || String.valueOf(keyBox.getPassword()).isEmpty())
                        throw new InvalidKeyException();
                    MainBackgroundWorker mainThread = new MainBackgroundWorker();
                    mainThread.start();
                } catch (InvalidInputException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidKeyException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid key!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        startButton.setBounds(182, 80, 89, 18);
        frmPadlockj.getContentPane().add(startButton);

        JPanel progressPanel = new JPanel();
        progressPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Progress", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        progressPanel.setBounds(10, 98, 263, 40);
        frmPadlockj.getContentPane().add(progressPanel);

        progressBar = new JProgressBar();
        progressBar.setForeground(new Color(255, 0, 0));
        GroupLayout gl_progressPanel = new GroupLayout(progressPanel);
        gl_progressPanel.setHorizontalGroup(
                gl_progressPanel.createParallelGroup(Alignment.LEADING)
                        .addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );
        gl_progressPanel.setVerticalGroup(
                gl_progressPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_progressPanel.createSequentialGroup()
                                .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        progressPanel.setLayout(gl_progressPanel);

        JPanel outputPanel = new JPanel();
        outputPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        outputPanel.setBounds(10, 136, 263, 42);
        frmPadlockj.getContentPane().add(outputPanel);

        outputBox = new JTextField();
        outputBox.setBackground(Color.WHITE);
        outputBox.setColumns(10);
        GroupLayout gl_outputPanel = new GroupLayout(outputPanel);
        gl_outputPanel.setHorizontalGroup(
                gl_outputPanel.createParallelGroup(Alignment.LEADING)
                        .addComponent(outputBox, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );
        gl_outputPanel.setVerticalGroup(
                gl_outputPanel.createParallelGroup(Alignment.LEADING)
                        .addComponent(outputBox, GroupLayout.PREFERRED_SIZE, 16, Short.MAX_VALUE)
        );
        outputPanel.setLayout(gl_outputPanel);

        JLabel cLabel = new JLabel("©UnexomWid 2019");
        cLabel.setForeground(Color.GRAY);
        cLabel.setBounds(162, 182, 131, 14);
        frmPadlockj.getContentPane().add(cLabel);
    }
}
