
import java.io.Serializable;
 

public class Veicolo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //Attributi
    
    private int codVeicolo;
    private String targa;
    private String marca;
    private String modello;
    private int nPosti;
    private boolean prenotato; //TRUE se il veicolo risulta prenotato, FALSE altrimenti
    
    //Metodi
    
    /**
    * Crea un veicolo con un codice identificativo fittizio uguale a zero.
    * @param targa targa del veicolo
    * @param marca nome della casa costruttrice del veicolo
    * @param modello modello del veicolo
    * @param nPosti numeri di posti del veicolo
    */
    public Veicolo(String targa, String marca, String modello, int nPosti) {
        codVeicolo = 0;
        this.targa = targa;
        this.marca = marca;
        this.modello = modello;
        this.nPosti = nPosti;
        prenotato = false;
    }
    
    /**
     * Crea un veicolo con codice identificativo fittizio uguale a zero a partire da un vettore di stringhe con i dati.
     * @param dati vettore di stringhe con i dati del veicolo
     */
    public Veicolo(String dati[]) {   
        codVeicolo = 0; 
        try {
            marca = dati[1];
            modello = dati[2];
            nPosti = Integer.parseInt(dati[3]);
            prenotato = Boolean.parseBoolean(dati[4]);
            targa = dati[5];
        } catch (NumberFormatException e) {
            nPosti = 0;
        } 
    }
    
     /**
     * Imposta il codice identificativo del veicolo.
     * @param codVeicolo codice identificativo
     */
    public void setCodVeicolo(int codVeicolo) {
        this.codVeicolo = codVeicolo;
    }
    /**
     * Imposta lo stato del noleggio del veicolo
     * @param prenotato <strong>true</strong> se il veicolo viene prenotato, <strong>false</strong> se il veicolo diventa 
     * disponibile per il noleggio.
     */
    public void setPrenotato(boolean prenotato) {
        this.prenotato = prenotato;
    }
    
    /**
    * Fornisce il codice identificativo del veicolo.
    * @return codice identificativo
    */
    public int getCodVeicolo() {
        return codVeicolo;
    }
    
    /**
    * Fornisce la targa del veicolo.
    * @return la targa
    */
    public String getTarga() {
        return targa;
    }
 
    /**
    * Fornisce la marca della casa costruttrice del veicolo.
    * @return la marca
    */
    public String getMarca() {
        return marca;
    }
 
    /**
    * Fornisce il modello del veicolo.
    * @return il modello
    */
    public String getModello() {
        return modello;
    }
 
    /**
    * Fornisce il numero di posti del veicolo.
    * @return numero di posti
    */
    public int getnPosti() {
        return nPosti;
    }
 
    /**
     * Verifica se il veicolo risulta o meno prenotato.
     * @return <strong>true</strong> se il veicolo risulta prenotato, <strong>false</strong> altrimenti
     */
    public boolean isPrenotato() {
        return prenotato;
    }
    
    /**
     * Metodo statico che restituisce una stringa con l'intestazione del file CSV con cui esportare la tabella dei veicoli.
     * @return stringa dell'intestazione del file CSV
     */
    public static String getIntestazioniCSV() {
        return "Cod.;Marca;Modello;n. Posti;Noleggiato;Targa"; 
    }
    
    /**
    * Fornisce i dati del veicolo sotto forma di una stringa di testo secondo il formato di un file CSV con il punto e virgola come delimitatore.
    * @return stringa con i dati del veicolo separati con punti e virgola
    */
    public String getVeicoloToRigaCsv() {
         return codVeicolo  + ";" + marca + ";" + modello + ";" + nPosti + ";" + prenotato + ";" + targa;
    }    
    
    @Override
    public String toString() {
        return "[cod. veicolo: " + codVeicolo + "] " + marca + " " + modello + " - "+ nPosti +" posti" + " - targa: " + targa;
    }
}