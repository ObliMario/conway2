/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conway2;

/**
 *
 * @author mario
 */
public class IndividuoWolf extends Individuo {

    public IndividuoWolf(int estado, int team) {
        super(estado, team, 2);
    }

    @Override
    public int survivePoints(int[] vecinosOrdered, Individuo[] vecinos) {
        int survivePoints;
        int[] sameClassArray;
        sameClassArray = getSameClass(this, vecinos, false);
        
        int AlliedWolves = sameClassArray[1]; //Allied wolves
        int myTeamDifferentClass = sameClassArray[3];
        
        int EnemyWolves = sameClassArray[2]; //Enemy wolves
        int enemyTeamDifferentClass = sameClassArray[4];

        if (AlliedWolves + myTeamDifferentClass < 2 || AlliedWolves + myTeamDifferentClass > 3) {
            survivePoints = 0;
        } else {
            survivePoints = 100;
            switch (EnemyWolves) {
                case 0 -> survivePoints -= 0;
                case 1 -> survivePoints -= 10;
                case 2 -> survivePoints -= 25;
                case 3 -> survivePoints -= 50;
                case 4 -> survivePoints -= 75;
                default -> survivePoints -= 100;
            }
        }

        return survivePoints;
    }

    @Override
    public int probNacimiento(int parejas) {
        int probNacimiento = 0;
        if (parejas == 2) {
            probNacimiento = 100;
        } else {
            probNacimiento = 0;
        }
        return probNacimiento;
    }

    @Override
    public Individuo nacer() {
        return new IndividuoWolf(1, this.team);
    }
    
}
