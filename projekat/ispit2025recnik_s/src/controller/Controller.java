/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import baza.DBBroker;
import forme.ServerskaForma;
import java.util.List;
import model.Korisnik;
import model.Rec;

/**
 *
 * @author Novak
 */
public class Controller {

    private static Controller instance;
    private DBBroker dbb;
    private ServerskaForma serverskaForma;

    private Controller() {
        dbb = new DBBroker();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void setServerskaForma(ServerskaForma serverskaForma) {
        this.serverskaForma = serverskaForma;
    }

    public List<Korisnik> vratiKorisnike() {
        return dbb.vratiKorisnike();
    }

    public Korisnik login(Korisnik korisnik) {
        return dbb.login(korisnik);
    }

    public boolean logout(Korisnik korisnik) {
        return dbb.logout(korisnik);
    }

    public List<Rec> ucitajRecnik() {
        return dbb.ucitajRecnik();
    }

    public Rec pretraga(String unos) {
        List<Rec> recnik = ucitajRecnik();
        for (Rec rec : recnik) {
            if (rec.getSrpskaRec().equals(unos)) {
                return rec;
            }
        }
        return null;
    }

    public boolean unesi(Rec rec) {
        return dbb.unesi(rec);
    }

}
