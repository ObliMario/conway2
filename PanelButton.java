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
    private JButton btnScore;
    private JButton btnEmpty;
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
        btnScore = new JButton("Score");
        btnEmpty = new JButton("Empty");

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 6));

        buttonPanel.add(btnPlay);
        buttonPanel.add(btnPause);
        buttonPanel.add(btnTeam);
        buttonPanel.add(btnClass);
        buttonPanel.add(btnNext);
        buttonPanel.add(btnScore);
        buttonPanel.add(btnEmpty);


        labelInfo = new JLabel("Info: ");
/* 
        labelInfo.setPreferredSize(new Dimension(Short.MAX_VALUE, labelInfo.getPreferredSize().height));
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
         */
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
        btnScore.addActionListener((ActionEvent e) -> {
            // Modificar la variable selectedTeam aquí
            float[] score = matrizConColores.getPuntajePorcentaje();
            labelInfo.setText("Info: " + "Team ROJO: " + score[1] + "% Team AZUL: " + score[2] + "%");
        });
        btnEmpty.addActionListener((ActionEvent e) -> {
            // Modificar la variable selectedTeam aquí
            matrizConColores.emptyMatriz();
        });
    }
}
