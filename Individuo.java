/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conway2;

import java.util.Random;

/**
 *
 * @author mario
 */
public class Individuo {

    protected int estado;
    protected int clase;
    protected int team;

    public Individuo(int estado, int team, int clase) {
        if (estado == 0) {
            // Si el estado es 0, el individuo está muerto y será de la clase
            // IndividuoMuerto
            this.estado = 0;
            this.clase = 0;
            this.team = 0;

        } else {

            this.estado = 1;
            this.team = team;
            this.clase = clase;
            //System.out.println("estado: " + this.estado + " team: " + this.team + " clase: " + this.clase);
        }
    }

    public int survivePoints(int[] vecinos) {
        // Recibo un array de enteros con la cantidad de vecinos de cada team según su
        // posición en el array
        // El primer elemento es la cantidad de vecinos del team 0, el segundo elemento
        // es la cantidad de vecinos del team 1, etc.

        int survivePoints = 0;

        int[] vecinosTeam = getVecinosTeam(vecinos);
        if (vecinosTeam[1] < 2 || vecinosTeam[1] > 3) {
            survivePoints = 0;
        } else if (vecinosTeam[1] == 2 || vecinosTeam[1] == 3) {
            survivePoints = 100;
        }

        return survivePoints;
    }

    public Individuo NacimientoPoints(int[] vecinos) {
        Random random = new Random();
        int[] nacimientoPoints = new int[vecinos.length];
        int totalPoints = 0;
        int cont = 0;
        for (int i = 0; i < vecinos.length; i++) {
            if (vecinos[i] == 3 && i != 0) {
                nacimientoPoints[i] = 100;
                totalPoints += 100;
                cont++;
            } else {
                nacimientoPoints[i] = 0;
            }
        }
        if (cont == 0) {
            return new IndividuoMuerto();
        }

        int randomN = 0;
        while (randomN == 0) {
            randomN = random.nextInt(totalPoints);
        }

        for (int i = 0; i < nacimientoPoints.length; i++) {
            randomN -= nacimientoPoints[i];
            if (randomN <= 0) {
                if (i > 2 || i <= 0) {
                    System.out.println("Random invalido: "+i);
                    return new IndividuoInvalido();
                }
                return new IndividuoNormal(1, i, 1);
            }
        }
        System.out.println("Random invalido");

        return new IndividuoInvalido();
    }

    public int[] getVecinosTeam(int[] vecinos) {
        int[] vecinosTeam = new int[3];
        vecinosTeam[0] = vecinos[0];
        vecinosTeam[1] = vecinos[team];
        int enemyTeam = 0;

        for (int i = 1; i < vecinos.length; i++) {
            if (i != team) {
                enemyTeam += vecinos[i];
            }
        }
        vecinosTeam[2] = enemyTeam;
        return vecinosTeam;
    }

}
