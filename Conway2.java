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

        int serie = 50;
        int maxSeries = 51;
        int iterations = 0;
        int maxIterations = 1000;
        Individuo[][] matriz;
        MatrizConColores matrizConColores;

        while (serie < maxSeries) {
            iterations = 0;

            
            matriz = RandomMatriz(serie ,serie);
            matrizConColores = new MatrizConColores(matriz);
            serie++;
            
            System.out.println("iteraciones: " + iterations + " de " + maxIterations + "");
            
            while (iterations < maxIterations) {
                matrizConColores.nextGeneration();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                iterations++;
            }
            System.out.println("Series: " + serie + " de " + maxSeries + "");
            //Cerrar la ventana

        }
    }

    private static Individuo[][] RandomMatriz(int filas, int columnas) {
        Individuo[][] matriz = new Individuo[filas][columnas];
        Random random = new Random();
        int clase=1;
        int alive=0;
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                alive = random.nextInt(2);
                if (alive == 0) {
                    matriz[i][j] = new IndividuoMuerto();
                } else if(clase == 1){
                    matriz[i][j] = new IndividuoSheep(1, random.nextInt(2)+1);
                }
            }
        }
        return matriz;

    }

    
}

