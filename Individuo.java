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
    protected int team;
    protected int clase;
    final int SHEEP = 1;
    final int WOLF = 2;

    public Individuo(int estado, int team, int clase) {
        if (estado == 0) {
            // Si el estado es 0, el individuo está muerto y será de la clase
            // IndividuoMuerto
            this.estado = 0;
            this.team = 0;
            this.clase = 0;

        } else if (estado == 1) {
            this.estado = 1;
            this.team = team;
            this.clase = clase;
            // System.out.println("estado: " + this.estado + " team: " + this.team + "
            // clase: " + this.clase);
        } else {
            this.estado = 2;
            this.team = 0;
            this.clase = 0;
            //System.out.println("Estado invalido");
        }
    }

    public Individuo createIndividuo(int selectedClass, int selectedTeam) {
        Individuo individuo;
        if (selectedTeam == 0) {
            return new IndividuoMuerto();
        }
        switch (selectedClass) {
            case SHEEP:
                individuo = new IndividuoSheep(1, selectedTeam);
                break;
            case WOLF:
                individuo = new IndividuoWolf(1, selectedTeam);
                break;
            default:
                individuo = new IndividuoInvalido();
                break;
        }
        return individuo;
    }

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

    /**
     * Devuelve un array de enteros que muestra la cantidad de Individuos [none,
     * myTeamSameClass, enemyTeamSameClass, myTeamDifferentClass,
     * enemyTeamDifferentClass]
     * 
     * @param thisIndividuo
     * @param allIndividuos
     * @return
     */
    public int[] getSameClass(Individuo thisIndividuo, Individuo[] allIndividuos, boolean thisInsideAllIndividuos) {

        int none = 0;
        int myTeamSameClass = 0;
        int enemyTeamSameClass = 0;
        int myTeamDifferentClass = 0;
        int enemyTeamDifferentClass = 0;

        for (int i = 0; i < allIndividuos.length; i++) {
            if (allIndividuos[i] != null) {
                if (allIndividuos[i].team == thisIndividuo.team && allIndividuos[i].clase == thisIndividuo.clase) {
                    myTeamSameClass += 1;
                } else if (allIndividuos[i].team != thisIndividuo.team
                        && allIndividuos[i].clase == thisIndividuo.clase) {
                    enemyTeamSameClass += 1;
                } else if (allIndividuos[i].team == thisIndividuo.team
                        && allIndividuos[i].clase != thisIndividuo.clase) {
                    myTeamDifferentClass += 1;
                } else if (allIndividuos[i].team != thisIndividuo.team
                        && allIndividuos[i].clase != thisIndividuo.clase) {
                    enemyTeamDifferentClass += 1;
                }
            } else {
                none += 1;
            }
        }
        if (thisInsideAllIndividuos)
            myTeamSameClass -= 1;

        return new int[] { none, myTeamSameClass, enemyTeamSameClass, myTeamDifferentClass, enemyTeamDifferentClass };
    }

    /**
     * Devuelve un nuevo individuo con el estado y el team correspondiente
     * 
     * @param vecinos //Todos los INDIVIDUOS vecinos del individuo
     * @return Individuo Nacido
     */
    public Individuo Nacimiento(Individuo[] vecinos) {
        // Recibo un array de individuos con todos los vecinos del individuo
        // contaré la cantidad de Individuos que tienen la misma clase y el mismo team
        // alrededor
        // de este individuo

        int[] nParejas = new int[vecinos.length];

        for (int i = 0; i < vecinos.length - 1; i++) {
            for (int j = i + 1; j < vecinos.length; j++) {
                if (vecinos[i] != null && vecinos[j] != null && vecinos[i].clase == vecinos[j].clase
                        && vecinos[i].team == vecinos[j].team) {
                    nParejas[i] += 1;
                    if (nParejas[i] > 2) {
                    }
                }
            }
        }

        // Reviso si hay alguien que tenga más de 2 parejas, en ese caso, saco todos
        // estos individuos de entre los candidatos

        for (int i = 0; i < nParejas.length - 1; i++) {
            if (nParejas[i] > 2) {
                for (int j = i; j < nParejas.length; j++) {
                    if (vecinos[i] != null && vecinos[j] != null && vecinos[i].clase == vecinos[j].clase
                            && vecinos[i].team == vecinos[j].team) {
                        nParejas[j] = 0;
                    }
                }
                nParejas[i] = 0;
            }
        }

        // Sacaré probabilidades de cada uno de reproducirse
        int[] probNacimiento = new int[vecinos.length];
        int probTotal = 0;
        for (int i = 0; i < vecinos.length; i++) {
            if (vecinos[i] != null && vecinos[i].estado == 1) {
                probNacimiento[i] = vecinos[i].probNacimiento(nParejas[i]);
                probTotal += probNacimiento[i];
            } else {
                probNacimiento[i] = 0;
            }
        }

        if (probTotal == 0) {
            return new IndividuoMuerto();
        }

        // Se eligirá al azar un individuo que se reproduzca

        Random rand = new Random();
        int prob = rand.nextInt(probTotal);
        int probAcum = 0;
        for (int i = 0; i < vecinos.length; i++) {
            probAcum += probNacimiento[i];
            if (probAcum > prob) {
                return vecinos[i].nacer();
            }
        }

        // Si llega hasta este punto hubo un error
        return new IndividuoInvalido();
    }

    /**
     * Retorna un array donde indica si los vecinos son [none, same, other]
     * 
     * @param vecinos
     * @return int[none, same, other]
     */
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

    public int probNacimiento(int parejas) {
        int probNacimiento = 0;
        if (parejas == 2) {
            probNacimiento = 30;
        } else {
            probNacimiento = 0;
        }
        return probNacimiento;
    }

    public Individuo nacer() {
        return new Individuo(1,this.team,this.clase);
    }
}
