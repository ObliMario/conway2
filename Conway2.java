/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.conway2;

import java.util.Random;

/**
 *
 * @author mario
 */
public class Conway2 {

    public static void main(String[] args) {
        System.out.println("Hello Conway!");
        int[][] matriz = {
                {1, 2, 0},
                {2, 0, 1},
                {0, 1, 2}
        };

        matriz = RandomMatriz(30, 60);

        MatrizConColores matrizConColores = new MatrizConColores(matriz);

        int maxIterations = 60;
        int iterations = 0;

        while (iterations < maxIterations) {
            matrizConColores.nextGeneration();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            iterations++;
        }
    }

    private static int[][] RandomMatriz(int filas, int columnas) {
        int[][] matriz = new int[filas][columnas];
        Random random = new Random();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = random.nextInt(3);
            }
        }
        return matriz;

    }

    
}

