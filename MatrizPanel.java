/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conway2;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author mario
 */
public class MatrizPanel extends JPanel {

    private static final int CELDA_SIZE = 20;
    private MatrizConColores matrizConColores;

    public MatrizPanel(MatrizConColores matrizConColores) {
        this.matrizConColores = matrizConColores;
        setPreferredSize(new Dimension(matrizConColores.matriz[0].length * CELDA_SIZE,
                matrizConColores.matriz.length * CELDA_SIZE));
        MatrizMouseListener mouseListener = new MatrizMouseListener(matrizConColores);
        addMouseListener(mouseListener);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < matrizConColores.matriz.length; i++) {
            for (int j = 0; j < matrizConColores.matriz[0].length; j++) {
                // las celdas deben tener el color de la clase
                g.setColor(matrizConColores.coloresClase[i][j]);
                g.fillRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);
                // las celdas deben tener bordes correspondientes al equipo con un ancho de 2px
                g.setColor(matrizConColores.coloresTeam[i][j]);
                if (matrizConColores.coloresTeam[i][j] != Color.GRAY) {
                    g2.setStroke(new BasicStroke(3)); // grosor de 2 píxel
                } else {
                    g2.setStroke(new BasicStroke(1)); // grosor de 1 píxel
                }
                g.drawRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);

                g2.setStroke(new BasicStroke(1)); // grosor de 1 píxel
                if (matrizConColores.coloresTeam[i][j] != Color.GRAY) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.GRAY);
                }

                g.drawRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);

            }
        }
    }

}