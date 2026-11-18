/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Novak
 */
public class ModelTabeleRecnik extends AbstractTableModel {

    private String[] kolone = {"srp", "eng"};
    private List<Rec> lista; //moze lista reci jer klasa Rec sadrzi listu prevoda

    public ModelTabeleRecnik(List<Rec> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rec r = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return r.getSrpskaRec();
            case 1:
                //elegantan nacin, 4. prikaz recnika 18:00
                //r.getPrevodi().stream().map(Prevod::getEngRec).reduce((a,b -> a+";"+b));
                
                //probaj da implementiras listu prevoda umesto reci, preko prevoda imas pristupi reci i imas prevod
                                
                //sirovinski nacin
                List<Prevod> prevodi = r.getPrevodi();
                String rezultat = "";
                for (Prevod prevod : prevodi) {
                    rezultat += prevod.getEngRec() + ";";
                }
                return rezultat;
                
            default:
                return "n/a";
        }
    }

}
