/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conway2;

/**
 *
 * @author mario
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MatrizMouseListener implements MouseListener {
    private MatrizConColores matrizConColores;
    private static final int CELDA_SIZE = 20;
    
    public MatrizMouseListener(MatrizConColores matrizConColores) {
        this.matrizConColores = matrizConColores;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Obtener la posición del clic
        int fila = e.getY() / CELDA_SIZE;
        int columna = e.getX() / CELDA_SIZE;
        matrizConColores.selectCell(fila, columna);

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

