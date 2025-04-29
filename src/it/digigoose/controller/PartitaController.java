package it.digigoose.controller;

// import java.io.IOException;
import java.util.List;

import it.digigoose.model.*;


public class PartitaController {
    private Partita partita;
    
    public PartitaController() {
    }
    
    public PartitaController(Partita partita) {
    	this();
        this.partita = partita;
    }
    
    public void iniziaPartita(List<Giocatore> giocatori) {
        partita = new Partita(giocatori);
        partita.determinaOrdineGiocatori();
        partita.inizializzaPosizioni();
        partita.setStato(StatoPartita.IN_CORSO);
    }
    
    public Giocatore getGiocatoreCorrente() {
        return partita.getGiocatoreCorrente();
    }
    
    public void passaTurno() {
        // Non è più necessario gestire il giroCorrente qui
        // poiché sarà gestito nella classe Partita
        partita.passaAlProssimoGiocatore();
    }
    
    public int getGiroCorrente() {
        return partita.getGiroCorrente();
    }
    
    public int[] tiraDadi() {
        // Per il gioco dell'oca classico, si usano due dadi a sei facce
        Dadi dadi = new Dadi(6, 2);
        return dadi.lancia();
    }
    
    public Casella muoviPedina(Giocatore giocatore, int passi) {
        int posizionePrecedente = giocatore.getPosizione();
        int nuovaPosizione = posizionePrecedente + passi;
        
        // Gestione limiti del tabellone
        if (nuovaPosizione > partita.getTabellone().getPosizioneMassima()) {
            int eccesso = nuovaPosizione - partita.getTabellone().getPosizioneMassima();
            nuovaPosizione = partita.getTabellone().getPosizioneMassima() - eccesso;
        }
        
        giocatore.setPosizione(nuovaPosizione);
        return partita.getTabellone().getCasella(nuovaPosizione);
    }
    
    public void applicaEffettoCasella(Casella casella, Giocatore giocatore) {
        if (casella.getNumero() == 63) {           
            partita.setStato(StatoPartita.TERMINATA);
        } else if (casella != null && casella.isSpeciale()) {
            casella.applicaEffetto(giocatore);
        }
    }
    
    public boolean verificaVincitore(Giocatore giocatore) {
        if (giocatore.getPosizione() == 63) {
            return true;
        }
        return false;
    }
    
    
    public Partita getPartita() {
        return partita;
    }
    
    public void setPartita(Partita partita) {
        this.partita = partita;
    }
    
    public void verificaELiberaGiocatoriBloccati(int[] valoreDadi) {
        // Controlla se qualcuno ha lanciato un 6 (per ATTENDI_DADO)
        boolean dado6Lanciato = false;
        for (int valore : valoreDadi) {
            if (valore == 6) {
                dado6Lanciato = true;
                break;
            }
        }
        
        // Se è stato lanciato un 6, libera tutti i giocatori in attesa di un dado 6
        if (dado6Lanciato) {
            for (Giocatore g : partita.getGiocatori()) {
                // -1 indica ATTENDI_DADO
                if (g.getTurniSaltati() == -1) {
                    g.setTurniSaltati(0); // Resetta, il giocatore è ora libero
                    System.out.println(g.getNome() + " è stato liberato e può giocare al prossimo turno!");
                }
            }
        }
        
        // Verifica se il giocatore corrente è in prigione (-2) e ha fatto 6
        Giocatore giocatoreCorrente = partita.getGiocatoreCorrente();
        if (giocatoreCorrente.getTurniSaltati() == -2 && dado6Lanciato) {
            giocatoreCorrente.setTurniSaltati(0); // Libera dalla prigione
            System.out.println(giocatoreCorrente.getNome() + " ha fatto 6 ed è uscito di prigione!");
        }
    }

}