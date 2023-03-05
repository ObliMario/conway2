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
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class MatrizConColores {
    private Individuo[][] matriz;
    private Color[][] coloresClase;
    private Color[][] coloresTeam;
    private MatrizPanel panel;
    final int BLANCO = 0;
    final int MUERTO = 0;
    final int INVALIDO = 2;
    final int ROJO = 1;
    final int AZUL = 2;
    final int nTeams = 3;
    final int SHEEP = 1;
    final int WOLF = 2;

    public MatrizConColores(Individuo[][] matriz) {
        this.matriz = matriz;
        this.coloresClase = new Color[matriz.length][matriz[0].length];
        this.coloresTeam = new Color[matriz.length][matriz[0].length];
        // Inicializar la matriz de colores con los valores de la matriz, si el valor es
        // 0, mostrar blanco, si es 1, mostrar rojo, si es 2, mostrar azul, en otro
        // caso, mostrar negro

        coloresClase = getColoresClase(matriz);
        coloresTeam = getColoresTeam(matriz);

        // Crear una ventana gráfica para mostrar la matriz
        JFrame frame = new JFrame("Conway's Game of Life");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new MatrizPanel();
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);

    }

    private Color[][] getColoresClase(Individuo[][] matriz) {
        coloresClase = new Color[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                coloresClase[i][j] = getColorClase(matriz[i][j]);
            }
        }
        return coloresClase;
    }
    private Color[][] getColoresTeam(Individuo[][] matriz) {
        coloresTeam = new Color[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                coloresTeam[i][j] = getColorTeam(matriz[i][j]);
            }
        }
        return coloresTeam;
    }

    private Color getColorClase(Individuo i) {
        Color color;
        if(i.estado == MUERTO){
            color = Color.WHITE;
        } else if(i.estado == INVALIDO){
            color = Color.YELLOW;
        } else {
            switch (i.clase) {
                case SHEEP:
                    color = Color.GREEN;
                    break;
                case WOLF:
                    color = Color.BLACK;
                    break;
                default:
                    System.out.println("Yellow, team: " + i.team);

                    color = Color.YELLOW;
                    break;
            }
        }
        return color;
    }
    private Color getColorTeam(Individuo i) {
        Color color;
        if(i.estado == MUERTO){
            color = Color.GRAY;
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
                newMatriz[i][j] = newIndividual(matriz, i, j);
            }
        }
        // Actualizar la matriz de coloresClase con los valores de la nueva matriz
        coloresClase = getColoresClase(newMatriz);
        coloresTeam = getColoresTeam(newMatriz);
        // Repintar la matriz
        panel.repaint();
        matriz = newMatriz;
    }

    private Individuo newIndividual(Individuo[][] matriz, int i, int j) {
        Individuo newIndividual;
        Individuo thisIndividuo = matriz[i][j];
        Individuo[] vecinos = getNeighbors(matriz, i, j);
        int[] orderedNeighbors = getOrderedNeighbors(thisIndividuo, vecinos);
        
        if (thisIndividuo.estado == MUERTO) {
            //reglas para decidir si y quien nace 
            newIndividual = thisIndividuo.Nacimiento(vecinos);
        } else {
            if(thisIndividuo.survivePoints(orderedNeighbors) == 0){
                newIndividual = new IndividuoMuerto();
            } else {
                newIndividual = thisIndividuo;
            }
        }
        return newIndividual;
    }

    
    /**
     * Devuelve un array de Individuos que indica si los vecinos son del equipo [0,1,2,..]
     * @param thisIndividuo
     * @param neighbors
     * @return Integer array with the number of neighbors of each team
     */
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
            Graphics2D g2 = (Graphics2D) g;


            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    //las celdas deben tener el color de la clase
                    g.setColor(coloresClase[i][j]);
                    g.fillRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);
                    //las celdas deben tener bordes correspondientes al equipo con un ancho de 2px
                    g.setColor(coloresTeam[i][j]);
                    if(coloresTeam[i][j] != Color.GRAY){
                        g2.setStroke(new BasicStroke(3)); //grosor de 2 píxel
                    } else {
                        g2.setStroke(new BasicStroke(1)); //grosor de 1 píxel
                    }
                    g.drawRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);
                    
                    g2.setStroke(new BasicStroke(1)); //grosor de 1 píxel
                    if(coloresTeam[i][j] != Color.GRAY){
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.GRAY);
                    }

                    g.drawRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);

    
                }
            }
        }
    }
}
