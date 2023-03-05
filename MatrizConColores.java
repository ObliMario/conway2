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
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

public class MatrizConColores {
    private Individuo[][] matriz;
    private Color[][] coloresClase;
    private Color[][] coloresTeam;
    private MatrizPanel panelMatriz;
    private ButtonPanel panelButton;
    private static final int CELDA_SIZE = 20;
    final int BLANCO = 0;
    final int MUERTO = 0;
    final int INVALIDO = 2;
    final int ROJO = 1;
    final int AZUL = 2;
    final int nTeams = 3;
    final int SHEEP = 1;
    final int WOLF = 2;
    private int selectedTeam = 1;
    private int selectedClass = 1;
    public boolean play = false;
    public boolean next = false;
    private int totalTeams = 2;
    private int totalClasses = 2;

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
        panelMatriz = new MatrizPanel();

        // Crear el panel de botones
        panelButton = new ButtonPanel();

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
                    System.out.println("Yellow, team: " + i.team);

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

    private void selectCell(int fila, int columna, int selectedTeam, int selectedClass) {

        matriz[fila][columna] = new Individuo(1, selectedTeam, selectedClass);
        matriz[fila][columna] = matriz[fila][columna].createIndividuo(selectedClass, selectedTeam);

        coloresClase = getColoresClase(matriz);
        coloresTeam = getColoresTeam(matriz);
        panelMatriz.repaint();
    }

    private class MatrizPanel extends JPanel implements MouseListener {

        public MatrizPanel() {
            setPreferredSize(new Dimension(matriz[0].length * CELDA_SIZE, matriz.length * CELDA_SIZE));
            addMouseListener(this);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    // las celdas deben tener el color de la clase
                    g.setColor(coloresClase[i][j]);
                    g.fillRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);
                    // las celdas deben tener bordes correspondientes al equipo con un ancho de 2px
                    g.setColor(coloresTeam[i][j]);
                    if (coloresTeam[i][j] != Color.GRAY) {
                        g2.setStroke(new BasicStroke(3)); // grosor de 2 píxel
                    } else {
                        g2.setStroke(new BasicStroke(1)); // grosor de 1 píxel
                    }
                    g.drawRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);

                    g2.setStroke(new BasicStroke(1)); // grosor de 1 píxel
                    if (coloresTeam[i][j] != Color.GRAY) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.GRAY);
                    }

                    g.drawRect(j * CELDA_SIZE, i * CELDA_SIZE, CELDA_SIZE, CELDA_SIZE);

                }
            }
        }

        // Implementar los métodos de la interfaz MouseListener aquí
        @Override
        public void mouseClicked(MouseEvent e) {
            // Obtener la posición del clic
            int fila = e.getY() / CELDA_SIZE;
            int columna = e.getX() / CELDA_SIZE;
            System.out.println("Click en la fila " + fila + " y columna " + columna);

            selectCell(fila, columna, selectedTeam, selectedClass);

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // No es necesario hacer nada aquí
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // No es necesario hacer nada aquí
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // No es necesario hacer nada aquí
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // No es necesario hacer nada aquí
        }
    }

    public class ButtonPanel extends JPanel {

        private JPanel buttonPanel;
        private JButton btnPlay;
        private JButton btnPause;
        private JButton btnTeam;
        private JButton btnClass;
        private JButton btnNext;
        private JLabel labelInfo;

        public ButtonPanel() {
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

                selectedTeam++;
                if (selectedTeam > totalTeams) {
                    selectedTeam = 0;
                }
                btnTeam.setText("Team: " + selectedTeam);
            });
            btnClass.addActionListener((ActionEvent e) -> {
                // Modificar la variable selectedTeam aquí

                selectedClass++;
                if (selectedClass > totalClasses) {
                    selectedClass = 1;
                }
                btnClass.setText("Class: " + selectedClass);
            });
            btnNext.addActionListener((ActionEvent e) -> {
                // Modificar la variable selectedTeam aquí
                nextGeneration();
            });
            btnPlay.addActionListener((ActionEvent e) -> {
                // Modificar la variable selectedTeam aquí
                play = true;
            });
            btnPause.addActionListener((ActionEvent e) -> {
                // Modificar la variable selectedTeam aquí
                play = false;
            });

        }
    }

}
