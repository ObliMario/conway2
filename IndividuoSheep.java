/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conway2;

/**
 *
 * @author mario
 */
public class IndividuoSheep extends Individuo {

    public IndividuoSheep(int estado, int team) {
        super(estado, team, 1);
    }

    @Override
    public int survivePoints(int[] vecinosOrdered, Individuo[] vecinos) {
        int survivePoints = 0;

        int[] vecinosTeam = getVecinosTeam(vecinosOrdered);
        if (vecinosTeam[1] + vecinosTeam[2] < 2 || vecinosTeam[1] + vecinosTeam[2] > 3) {
            survivePoints = 0;
        } else if (vecinosTeam[1] + vecinosTeam[2] == 2 || vecinosTeam[1] + vecinosTeam[2] == 3) {
            survivePoints = 100;
        }

        return survivePoints;
    }

    @Override
    public int probNacimiento(int parejas) {
        int probNacimiento = 0;
        if (parejas == 2) {
            probNacimiento = 30;
        } else {
            probNacimiento = 0;
        }
        return probNacimiento;
    }

    @Override
    public Individuo nacer() {
        return new IndividuoSheep(1, this.team);
    }

}
