/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etf.backgammon.dp120263d;

import java.util.List;

/**
 *
 * @author Drakulic
 */
public class Igra {

    Board board;
    Igrac whiteIgrac;
    Igrac blackIgrac;
    Igrac prviIgra;
    Igrac drugiIgra;
    int IgraSeDo;
    public Igra(Igrac white, Igrac black, Board board,int IgraSeDo) {
        this.board = board;
        this.whiteIgrac = white;
        this.blackIgrac = black;
        this.IgraSeDo=IgraSeDo;
    }

    public Igrac FirstRoll() {
        Igrac winner = null;
        int win = 0;

        while (win == 0) {
            int w = whiteIgrac.FirstRoll();
           
            int b = blackIgrac.FirstRoll();
           // System.out.println("crni je rolao "+b);
            if (w > b) {
                win = 1;
                
                prviIgra = whiteIgrac;
                drugiIgra = blackIgrac;
                prviIgra.boja = "white";
                drugiIgra.boja = "black";
                
            }
            if (w < b) {
                win = 1;
                
                prviIgra = blackIgrac;
                drugiIgra = whiteIgrac;
                prviIgra.boja = "black";
                drugiIgra.boja = "white";
            }
        }
        winner=prviIgra;
       
        return winner;
    }

    public Igrac Game() {
        boolean igra = true;
        Igrac winner = null;
        int max;
        Board maxBoard=null;
        System.out.println("POCETNA TABLA");
        System.out.println(Board.mainTable+"\n");
        while (igra) {
            
            
            board.tabla.SledeciIgrac(prviIgra);
            board.tabla.napisi("na potezu je igrac "+prviIgra.boja);
            
            
            int dice1 = prviIgra.Roll();
            board.tabla.napisi("dobijeno je "+dice1+ "od strane igraca "+prviIgra.boja);
   
            
            
            List<Board> lista=board.sviPotezi(dice1 % 10 , dice1 / 10, prviIgra);
            
          
            board = prviIgra.Odigraj(dice1 % 10, dice1 / 10);
            board=Board.mainTable;
            System.out.println("NA POTEZU JE"+prviIgra.boja);
        System.out.println(Board.mainTable+"\n");
            board.tabla.OsveziTablu();
            if (board.CheckWin(prviIgra)) {
                igra = false;
                winner = prviIgra;
                break;
              
            }
            board.tabla.napisi("na potezu je igrac "+prviIgra.boja);
            board.tabla.SledeciIgrac(drugiIgra);
            dice1 = drugiIgra.Roll();
            board.tabla.napisi("dobijeno je "+dice1+ "od strane igraca "+prviIgra.boja);
          //  System.out.println("crni je rolao "+dice1);
            
            lista=board.sviPotezi(dice1 % 10 , dice1 / 10, drugiIgra);
            
          
            board = drugiIgra.Odigraj(dice1 % 10, dice1 / 10);
            board=Board.mainTable;
            board.tabla.OsveziTablu();
            System.out.println("NA POTEZU JE "+drugiIgra.boja);
        System.out.println(Board.mainTable+"\n");
           // System.out.println("crni je odigrao");
            //System.out.println(board);
            if (board.CheckWin(drugiIgra)) {
                igra = false;
                winner = drugiIgra;
                break;
            }
        }
        return winner;
    }
    
    public Igrac Partija(){
        
        
        Igrac winnerRunda = null;
        Igrac winner = null;
        int poen;
        
        
        int poeniBeli = 0;
        int poeniCrni = 0;
    
        Board.mainTable.tabla.setIgraSeDo(IgraSeDo);
        Board.mainTable.tabla.setHeuristic(whiteIgrac, blackIgrac);
        
        
        
        while (poeniBeli < IgraSeDo && poeniCrni < IgraSeDo) {
            Board.mainTable.tabla.setPoeniBeli(poeniBeli);
            Board.mainTable.tabla.setPoeniCrni(poeniCrni);
            
            FirstRoll();
            
            winnerRunda = Game();
            
            poen = Board.mainTable.izracunajPoene(winnerRunda);
            
            if (winnerRunda.boja == "white") {
                poeniBeli += poen;
            }
            if (winnerRunda.boja == "black") {
                poeniCrni += poen;
            }
            Board.mainTable.tabla.PobednikRunde(winnerRunda, poen);
            Board.mainTable.ResetBoard();
            Board.mainTable.tabla.OsveziTablu();
        }
        if (poeniBeli >= IgraSeDo) {
            winner = whiteIgrac;
        } else {
            winner = blackIgrac;
        }
        Board.mainTable.tabla.PobednikIgre(winner);
        return winner;
    }
    

}
