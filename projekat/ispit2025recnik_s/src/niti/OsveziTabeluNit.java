/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import forme.ServerskaForma;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Novak
 */
public class OsveziTabeluNit extends Thread {

    private ServerskaForma sf;

    public OsveziTabeluNit(ServerskaForma sf) {
        this.sf = sf;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(5000);
                sf.popuniTabelu();
                System.out.println("Osvezena tabela");
            } catch (InterruptedException ex) {
                Logger.getLogger(OsveziTabeluNit.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Nit za osvezavanje prekinuta");
                break;
            }
        }
    }

}
