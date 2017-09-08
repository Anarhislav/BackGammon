/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etf.backgammon.dp120263d;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Drakulic
 */
public class Racunar extends Igrac {

    public String boja;
    public Board board;
    public int dubljinaStabla;

    public Racunar(String boja, Board board, int dubljinaStabla) {
        this.boja = boja;
        this.board = board;
        this.dubljinaStabla = dubljinaStabla;
    }

    @Override
    Board Odigraj(int kocka1, int kocka2) {

       
        double o = expectimax(Board.mainTable, this, kocka1, kocka2, dubljinaStabla, 0);
    
        return Board.mainTable;
    }

    @Override
    int Roll() {
        int d1 = (int) (Math.random() * 6 + 1);
        int d2 = (int) (Math.random() * 6 + 1);
        board.tabla.setVrednostiKocke(d1, d2, this);
        return d1 * 10 + d2;
    }

    @Override
    int FirstRoll() {

        int d1 = (int) (Math.random() * 6 + 1);
        int d2 = (int) (Math.random() * 6 + 1);
        board.tabla.setVrednostiKocke(d1, d2, this);
        return d1;
    }
    
    int kockice[] = {12, 13, 14, 15, 16, 23, 24, 25, 26, 34, 35, 36, 45, 46, 56};

    double expectimax(Board board, Igrac igrac, int dice1, int dice2, int maxDubina, int dubina) {
        double value = 0;
        String stanje = "max";
        double sumaI, sumaR;
        List<Board> potezi;
        Board t, maxBoard = null;
        Iterator b;
        if (board.isTerminal(igrac)) {
            return (double) board.terminalValue(igrac);
        }

        if (maxDubina == dubina) {
            
            return (double) board.Heuristic(igrac);
        }

        if (dubina % 2 == 1) {
            stanje = "chance";
        }
        if (dubina % 2 == 0) {
            stanje = "min";
        }
        if (dubina % 4 == 0) {
            stanje = "max";
        }

        if (stanje == "chance") {
            value = 0;
            sumaI = 0;
            sumaR = 0;
            for (int i = 1; i < 7; i++) {
                sumaI += expectimax(board, igrac, i, i, maxDubina, dubina + 1) / 36;
            }
            sumaI /= 6;
            for (int i = 0; i < 15; i++) {
                sumaR += expectimax(board, igrac, kockice[i] % 10, kockice[i] / 10, maxDubina, dubina + 1) / 18;
            }
            sumaR /= 15;
            value = sumaI + sumaR;
            return value;
        }

        if (stanje == "min") {
            value = +1000;
            potezi = board.sviPotezi(dice1, dice2, igrac);
            b = potezi.iterator();
            while (b.hasNext()) {
                t = (Board) b.next();
                double vr = expectimax(t, igrac, dice1, dice2, maxDubina, dubina + 1);
                if (value > vr) {
                    value = vr;
                }
            }
            return value;
        }

        if (stanje == "max") {
           
            value = -1000;
            potezi = board.sviPotezi(dice1, dice2, igrac);
            b = potezi.iterator();
          
            while (b.hasNext()) {
                
                t = (Board) b.next();
              
                double vr = expectimax(t, igrac, dice1, dice2, maxDubina, dubina + 1);

                if (value < vr) {
                    value = vr;

                    if (dubina == 0) {
                        
                        maxBoard = t;

                    }
                }
               
            }
            if(maxBoard!=null){
            maxBoard.tabla = Board.mainTable.tabla;
            Board.mainTable = maxBoard;}

            return value;
        }

        return -1;
    }

}
