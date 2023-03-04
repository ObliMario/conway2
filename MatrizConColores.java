/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conway2;

/**
 *
 * @author mario
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

public class MatrizConColores {
    private int[][] matriz;
    private Color[][] colores;
    private MatrizPanel panel;
    final int ROJO = 1;
    final int AZUL = 2;
    final int BLANCO = 0;

    public MatrizConColores(int[][] matriz) {
        this.matriz = matriz;
        this.colores = new Color[matriz.length][matriz[0].length];
        // Inicializar la matriz de colores con los valores de la matriz, si el valor es
        // 0, mostrar blanco, si es 1, mostrar rojo, si es 2, mostrar azul, en otro
        // caso, mostrar negro

        colores = getColores(matriz);

        // Crear una ventana gráfica para mostrar la matriz
        JFrame frame = new JFrame("Conway's Game of Life");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new MatrizPanel();
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);

    }

    private Color[][] getColores(int[][] matriz) {
        colores = new Color[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                colores[i][j] = getColor(matriz[i][j]);
            }
        }
        return colores;
    }

    private Color getColor(int i) {
        Color color;
        switch (i) {
            case BLANCO:
                color = Color.WHITE;
                break;
            case ROJO:
                color = Color.RED;
                break;
            case AZUL:
                color = Color.BLUE;
                break;
            default:
                color = Color.BLACK;
                break;
        }
        return color;
    }

    public void nextGeneration() {
        int[][] newMatriz = new int[matriz.length][matriz[0].length];

        // Implementar el algoritmo del juego de la vida aquí
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                // newMatriz[i][j] = random.nextInt(3); //random
                newMatriz[i][j] = newIndividual(matriz, i, j);
            }
        }
        // Actualizar la matriz de colores con los valores de la nueva matriz
        colores = getColores(newMatriz);
        // Repintar la matriz
        panel.repaint();
        matriz = newMatriz;
    }

    private int newIndividual(int[][] matriz, int i, int j) {
        int newIndividual = BLANCO;
        int reds = 0;
        int blues = 0;
        int dead = 0;
        int individual = matriz[i][j];
        Random random = new Random();
        int[] neighbors = getNeighbors(matriz, i, j);
        for (int k = 0; k < neighbors.length; k++) {
            if (neighbors[k] == BLANCO) {
                dead++;
            } else {
                if (neighbors[k] == ROJO) {
                    reds++;
                } else {
                    blues++;
                }
            }
        }
        if (individual == BLANCO) {
            if (reds == 3 || blues == 3) {
                if (reds == blues) {
                    newIndividual = random.nextInt(2) + 1;
                } else {
                    if (reds == 3) {
                        newIndividual = ROJO;
                    } else {
                        newIndividual = AZUL;
                    }
                }
            } else {
                newIndividual = BLANCO;
            }

        } else {
            if (individual == ROJO) {
                if (reds + blues > 3 || reds + blues < 2) {
                    newIndividual = 0;
                } else {
                    newIndividual = ROJO;
                }
            } else {
                if (reds + blues > 3 || reds + blues < 2) {
                    newIndividual = 0;
                } else {
                    newIndividual = AZUL;
                }
            }
        }
        return newIndividual;
    }

    private int[] getNeighbors(int[][] matriz, int i, int j) {
        int[] neighbors = new int[8];
        int k = 0;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if (x >= 0 && x < matriz.length && y >= 0 && y < matriz[0].length) {
                    if (x != i || y != j) {
                        neighbors[k] = matriz[x][y];
                        k++;
                    }
                }
            }
        }
        return neighbors;
    }

    private class MatrizPanel extends JPanel {
        private static final int CELDA_SIZE = 20;

        public MatrizPanel() {
            setPreferredSize(new Dimension(matriz[0].length * CELDA_SIZE, matriz.length * CELDA_SIZE));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    g.setColor(colores[i][j]);
                    g.fillRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);
                }
            }
        }
    }
}
