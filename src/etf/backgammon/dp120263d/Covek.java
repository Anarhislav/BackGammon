/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etf.backgammon.dp120263d;

import etf.backgammon.dp120263d.grafickiKorisnickiInterface.Tabla;

/**
 *
 * @author Drakulic
 */
public class Covek extends Igrac {

    int firstRoll = 0;
    int roll = 0;
    int odigrao = 0;
    public Covek(String boja,Board board){
        this.boja=boja;
        this.board=board;
    }
    
    
    @Override
    Board Odigraj(int kocka1, int kocka2) {
        //board.tabla.napisi(boja+" odigraj potez sa"+ kocka1+kocka2);
        board.tabla.IskljuciSveDugmice();
        int iteracije = 1;
        if (kocka1 == kocka2) {
            iteracije++;
        }
        for (int i = 0; i < iteracije; i++) {
            if (!board.tabla.MoguciPoteziPickUp(kocka1, kocka2,this)) {
                //nema poteza za odigrati
                
                return board;
            }
            board.tabla.napisi("uzmi zeton "+boja+"y");
            board.tabla.waitForPickUp();
            
            board.tabla.IskljuciSveDugmice();
            
            board.tabla.MoguciPoteziZaPutDown(kocka1,kocka2);
            board.tabla.napisi("spusti zeton "+boja+"y");
            int d = board.tabla.waitForPutDown(); //vrati nam dice koji je iskoriscen
            board.tabla.IskljuciSveDugmice();
            int leftDice;
            if (d == kocka1) {
                leftDice = kocka2;
            } else {
                leftDice = kocka1;
            }
           
            if (!board.tabla.MoguciPoteziPickUp(leftDice,this)) {
               
                return board;
            }
            board.tabla.napisi("ostala ti je kockika "+leftDice+" podigni zeton"+boja);
            board.tabla.waitForPickUp();
            
            board.tabla.IskljuciSveDugmice();
            
            board.tabla.MoguciPoteziZaPutDown(leftDice);
            board.tabla.napisi("spusti zeton "+boja+"y");
            d=board.tabla.waitForPutDown();
            board.tabla.IskljuciSveDugmice();
        }
        return board;
    }

    @Override
    int Roll() {
        board.tabla.napisi(boja+" treba da odigra ROLL");
        int i = board.tabla.OmoguciRoll(this);
        board.tabla.IskljuciSveDugmice();
        return i;
    }

    @Override
    int FirstRoll() {
        board.tabla.napisi(boja+" treba da odigra roll");
        int i = board.tabla.OmoguciFirstRoll(this);
        
        board.tabla.IskljuciSveDugmice();
        return i;
    }

}
