/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Novak
 */
public class Prevod implements Serializable {

    private int id;
    private Rec srpskaRec;
    private String engRec;

    public Prevod() {
    }

    public Prevod(int id, Rec srpskaRec, String engRec) {
        this.id = id;
        this.srpskaRec = srpskaRec;
        this.engRec = engRec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rec getSrpskaRec() {
        return srpskaRec;
    }

    public void setSrpskaRec(Rec srpskaRec) {
        this.srpskaRec = srpskaRec;
    }

    public String getEngRec() {
        return engRec;
    }

    public void setEngRec(String engRec) {
        this.engRec = engRec;
    }

}
