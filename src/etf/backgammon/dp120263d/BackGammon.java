/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etf.backgammon.dp120263d;

import etf.backgammon.dp120263d.grafickiKorisnickiInterface.Menu;
import etf.backgammon.dp120263d.grafickiKorisnickiInterface.Tabla;

/**
 *
 * @author Drakulic
 */
public class BackGammon {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //  new Menu().setVisible(true);
        //  new Tabla().setVisible(false);
        Menu m;
        m = new Menu();
        m.setVisible(true);
        while(true){
        while(!m.pritisnutoDugme()){
            System.out.print("cekam da se pritisne dugme");
        }
        m.igra=0;
        
        
        Igra igra=new Igra(m.Beli(), m.Crni(), Board.mainTable,m.dokle());
        
        igra.Partija();
        m.ugasiTablu();
        m.setVisible(true);
        }
       // t.setVisible(false);
       /*
        Board tabla = new Board(null);
        Board.mainTable.tabla.setVisible(false);
        
        Igrac winnerRunda = null;
        Igrac winner = null;
        int poen;
        int igraSeDo = m.IgraSeDo();
        
        int poeniBeli = 0;
        int poeniCrni = 0;
        int tezina = m.Tezina();
        Igrac beli, crni;
        
        System.out.print(m.BeliIgrac());
        if (m.BeliIgrac() == "covek") {
            beli = new Covek("white", Board.mainTable);
        } else {
            beli = new Racunar("white", Board.mainTable, tezina);
        }
        
        if (m.CrniIgrac() == "covek") {
            crni = new Covek("black", Board.mainTable);
        } else {
            crni = new Racunar("black", Board.mainTable, tezina);
        }
        
        Board.mainTable.tabla.setIgraSeDo(1);
        Board.mainTable.tabla.setHeuristic(beli, crni);
        Igra igra = new Igra(beli, crni, Board.mainTable);
        
        while (poeniBeli < igraSeDo && poeniCrni < igraSeDo) {
            Board.mainTable.tabla.setPoeniBeli(poeniBeli);
            Board.mainTable.tabla.setPoeniCrni(poeniCrni);
            
            igra.FirstRoll();
            
            winnerRunda = igra.Game();
            
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
        if (poeniBeli >= igraSeDo) {
            winner = beli;
        } else {
            winner = crni;
        }
        Board.mainTable.tabla.PobednikIgre(winner);
        */
    }
    
}
