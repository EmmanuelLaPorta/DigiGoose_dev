package it.digigoose.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tabellone implements Serializable {
    private List<Casella> caselle;
    private static final long serialVersionUID = 1L;
    
    public Tabellone() {
        caselle = new ArrayList<>();
        inizializzaTabellone();
    }
    
    public List<Casella> getCaselle() {
        return caselle;
    }
    
    public Casella getCasella(int posizione) {
        if (posizione >= 0 && posizione < caselle.size()) {
            return caselle.get(posizione);
        }
        return null;
    }
    
    public void inizializzaTabellone() {
        // Creiamo prima tutte le caselle normali (63 caselle del gioco dell'oca)
        for (int i = 0; i <= 63; i++) {
            Casella casella = new Casella(i);
            aggiungiCasella(casella);
        }
        
        // Configura le caselle speciali
        // Le caselle dell'oca: tipicamente sono multiple di 9 nel gioco tradizionale
        for (int i = 9; i < 63; i += 9) {
            Casella casella = getCasella(i);
            casella.setSpeciale(true);
            casella.setTipoEffetto(TipoEffettoCasella.BALZO_E_RILANCIA);
        }
        
        // Altre caselle speciali
        getCasella(6).setTipoEffetto(TipoEffettoCasella.PONTE);
        getCasella(19).setTipoEffetto(TipoEffettoCasella.ATTENDI_DADO);
        getCasella(31).setTipoEffetto(TipoEffettoCasella.POZZO);
        getCasella(42).setTipoEffetto(TipoEffettoCasella.LABIRINTO);
        getCasella(52).setTipoEffetto(TipoEffettoCasella.PRIGIONE);
        getCasella(58).setTipoEffetto(TipoEffettoCasella.RITORNO_INIZIO);
    }
    
    public void aggiungiCasella(Casella casella) {
        caselle.add(casella);
    }
    
    public int getPosizioneMassima() {
        return caselle.size() - 1;
    }
}