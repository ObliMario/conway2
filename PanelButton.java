/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conway2;

/**
 *
 * @author mario
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelButton extends JPanel {

    private JPanel buttonPanel;
    private JButton btnPlay;
    private JButton btnPause;
    private JButton btnTeam;
    private JButton btnClass;
    private JButton btnNext;
    private JLabel labelInfo;

    private MatrizConColores matrizConColores;

    public PanelButton(MatrizConColores matrizConColores) {
        this.matrizConColores = matrizConColores;
        // Crear botones y agregarlos al panell de botones
        btnPlay = new JButton("Play");
        btnPause = new JButton("Pause");
        btnTeam = new JButton("Team: 1");
        btnClass = new JButton("Class: 1");
        btnNext = new JButton("Next");

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));

        buttonPanel.add(btnPlay);
        buttonPanel.add(btnPause);
        buttonPanel.add(btnTeam);
        buttonPanel.add(btnClass);
        buttonPanel.add(btnNext);

        labelInfo = new JLabel("Info: ");
        buttonPanel.add(labelInfo);

        // Agregar el panell de botones y el contenido principal al panell
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);

        btnTeam.addActionListener((ActionEvent e) -> {
            // Modificar la variable selectedTeam para que tome valores de 1 en adelante con
            // módulo 2

            matrizConColores.selectedTeam++;
            if (matrizConColores.selectedTeam > matrizConColores.totalTeams) {
                matrizConColores.selectedTeam = 0;
            }
            btnTeam.setText("Team: " + matrizConColores.selectedTeam);
        });
        btnClass.addActionListener((ActionEvent e) -> {
            // Modificar la variable selectedTeam aquí

            matrizConColores.selectedClass++;
            if (matrizConColores.selectedClass > matrizConColores.totalClasses) {
                matrizConColores.selectedClass = 1;
            }
            btnClass.setText("Class: " + matrizConColores.selectedClass);
        });
        btnNext.addActionListener((ActionEvent e) -> {
            // Modificar la variable selectedTeam aquí
            matrizConColores.nextGeneration();
        });
        btnPlay.addActionListener((ActionEvent e) -> {
            // Modificar la variable selectedTeam aquí
            matrizConColores.play = true;
        });
        btnPause.addActionListener((ActionEvent e) -> {
            // Modificar la variable selectedTeam aquí
            matrizConColores.play = false;
        });

    }
}
