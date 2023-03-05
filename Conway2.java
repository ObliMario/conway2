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

        int serie = 45;
        int iterations = 0;
        int maxIterations = 30;
        Individuo[][] matriz;
        MatrizConColores matrizConColores;
        
        matriz = RandomMatriz(serie ,serie);
        matrizConColores = new MatrizConColores(matriz);
        
        while (true) {
            if(matrizConColores.play){
                iterations = 0;
                while (iterations < maxIterations && matrizConColores.play) {
                    matrizConColores.nextGeneration();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    iterations++;
                    if(iterations % 10 == 0) System.out.println("iteraciones: \n" + iterations + " de " + maxIterations + "");
                }
                matrizConColores.play = false;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Individuo[][] RandomMatriz(int filas, int columnas) {
        Individuo[][] matriz = new Individuo[filas][columnas];
        Random random = new Random();
        int clase;
        int alive;
        int team;
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                alive = random.nextInt(2);
                clase = random.nextInt(2)+1;
                team = random.nextInt(2)+1;
                if (alive == 0) {
                    matriz[i][j] = new IndividuoMuerto();
                } else if(clase == 1){
                    matriz[i][j] = new IndividuoSheep(1, team);
                } else if(clase == 2){
                    matriz[i][j] = new IndividuoWolf(1, team);
                }
            }
        }
        return matriz;

    }

    
}

