import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;
 

public class Autonoleggio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //Attributi
    
    public static final int MAX_VEICOLI = 100; //numero massimo di veicoli che l'autonoleggio potrà contenere
    private Veicolo [] veicoli; //insieme dei veicoli che compongono l'autonoleggio
    private int numVeicoli; //n. tot. di veicoli presenti nell'autonoleggio
    private int codVeicolo; //ulimo codice generato
    
    
    //Metodi
    
    /**
     * Crea un autonoleggio vuoto. 
     */
    public Autonoleggio() {
        codVeicolo = 0; //inizializza il contatore per il CODICE DEI VEICOLI da gestire ad AUTOINCREMENTO all'inserimento di un nuovo veicolo
        numVeicoli = 0; //N. dei VEICOLI PRESENTI nell'autonoleggio e PRIMA POSIZIONE LIBERA NELL'AUTONOLEGGIO
        veicoli = new Veicolo[MAX_VEICOLI]; //ISTANZIA il vettore dell'AUTONOLEGGIO (autonoleggio vuoto)
    }
    
    /**
     * Fornisce la posizione occupata da un veicolo nell'autonoleggio.
     * @param codVeicolo codice del veicolo
     * @return indice di posizione occupata, oppure -1 se il veicolo non esiste
     */
    public int getPosizioneVeicolo (int codVeicolo) {
        for(int i=0; i<numVeicoli; i++) {
            if(veicoli[i].getCodVeicolo() == codVeicolo) {
               return i;
            }
        }
        return -1; // codVeicolo non trovato
    }
    
    /**
     * Fornisce un Veicolo a partire dal suo codice.
     * @param codVeicolo codice identificativo del veicolo nell'autonoleggio
     * @return il veicolo
     */
    public Veicolo getVeicolo(int codVeicolo) {
        int pos = getPosizioneVeicolo(codVeicolo);
        return veicoli[pos];
    }
    
    /**
     * Aggiunge un nuovo veicolo all'autonoleggio assegnando al veicolo un codice
     * identificativo numerico autoincrementale.
     * @param veicolo il veicolo da aggiungere
    */
    public void aggiungiVeicolo(Veicolo veicolo) {
        codVeicolo++; //gestisce l'AUTOINCREMENTO del codice del veicolo
        veicolo.setCodVeicolo(codVeicolo);
        veicoli[numVeicoli] = veicolo; //aggiunge il veicolo all'autonoleggio
        numVeicoli++;
    }
    
    /**
     * Cancella un veicolo dall'autonoleggio a partire dal suo codice
     * <strong>NOTA</strong> - Si accerta dell'esistenza del codice del veicolo ed effettua un
     * "ricompattamento" del vettore dei veicoli eliminando lo spazio vuoto creato nel
     * vettore con la cancellazione del veicolo.
     * @param codVeicolo codice identificativo del veicolo
     * @return <strong>stringa di messaggio</strong> con la descrizione dell'errore,
     * se il codice del veicolo non esiste, <strong>null</strong> altrimenti
     */
    public String cancellaVeicolo(int codVeicolo) {
        int pos; //indice della posizione del veicolo da cancellare
        pos = getPosizioneVeicolo(codVeicolo);
        //se il codice è inesistente
        if(pos < 0)
            return "Attenzione: codice veicolo inesistente.";
        else {
            //crea un nuovo vettore di Veicoli
            Veicolo [] v = new Veicolo[Autonoleggio.MAX_VEICOLI];
            int j = 0; //indice del nuovo vettore creato
            for(int i=0; i<numVeicoli; i++) {
                if(i != pos) {
                    //"carica" il Veicolo nel nuovo vettore
                    v[j] = veicoli[i];
                    j++;
                }
            }
            //aggiorna il riferimento con quello del nuovo vettore
            veicoli = v;
            numVeicoli = j;
            return null;
        }  
    }
    
    /**
     * Porta un veicolo nello stato 'prenotato'<br>
     * <strong>NOTA</strong> - Se un veicolo si trova nello stato 'prenotato',
     * questa operazione NON è da considerarsi non conforme e NON viene segnalata 
     * (un veicolo può registrare più prenotazioni che rigauardano periodi diversi)
     * @param codVeicolo codice del veicolo da noleggiare
     * @return <strong>stringa di messaggio</strong> con la descrizione dell'errore,
     * se codVeicolo è inesistente, <strong>null</strong> altrimenti
    */    
    public String setVeicoloPrenotato(int codVeicolo) {
        int pos = this.getPosizioneVeicolo(codVeicolo);
        if(pos < 0)
            return "Attenzione: codice veicolo inesistente.";
        else {
            //imposta lo stato su 'prenotato'
            veicoli[pos].setPrenotato(true);
            return null;
            }   
        }
    
    /**
     * Esegue il rientro di un veicolo nell'autonoleggio, eventualmente portandolo
     * nello stato 'non prenotato'<br>
     * <strong>NOTA</strong> - Se un veicolo si trova nello stato 'non prenotato',
     * questa operazione è da considerarsi non conforme e viene segnalata.
     * @param codVeicolo codice del veicolo che rientra
     * @return <strong>stringa di messaggio</strong> con la descrizione dell'errore,
     * se il veicolo non esiste o non risulta essere in noleggio, <strong>null</strong> altrimenti
    */    
    public String setVeicoloNonPrenotato(int codVeicolo) {
        //si procura la posizione del veicolo nel vettore dei veicoli
        int pos = this.getPosizioneVeicolo(codVeicolo);
        //se il codice non esiste
        if(pos < 0)
           return "Attenzione: codice veicolo inesistente.";
       else {
            //se il veicolo non risulta noleggiato
           if(!veicoli[pos].isPrenotato())
               return "Attenzione: il veicolo non risuluta essere in noleggio.";
           else {
               //imposta lo stato 'non prenotato'
               veicoli[pos].setPrenotato(false);
               return null;
           } 
        }
    }
    
    /**
    * Verifica l'esistenza di un veicolo a partire dal suo codice.
    * @param codVeicolo codice del veicolo
    * @return <strong>true</strong> se il veicolo esiste, <strong>false</strong> altrimenti
    */
    public boolean isPresenteVeicolo(int codVeicolo) {
        int pos = getPosizioneVeicolo(codVeicolo);
        if(pos < 0)
            return false;
        else
            return true;
    }
    
    /**
     * Restituisce l'elenco dei veicoli che non risultano prenotati.
     * @return stringa con l'elenco dei veicoli senza prenotazioni
     */
    public String getVeicoliSenzaPrenotazioni() {
        String s = "";
        for(int i=0; i<numVeicoli; i++) {
            if(!veicoli[i].isPrenotato()) {
                s += veicoli[i].toString() + '\n';
            }  
        }
        return s;
    }
    
    /**
     * Restituisce l'elenco di tutti i veicoli dell'autonoleggio.
     * @return stringa dell'elenco
     */
    public String getTuttiVeicoli() {
        String s = "";
        for(int i=0; i<numVeicoli; i++) {
                s += veicoli[i].toString() + '\n';
        }
        return s;
    }
    
    /**
     * Esporta la tabella dei veicoli dell'autonoleggio in un file CSV<br>
     * <strong>NOTA</strong> - Il file CSV esportato ha anche la riga di intestazione.
     * @param nomeFile nome del file in cui esportare
     * @throws java.io.IOException in caso di errori di scrittura sul file
     */
    public void esportaInCsv(String nomeFile) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(nomeFile));
        out.println(Veicolo.getIntestazioniCSV());
        for(int i=0; i<numVeicoli; i++) {
            out.println(veicoli[i].getVeicoloToRigaCsv());
        }
        out.flush();
        out.close();
    }
 
    /**
     * Importa veicoli da <strong>fonti di dati</strong> diverse (as es. fogli elettronici, database, altre applicazioni): 
     * aggiunge nuovi veicoli all'autonoleggio importandoli da un file in formato CSV situato nella directory corrente<br>
     * Esso ha l'<strong>intestazione</strong> e utilizza come carattere delimitatore il punto e virgola.<br><br>
     * <strong>NOTA</strong> - Il file CSV deve avere la riga dell'intestazione. <strong>Suggerimento</strong>Per saper 
     * qual è la struttura corretta del file CSV, eseguire prima un'esportazione in CSV.
     * @throws java.io.FileNotFoundException in caso il file da cui importare non è disponibile o è insesistente
     */
    public void importaDaCsv(String nomeFile) throws FileNotFoundException {
        Veicolo v;
        String riga;
        //apre il file in lettura
        Scanner in = new Scanner(new FileReader(nomeFile));
        //salta la riga dell'intestazione
        in.nextLine();
        while(in.hasNextLine()) {
            //legge ciascuna riga
            riga = in.nextLine();
            //splitta i dati della riga in un vettore di stringhe
            String[] dati = riga.split(";");
            if(dati.length >1) {
                //crea un nuovo veicolo a partire dati contenuti nel vettore di stringhe generato
                v = new Veicolo(dati);
                //aggiunge il veicolo all'autonoleggio
                this.aggiungiVeicolo(v);
            }
        }
        in.close();
    }    
}