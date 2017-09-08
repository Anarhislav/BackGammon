/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etf.backgammon.dp120263d;

import etf.backgammon.dp120263d.grafickiKorisnickiInterface.Tabla;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Drakulic
 */
public class Board {

    public static Board mainTable = null;

    public int[] table = new int[24];
    public int barWhite;
    public int barBlack;
    public int goalWhite;
    public int goalBlack;
    public Tabla tabla;
    private int lastUsedDice;

    public Board(Tabla t) {
        mainTable = this;
        this.tabla = t;
        ResetBoard();

    }

    public Board(Board board) {
        this.barBlack = board.barBlack;
        this.barWhite = board.barWhite;
        this.goalBlack = board.goalBlack;
        this.goalWhite = board.goalWhite;
        for (int i = 0; i < 24; i++) {
            this.table[i] = board.table[i];
        }
    }

    public void ResetBoard() {
        for (int i = 0; i < 24; i++) {
            table[i] = 0;
        }
        table[5] = 5;
        table[16] = -3;
        table[18] = -5;
        table[23] = 2;
        table[11] = -5;
        table[7] = 3;
        table[12] = 5;
        table[0] = -2;
        barBlack = 0;
        barWhite = 0;
        goalBlack = 0;
        goalWhite = 0;
        mainTable = this;
    }

    public boolean CheckWin(Igrac i) {
        boolean rez = false;
        if (i.boja == "white") {
            if (goalWhite == 15) {
                rez = true;
            }
        }
        if (i.boja == "black") {
            if (goalBlack == 15) {
                rez = true;
            }
        }
        return rez;
    }

    public int izracunajPoene(Igrac winnerRunda) {
        int poen = 1;
        if (winnerRunda.boja == "white") {

            for (int i = 6; i < 18; i++) {
                if (table[i] < 0 || goalBlack == 0) { //ima crnih van kuce
                    poen = 2;
                    break;
                }
            }
            for (int i = 0; i < 6; i++) {
                if (table[i] < 0 || barBlack > 0) {
                    poen = 3;
                    break;
                }
            }

        } else {
            for (int i = 6; i < 18; i++) {
                if (table[i] > 0 || goalWhite == 0) { //ima crnih van kuce
                    poen = 2;
                    break;
                }
            }
            for (int i = 0; i < 6; i++) {
                if (table[i] < 0 || barWhite > 0) {
                    poen = 3;
                    break;
                }
            }

        }
        return poen;
    }

    public int Heuristic(Igrac igrac) {
        int vrednost = 0;

        // System.out.println(igrac.boja);
        // vrednost += addBlock(igrac, 1);
        // System.out.println("zbog blokiranih figurica je:" + addBlock(igrac, 1));
        vrednost += addAtackAble(igrac, 1);
        // System.out.println("zbog atackAble figurica je:" + addAtackAble(igrac, 1));
        vrednost += addBarState(igrac, 6);
        //System.out.println("zbog barState figurica je:" + addBarState(igrac, 5));
        vrednost += addOutBoard(igrac, 2);
        //System.out.println("zbog outBoard figurica je:" + addOutBoard(igrac, 1));
        vrednost += addEnemyBoard(igrac, 4);
        //System.out.println("zbog EnemyBoard figurica je:" + addEnemyBoard(igrac, -2));
        vrednost += addGoal(igrac, 3);

        return vrednost;
    }

    public String pisiHeuristic(Igrac i) {
        StringBuilder s = new StringBuilder();
        s.append("ukupna heuristika je: " + Heuristic(i) + "\n");

        // s.append("add_block je: " + addBlock(i, 1));
        s.append(" add_atackAble je: " + addAtackAble(i, 1));
        s.append(" addBarState je: " + addBarState(i, 6));
        s.append(" addOutBoard je: " + addOutBoard(i, 1));
        s.append(" addEnemyBoard je: " + addEnemyBoard(i, 3));
        s.append(" addGoal je: " + addGoal(i, 3) + "\n");
        return s.toString();
    }

    public boolean isTerminal(Igrac igrac) {
        if (igrac.boja == "white" && goalWhite == 15) {
            return true;
        }
        if (igrac.boja == "black" && goalBlack == 15) {
            return true;
        }
        return false;
    }

    public int terminalValue(Igrac igrac) {
        int value = 0;

        if (igrac.boja == "white") {
            if (goalWhite == 15) {
                value = 50;
                for (int i = 6; i < 18; i++) {
                    if (table[i] < 0||goalBlack==0) {
                        value = 100;
                        break;
                    }
                }
                for (int i = 0; i < 6; i++) {
                    if (table[i] < 0 || barBlack > 0) {
                        value = 150;
                        break;
                    }
                }
            }
            if (goalBlack == 15) {
                value = -50;
                for (int i = 6; i < 18; i++) {
                    if (table[i] > 0||goalWhite==0) {
                        value = -100;
                        break;
                    }
                }
                for (int i = 18; i < 24; i++) {
                    if (table[i] > 0||barWhite>0) {
                        value = -150;
                        break;
                    }
                }
            }
        } else if (igrac.boja == "black") {
            if (goalWhite == 15) {
                value = -50;
                for (int i = 6; i < 18; i++) {
                    if (table[i] < 0||goalBlack==0) {
                        value = -2 * 50;
                        break;
                    }
                }
                for (int i = 0; i < 6; i++) {
                    if (table[i] < 0 || barBlack > 0) {
                        value = -3 * 50;
                        break;
                    }
                }
            }
            if (goalBlack == 15) {
                value = 50;
                for (int i = 6; i < 18; i++) {
                    if (table[i] > 0|| goalWhite == 0) {
                        value = 2 * 50;
                        break;
                    }
                }
                for (int i = 18; i < 24; i++) {
                    if (table[i] > 0||barWhite>0) {
                        value = 3 * 50;
                        break;
                    }
                }
            }
        }
        return value;
    }

    private int addBlock(Igrac igrac, int v) {
        int value = 0;

        for (int i = 0; i < 24; i++) { // da li je beli blokirao nekog crnog
            if (table[i] > 1) { //za svako polje gde imamo 2 figure
                for (int j = i - 1; (j >= (i - 6)) && (j >= 0); j--) {
                    if (table[j] < 0) { //zeton na poziciji i nam je blokiran od strane zetona sa pozicije j
                        value += v;

                    }
                }
            }
            if (table[i] < -1) { // da li je crni na poziciji j blokirao belog na poziciji i
                for (int j = i + 1; j < 24 && j <= (i + 6); j++) {
                    if (table[j] > 0) {
                        value -= v;

                    }
                }
            }
        }

        if (igrac.boja == "black") {
            value = -value;
        }
        return value;
    }

    private int addAtackAble(Igrac igrac, int v) {
        int value = 0;
        if (igrac.boja == "white") {
            for (int i = 18; i < 24; i++) {
                if (table[i] == 1) {
                    value -= v;
                }
            }
            for (int i = 0; i < 18; i++) {
                if (table[i] == 1) {
                    value -= 2 * v;
                }
            }
        } else {
            for (int i = 0; i < 6; i++) {
                if (table[i] == -1) {
                    value -= v;
                }
            }
            for (int i = 6; i < 24; i++) {
                if (table[i] == -1) {
                    value -= 2 * v;
                }
            }

        }
        return value;
    }

    private int addBarState(Igrac igrac, int v) {
        int value = 0;

        if (igrac.boja == "black") {
            value += v * barWhite;
        }
        if (igrac.boja == "white") {
            value += v * barBlack;
        }
        return value;

    }

    private int addOutBoard(Igrac igrac, int v) {
        int value = 0;
        if (igrac.boja == "white") {
            for (int i = 6; i < 18; i++) {
                if (table[i] > 0) {
                    value -= table[i] * v;
                }

            }
        }

        if (igrac.boja == "black") {
            for (int i = 6; i < 18; i++) {
                if (table[i] < 0) {
                    value += table[i] * v;
                }

            }
        }
        return value;
    }

    private int addEnemyBoard(Igrac igrac, int v) {
        int value = 0;

        if (igrac.boja == "white") {
            for (int i = 18; i < 24; i++) {
                if (table[i] > 0) {
                    value -= v * table[i];
                }
            }
        } else {
            for (int i = 0; i < 6; i++) {
                if (table[i] < 0) {
                    value += v * table[i];
                }
            }
        }
        return value;
    }

    private int addGoal(Igrac igrac, int v) {
        int value = 0;
        if (igrac.boja == "white") {
            value = goalWhite * v;
        }
        if (igrac.boja == "black") {
            value = goalBlack * v;
        }
        return value;
    }

    public List<Board> sviPotezi(int d1, int d2, Igrac igrac) {
        List<Board> lista = new ArrayList<Board>();

        if (igrac.boja == "white") {

            odigrajPrviKocku(d1, d2, lista, igrac);

            lista = odigrajDruguKocku(d1, d2, lista, igrac);

            if (d1 == d2) {
                lista = odigrajDruguKocku(d1, d2, lista, igrac);
                lista = odigrajDruguKocku(d1, d2, lista, igrac);
            }

        } else if (igrac.boja == "black") {

            odigrajPrviKocku(d1, d2, lista, igrac);

            lista = odigrajDruguKocku(d1, d2, lista, igrac);
            if (d1 == d2) {
                lista = odigrajDruguKocku(d1, d2, lista, igrac);
                lista = odigrajDruguKocku(d1, d2, lista, igrac);
            }

        }

        return lista;
    }

    private void odigrajPrviKocku(int d1, int d2, List<Board> lista, Igrac igrac) {
        Board b;
        if (igrac.boja == "white") {
            if (barWhite > 0) {
                if (table[24 - d1] == -1) { //ako radimo blot sa bar-a
                    b = new Board(this);
                    b.table[24 - d1] = 1;
                    b.barWhite--;
                    b.barBlack++;
                    b.lastUsedDice = d1;
                    dodajUListu(lista, b);

                }
                if (table[24 - d1] >= 0) {
                    b = new Board(this);
                    b.table[24 - d1]++;
                    b.barWhite--;
                    b.lastUsedDice = d1;
                    dodajUListu(lista, b);

                }
                if (table[24 - d2] == -1) {
                    b = new Board(this);
                    b.table[24 - d2] = 1;
                    b.barWhite--;
                    b.lastUsedDice = d2;
                    b.barBlack++;
                    dodajUListu(lista, b);

                }
                if (table[24 - d2] >= 0) {
                    b = new Board(this);
                    b.table[24 - d2]++;
                    b.barWhite--;
                    b.lastUsedDice = d2;
                    dodajUListu(lista, b);

                }
            } else {

                for (int i = 0; i < 24; i++) {
                    if (table[i] > 0) {
                        if (i - d1 < 0 && ReadyToTakeOff(igrac)) {
                            b = new Board(this);
                            b.table[i]--;
                            b.goalWhite++;
                            b.lastUsedDice = d1;
                            dodajUListu(lista, b);
                        }
                        if (i - d2 < 0 && ReadyToTakeOff(igrac)) {
                            b = new Board(this);
                            b.table[i]--;
                            b.goalWhite++;
                            b.lastUsedDice = d2;
                            dodajUListu(lista, b);
                        }
                        if (i - d1 >= 0 && table[i - d1] == -1) {
                            b = new Board(this);
                            b.table[i]--;
                            b.table[i - d1] = 1;
                            b.lastUsedDice = d1;
                            b.barBlack++;
                            dodajUListu(lista, b);
                        }
                        if (i - d1 >= 0 && table[i - d1] >= 0) {
                            b = new Board(this);
                            b.table[i]--;
                            b.table[i - d1]++;
                            b.lastUsedDice = d1;
                            dodajUListu(lista, b);
                        }
                        if (i - d2 >= 0 && table[i - d2] == -1) {
                            b = new Board(this);
                            b.table[i]--;
                            b.table[i - d2] = 1;
                            b.lastUsedDice = d2;
                            b.barBlack++;
                            dodajUListu(lista, b);
                        }
                        if (i - d2 >= 0 && table[i - d2] >= 0) {
                            b = new Board(this);
                            b.table[i]--;
                            b.table[i - d2]++;
                            b.lastUsedDice = d2;
                            dodajUListu(lista, b);
                        }
                    }
                }

            }

        } else if (igrac.boja == "black") {

            if (barBlack > 0) {
                if (table[d1 - 1] == 1) {
                    b = new Board(this);
                    b.table[d1 - 1] = -1;
                    b.barBlack--;
                    b.barWhite++;
                    b.lastUsedDice = d1;
                    dodajUListu(lista, b);

                }
                if (table[d1 - 1] <= 0) {
                    b = new Board(this);
                    b.table[d1 - 1]--;
                    b.barBlack--;
                    b.lastUsedDice = d1;
                    dodajUListu(lista, b);

                }
                if (table[d2 - 1] == 1) {
                    b = new Board(this);
                    b.table[d2 - 1] = -1;
                    b.barBlack--;
                    b.barWhite++;
                    b.lastUsedDice = d2;
                    dodajUListu(lista, b);

                }
                if (table[d2 - 1] <= 0) {
                    b = new Board(this);
                    b.table[d2 - 1]--;
                    b.barBlack--;
                    b.lastUsedDice = d2;
                    dodajUListu(lista, b);

                }
            } else {

                for (int i = 0; i < 24; i++) {
                    if (table[i] < 0) { //ovdi se nalazi black token
                        if (i + d1 > 23 && ReadyToTakeOff(igrac)) {
                            b = new Board(this);
                            b.table[i]++; //skidamo crnog odavle
                            b.goalBlack++;
                            b.lastUsedDice = d1;
                            dodajUListu(lista, b);
                        }
                        if (i + d2 > 23 && ReadyToTakeOff(igrac)) {
                            b = new Board(this);
                            b.table[i]++;
                            b.goalBlack++;
                            b.lastUsedDice = d2;
                            dodajUListu(lista, b);
                        }
                        if (i + d1 <= 23 && table[i + d1] == 1) { //crni moze blut da uradi
                            b = new Board(this);
                            b.table[i]++;
                            b.table[i + d1] = -1;
                            b.lastUsedDice = d1;
                            b.barWhite++;
                            dodajUListu(lista, b);
                        }
                        if (i + d1 <= 23 && table[i + d1] <= 0) { //crni pomera svoje figurice
                            b = new Board(this);
                            b.table[i]++;
                            b.table[i + d1]--;
                            b.lastUsedDice = d1;
                            dodajUListu(lista, b);
                        }
                        if (i + d2 <= 23 && table[i + d2] == 1) {
                            b = new Board(this);
                            b.table[i]++;
                            b.table[i + d2] = -1;
                            b.lastUsedDice = d2;
                            b.barWhite++;
                            dodajUListu(lista, b);
                        }
                        if (i + d2 <= 23 && table[i + d2] <= 0) {
                            b = new Board(this);
                            b.table[i]++;
                            b.table[i + d2]--;
                            b.lastUsedDice = d2;
                            dodajUListu(lista, b);
                        }
                    }
                }

            }

        }
    }

    private List<Board> odigrajDruguKocku(int d1, int d2, List<Board> lista, Igrac igrac) {
        int kocka;
        Board b;
        Board novi;
        List<Board> fin = new ArrayList<Board>();
        Iterator<Board> board = lista.iterator();
        int odigranaDruga = 0;

        while (board.hasNext()) {
            b = board.next();
            if (b.lastUsedDice == d1) {
                kocka = d2;
            } else {
                kocka = d1;
            }
            if (igrac.boja == "white") {
                if (b.barWhite > 0) {
                    if (b.table[24 - kocka] == -1) { //ako radimo blot sa bar-a
                        novi = new Board(b);
                        novi.table[24 - kocka] = 1;
                        novi.barWhite--;
                        novi.barBlack++;
                        novi.lastUsedDice = kocka;
                        odigranaDruga = 1;
                        dodajUListu(fin, novi);

                    }
                    if (b.table[24 - kocka] >= 0) {
                        novi = new Board(b);
                        novi.table[24 - kocka]++;
                        novi.barWhite--;
                        novi.lastUsedDice = kocka;
                        odigranaDruga = 1;
                        dodajUListu(fin, novi);
                    }
                    if (odigranaDruga == 0) {
                        dodajUListu(fin, b);
                    }
                } else {
                    for (int i = 0; i < 24; i++) {
                        if (b.table[i] > 0) {
                            if (i - kocka < 0 && ReadyToTakeOff(igrac)) {
                                novi = new Board(b);
                                novi.table[i]--;
                                novi.goalWhite++;
                                novi.lastUsedDice = kocka;
                                odigranaDruga = 1;
                                dodajUListu(fin, novi);
                            }

                            if (i - kocka >= 0 && b.table[i - kocka] == -1) {
                                novi = new Board(b);
                                novi.table[i]--;
                                novi.table[i - kocka] = 1;
                                novi.barBlack++;
                                novi.lastUsedDice = kocka;
                                odigranaDruga = 1;
                                dodajUListu(fin, novi);
                            }
                            if (i - kocka >= 0 && b.table[i - kocka] >= 0) {
                                novi = new Board(b);
                                novi.table[i]--;
                                novi.table[i - kocka]++;
                                novi.lastUsedDice = kocka;
                                odigranaDruga = 1;
                                dodajUListu(fin, novi);
                            }

                        }
                    }

                }

            } else if (igrac.boja == "black") {
                if (b.barBlack > 0) {
                    if (b.table[kocka - 1] == 1) { //ako radimo blot sa bar-a
                        novi = new Board(b);
                        novi.table[kocka - 1] = -1;
                        novi.barWhite++;
                        novi.barBlack--;
                        novi.lastUsedDice = kocka;
                        odigranaDruga = 1;
                        dodajUListu(fin, novi);

                    }
                    if (b.table[kocka - 1] <= 0) {
                        novi = new Board(b);
                        novi.table[kocka - 1]--;
                        novi.barBlack--;
                        novi.lastUsedDice = kocka;
                        odigranaDruga = 1;
                        dodajUListu(fin, novi);
                    }
                    if (odigranaDruga == 0) {
                        dodajUListu(fin, b);
                    }

                } else {
                    for (int i = 0; i < 24; i++) {
                        if (b.table[i] < 0) {
                            if (i + kocka > 23 && ReadyToTakeOff(igrac)) {
                                novi = new Board(b);
                                novi.table[i]++;
                                novi.goalBlack++;
                                novi.lastUsedDice = kocka;
                                odigranaDruga = 1;
                                dodajUListu(fin, novi);
                            }

                            if (i + kocka <= 23 && b.table[i + kocka] == 1) {   //blunt crni  
                                novi = new Board(b);
                                novi.table[i]++;
                                novi.table[i + kocka] = -1;
                                novi.barWhite++;
                                novi.lastUsedDice = kocka;
                                odigranaDruga = 1;
                                dodajUListu(fin, novi);
                            }
                            if (i + kocka <= 23 && b.table[i + kocka] <= 0) {
                                novi = new Board(b);
                                novi.table[i]++;
                                novi.table[i + kocka]--;
                                novi.lastUsedDice = kocka;
                                odigranaDruga = 1;
                                dodajUListu(fin, novi);
                            }

                        }
                    }
                }

            }
            if (odigranaDruga == 0) {
                dodajUListu(fin, b);
            }
        }

        return fin;
    }

    private void dodajUListu(List<Board> lista, Board b) { // dodaj u unikatnu listu
        boolean postoji = false;
        Iterator<Board> board = lista.iterator();
        while (board.hasNext()) {
            if (istiBoard(board.next(), b)) {
                postoji = true;
                break;
            }

        }
        if (postoji == false) {
            lista.add(b);
        }
    }

    private boolean istiBoard(Board b1, Board b2) { //vracamo true ako su isti boardovi
        boolean rez = true;

        for (int i = 0; i < 24; i++) {
            if (b1.table[i] != b2.table[i]) {
                rez = false;
                break;
            }
        }

        if (b1.barBlack != b2.barBlack) {
            rez = false;
        }

        if (b1.barWhite != b2.barWhite) {
            rez = false;
        }

        if (b1.goalBlack != b2.goalBlack) {
            rez = false;
        }

        if (b1.goalWhite != b2.goalWhite) {
            rez = false;
        }

        return rez;

    }

    private boolean ReadyToTakeOff(Igrac ig) {

        if (ig.boja == "white") {
            if (barWhite > 0) {
                return false;
            }
            for (int i = 6; i < 24; i++) {
                if (table[i] > 0) {
                    return false;
                }
            }
        }
        if (ig.boja == "black") {
            if (barBlack > 0) {
                return false;
            }
            for (int i = 17; i >= 0; i--) {
                if (table[i] < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String rez = "";
        StringBuilder r = new StringBuilder();

        for (int i = 12; i < 18; i++) {
            r.append(" | " + table[i] + " | ");
        }
        r.append(" |blackbar| " + barBlack + " |blackbar| ");
        for (int i = 18; i < 24; i++) {
            r.append(" | " + table[i] + " | ");
        }
        r.append(" |blackGoal| " + goalBlack + " ||| " + "\n" + "\n");

        for (int i = 11; i > 5; i--) {
            r.append(" | " + table[i] + " | ");
        }
        r.append(" |barWhite| " + barWhite + " |barWhite| ");
        for (int i = 5; i >= 0; i--) {
            r.append(" | " + table[i] + " | ");
        }
        r.append(" |goalWhite| " + goalWhite + " ||| ");

        rez = r.toString();
        return rez;
    }
}
