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
    
}