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
    private Individuo[][] matriz;
    private Color[][] colores;
    private MatrizPanel panel;
    final int BLANCO = 0;
    final int MUERTO = 0;
    final int INVALIDO = 2;
    final int ROJO = 1;
    final int AZUL = 2;
    final int nTeams = 3;

    public MatrizConColores(Individuo[][] matriz) {
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

    private Color[][] getColores(Individuo[][] matriz) {
        colores = new Color[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                colores[i][j] = getColor(matriz[i][j]);
            }
        }
        return colores;
    }

    private Color getColor(Individuo i) {
        Color color;
        if(i.estado == MUERTO){
            color = Color.WHITE;
        } else if(i.estado == INVALIDO){
            color = Color.BLACK;
        } else {
            switch (i.team) {
                case ROJO:
                    color = Color.RED;
                    break;
                case AZUL:
                    color = Color.BLUE;
                    break;
                default:
                    System.out.println("Yellow, team: " + i.team);

                    color = Color.YELLOW;
                    break;
            }
        }
        return color;
    }

    public void nextGeneration() {

        Individuo[][] newMatriz = new Individuo[matriz.length][matriz[0].length];
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

    private Individuo newIndividual(Individuo[][] matriz, int i, int j) {
        Individuo newIndividual;
        Individuo thisIndividuo = matriz[i][j];
        Individuo[] neighbors = getNeighbors(matriz, i, j);
        int[] orderedNeighbors = getOrderedNeighbors(thisIndividuo, neighbors);
        
        if (thisIndividuo.estado == BLANCO) {
            //reglas para decidir si nace alguno alrededor
            newIndividual = thisIndividuo.NacimientoPoints(orderedNeighbors);
        } else {
            if(thisIndividuo.survivePoints(orderedNeighbors) == 0){
                newIndividual = new IndividuoMuerto();
            } else {
                newIndividual = new Individuo(1, thisIndividuo.team, thisIndividuo.clase);
            }
        }
        return newIndividual;
    }

    
    /* Devuelve un array de Individuos que indica si los vecinos son del equipo [0,1,2,..] */
    private int[] getOrderedNeighbors(Individuo thisIndividuo, Individuo[] neighbors) {
        int[] orderedNeighbors = new int[nTeams];
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] == null || neighbors[i].estado == MUERTO) {
                orderedNeighbors[0]++;
            } else {
                orderedNeighbors[neighbors[i].team]++;
            }
        }
        return orderedNeighbors;
    }

    private Individuo[] getNeighbors(Individuo[][] matriz, int i, int j) {
        Individuo[] neighbors = new Individuo[8];
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
                    //las celdas deben tener el color correspondiente
                    g.setColor(colores[i][j]);
                    g.fillRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);
                    //las celdas deben tener bordes blancos
                    g.setColor(Color.WHITE);
                    g.drawRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);
                }
            }
        }
    }
}
