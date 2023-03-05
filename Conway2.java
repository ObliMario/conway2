/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.conway2;

import java.io.Console;
import java.util.Random;

/**
 *
 * @author mario
 */
public class Conway2 {

    public static void main(String[] args) {
        System.out.println("Hello Conway!");

        Individuo[][] matriz = RandomMatriz(30, 30);

        MatrizConColores matrizConColores = new MatrizConColores(matriz);

        int maxIterations = 100;
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
        System.out.println("FIN");
    }

    private static Individuo[][] RandomMatriz(int filas, int columnas) {
        Individuo[][] matriz = new Individuo[filas][columnas];
        Random random = new Random();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = new Individuo(random.nextInt(2), random.nextInt(2)+1, 1);
            }
        }
        return matriz;

    }

    
}

