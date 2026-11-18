/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Korisnik;
import model.Prevod;
import model.Rec;

/**
 *
 * @author Novak
 */
public class DBBroker {

    public List<Korisnik> vratiKorisnike() {
        String upit = "SELECT * FROM USER";
        List<Korisnik> lista = new ArrayList<>();

        try {
            Statement st = Konekcija.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(upit);

            while (rs.next()) {
                Korisnik k = new Korisnik();
                k.setId(rs.getInt("id"));
                k.setIme(rs.getString("ime"));
                k.setPrezime(rs.getString("prezime"));
                k.setEmail(rs.getString("email"));
                k.setLozinka(rs.getString("lozinka"));
                k.setStatus(rs.getString("status"));

                lista.add(k);
            }

            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Korisnik login(Korisnik korisnik) {
        List<Korisnik> lista = vratiKorisnike();
        for (Korisnik k : lista) {
            if (k.equals(korisnik)) {
                //timestamp
                LocalDateTime sad = LocalDateTime.now();
                azurirajStatus(k, "prijavljen u " + sad);
                k.setStatus("prijavljen u " + sad);
                return k;
            }
        }
        return null;
    }

    private void azurirajStatus(Korisnik k, String status) {
        String upit = "UPDATE USER SET STATUS = ? WHERE ID = ?";
        try {
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
            ps.setString(1, status);
            ps.setInt(2, k.getId());

            int uspeh = ps.executeUpdate();
            if (uspeh > 0) {
                Konekcija.getInstance().getConnection().commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean logout(Korisnik korisnik) {
        try {//moze da se doda trycatch za svaki slucaj
            LocalDateTime sad = LocalDateTime.now();
            azurirajStatus(korisnik, "odjavljen u " + sad);
            return true; //ovo ce uvek da bude tacno jer ako bude nega greska ona e da bude uhvacena u azurirajStatus funkciji
        } catch (Exception e) {
            return false;
        }
    }

    public List<Rec> ucitajRecnik() {
        List<Rec> lista = new ArrayList<>();
        String upit = "SELECT * FROM REC";

        Statement st;
        try {
            st = Konekcija.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(upit);

            while (rs.next()) {
                Rec r = new Rec();
                r.setId(rs.getInt("id"));
                r.setSrpskaRec(rs.getString("srpska_rec"));

                lista.add(r);
            }
            //mora da se prodje kroz svaku rec i da se izvuku prevodi
            //ne moze join, jedna rec ima  vise prevoda i mogu da se dese duplikati u recniku
            for (Rec rec : lista) {
                upit = "SELECT * FROM prevod WHERE sprska_rec_id = " + rec.getId();
                System.out.println(upit);
                PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
                rs = ps.executeQuery();

                List<Prevod> prevodi = new ArrayList<>();
                while (rs.next()) {
                    Prevod p = new Prevod();
                    p.setId(rs.getInt("id"));
                    p.setEngRec(rs.getString("eng_rec"));
                    p.setSrpskaRec(rec);

                    prevodi.add(p);
                }
                rec.setPrevodi(prevodi);
            }

            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean unesi(Rec rec) {
        //prvo moram da ubacim novu reč i da uzmem njen id
        //onda taj id koristim za tabelu prevod

        String upit = "insert into rec (srpska_rec) values (?)";
        try {
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rec.getSrpskaRec());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int noviId = -1;
            if (generatedKeys.next()) {
                noviId = generatedKeys.getInt(1);
            } else {
                System.out.println("greska prilikom dobijanja id-ja nove reci");
            }

            List<Prevod> lista = rec.getPrevodi();

            for (Prevod prevod : lista) {
                upit = "insert into prevod (sprska_rec_id, eng_rec) values (?,?)";
                ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
                ps.setInt(1, noviId);
                ps.setString(2, prevod.getEngRec());

                ps.executeUpdate();
            }

            Konekcija.getInstance().getConnection().commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
