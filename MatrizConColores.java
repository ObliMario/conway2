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
import java.awt.Color;
import java.util.Random;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;

public class MatrizConColores {
    public Individuo[][] matriz;
    public Color[][] coloresClase;
    public Color[][] coloresTeam;
    private PanelMatriz panelMatriz;
    private PanelButton panelButton;
    final int BLANCO = 0;
    final int MUERTO = 0;
    final int INVALIDO = 2;
    final int ROJO = 1;
    final int AZUL = 2;
    final int nTeams = 3;
    final int SHEEP = 1;
    final int WOLF = 2;
    public int selectedTeam = 1;
    public int selectedClass = 1;
    public boolean play = false;
    public boolean next = false;
    public int totalTeams = 2;
    public int totalClasses = 2;

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
        JFrame ventana = new JFrame("Conway's Game of Life");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el panel de la matriz
        panelMatriz = new PanelMatriz(this);

        // Crear el panel de botones
        panelButton = new PanelButton(this);

        // Crear el split pane y agregar los paneles a cada lado
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelMatriz, panelButton);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(0.7); // El 70% se asigna a la matriz

        ventana.getContentPane().add(splitPane, BorderLayout.CENTER);

        ventana.pack();
        ventana.setVisible(true);

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
        if (i.estado == MUERTO) {
            color = Color.WHITE;
        } else if (i.estado == INVALIDO) {
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
                    System.out.println("Error: Yellow, clase: " + i.clase);
                    color = Color.YELLOW;
                    break;
            }
        }
        return color;
    }

    private Color getColorTeam(Individuo i) {
        Color color;
        if (i.estado == MUERTO) {
            color = Color.GRAY;
        } else if (i.estado == INVALIDO) {
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
                    System.out.println("Error: Yellow, team: " + i.team);

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
        panelMatriz.repaint();
        matriz = newMatriz;
    }

    private Individuo newIndividual(Individuo[][] matriz, int i, int j) {
        Individuo newIndividual;
        Individuo thisIndividuo = matriz[i][j];
        Individuo[] vecinos = getNeighbors(matriz, i, j);
        int[] orderedNeighbors = getOrderedNeighbors(thisIndividuo, vecinos);

        if (thisIndividuo.estado == MUERTO) {
            // reglas para decidir si y quien nace
            newIndividual = thisIndividuo.Nacimiento(vecinos);
        } else {
            if (thisIndividuo.survivePoints(orderedNeighbors, vecinos) == 0) {
                newIndividual = new IndividuoMuerto();
            } else {
                Random rand = new Random();
                int randomNum = rand.nextInt(100);
                if (randomNum < thisIndividuo.survivePoints(orderedNeighbors, vecinos)) {
                    newIndividual = thisIndividuo;
                } else {
                    newIndividual = new IndividuoMuerto();
                }
            }
        }
        return newIndividual;
    }

    /**
     * Devuelve un array de Individuos que indica si los vecinos son del equipo
     * [0,1,2,..]
     * 
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

    public void selectCell(int fila, int columna) {

        matriz[fila][columna] = new Individuo(1, selectedTeam, selectedClass);
        matriz[fila][columna] = matriz[fila][columna].createIndividuo(selectedClass, selectedTeam);

        coloresClase = getColoresClase(matriz);
        coloresTeam = getColoresTeam(matriz);
        panelMatriz.repaint();
    }

    public int[] getPuntaje() {
        int[] puntaje = new int[nTeams+1];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (matriz[i][j].estado != MUERTO) {
                    puntaje[matriz[i][j].team]++;
                }
            }
        }
        return puntaje;
    }

    public float[] getPuntajePorcentaje(){
        int[] puntaje = getPuntaje();
        float[] puntajePorcentaje = new float[nTeams+1];
        for (int i = 0; i < puntaje.length; i++) {
            puntajePorcentaje[i] = (float) puntaje[i] / (matriz.length * matriz[0].length);
            // Redondear a 2 decimales sobre el 100 %
            puntajePorcentaje[i] = Math.round(puntajePorcentaje[i] * 10000) / 100f;    
        }
        return puntajePorcentaje;
    }

    public void emptyMatriz() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j] = new IndividuoMuerto();
            }
        }
        coloresClase = getColoresClase(matriz);
        coloresTeam = getColoresTeam(matriz);
        panelMatriz.repaint();
    }

}
