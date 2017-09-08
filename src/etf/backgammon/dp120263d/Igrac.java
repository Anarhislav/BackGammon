/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etf.backgammon.dp120263d;

/**
 *
 * @author Drakulic
 */
public abstract class Igrac {
    
    public String boja;
    public Board board;
    
    abstract Board  Odigraj(int kocka1,int kocka2);
    abstract int Roll();
    abstract int FirstRoll();
    
    
}
