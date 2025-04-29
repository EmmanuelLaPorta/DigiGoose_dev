package it.digigoose.model;

import java.io.Serializable;

public class Casella implements Serializable {
    private int numero;
    private boolean speciale;
    private TipoEffettoCasella tipoEffetto;
    private static final long serialVersionUID = 1L;
    
    public Casella(int numero) {
        this.numero = numero;
        this.speciale = false;
        this.tipoEffetto = TipoEffettoCasella.NESSUNO;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public boolean isSpeciale() {
        return speciale;
    }
    
    public void setSpeciale(boolean speciale) {
        this.speciale = speciale;
    }
    
    public TipoEffettoCasella getTipoEffetto() {
        return tipoEffetto;
    }
    
    public void setTipoEffetto(TipoEffettoCasella tipoEffetto) {
        this.tipoEffetto = tipoEffetto;
        this.speciale = tipoEffetto != TipoEffettoCasella.NESSUNO;
    }
    
    public void applicaEffetto(Giocatore giocatore) {
        if (!isSpeciale()) return;
        
        switch (tipoEffetto) {
            case BALZO_E_RILANCIA:
                giocatore.setRichiedeRelancio(true);
                // Avanza di 3 caselle
                giocatore.setPosizione(giocatore.getPosizione() + 3);
                break;
                
            case PONTE:
                // Avanza di 6 caselle
                giocatore.setPosizione(giocatore.getPosizione() + 6);
                break;
                
            case RITORNO_INIZIO:
                // Torna alla casella di partenza
                giocatore.setPosizione(0);
                break;
                
            case RADDOPPIA_MOVIMENTO:
                // L'implementazione dipende dal controller, qui non facciamo nulla
                break;
                
            case FERMA_UN_TURNO:
                giocatore.setTurniSaltati(1);
                break;
                
            case FERMA_DUE_TURNI:
                giocatore.setTurniSaltati(2);
                break;
                
            case VOLA_AVANTI:
                // Avanza di 10 caselle
                giocatore.setPosizione(giocatore.getPosizione() + 10);
                break;
                
            case ATTENDI_DADO:
                // Deve attendere che un altro giocatore lanci un 6 per muoversi
                giocatore.setTurniSaltati(-1); // Valore speciale -1 indica attesa del dado
                break;
                
            case RILANCIA_TORNA_INDIETRO:
                giocatore.setRichiedeRelancio(true);
                // Torna indietro di 3 caselle
                giocatore.setPosizione(Math.max(0, giocatore.getPosizione() - 3));
                break;
                
            case RADDOPPIA_PUNTEGGIO:
                // L'implementazione dipende dal controller, qui non facciamo nulla
                break;
                
            case VAI_INDIETRO:
                // Torna indietro di 5 caselle
                giocatore.setPosizione(Math.max(0, giocatore.getPosizione() - 5));
                break;
                
            case LABIRINTO:
                // Torna alla casella 30
                giocatore.setPosizione(30);
                break;
                
            case PRIGIONE:
                // Resta fermo finché non fai 6
                giocatore.setTurniSaltati(-2); // Gestito diversamente nel controller
                break;
                
            default:
                break;
        }
    }
}