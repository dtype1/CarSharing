
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
 
/**
 * Classe fondamentale del package che incorpora un {@link Autonoleggio} e gestisce le 
 * sue <strong>prenotazioni/noleggi</strong> (di classe {@link Noleggio})<br>
 * <strong>NOTA</strong> - Funge da una sorta di classe wrapper di {@link Autonoleggio}.
 */
public class Gestione implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //Attributi
    
    private Autonoleggio autonoleggio;
    private Noleggio [] noleggi; //l'insieme delle prenotazioni dei noleggi
    private int numNoleggi; //n. tot. di noleggi
    private int codNoleggio; //ultimo codice generato per i noleggi
    
    public Gestione() {
        autonoleggio = new Autonoleggio();
        noleggi = new Noleggio[Autonoleggio.MAX_VEICOLI];
        numNoleggi = 0;
        codNoleggio = 0; //inizializza il contatore per il CODICE DEI NOLEGGI da gestire ad AUTOINCREMENTO all'inserimento di un nuovo NOLEGGIO
    }
    
    /**
     * Aggiunge un nuovo veicolo all'autonoleggio assegnando al veicolo un codice
     * identificativo numerico autoincrementale.
     * @param v il veicolo da aggiungere
    */
    public void aggiungiVeicolo(Veicolo v) {
        autonoleggio.aggiungiVeicolo(v);
    }
    
    /**
     * Fornisce la posizione occupata da un noleggio nelle prenotazioni.
     * @param codNoleggio codice del noleggio
     * @return indice di posizione occupata, oppure -1 se il noleggio non esiste
     */
    public int getPosizioneNoleggio(int codNoleggio) {
        for(int i=0; i<numNoleggi; i++) {
            if(noleggi[i].getCodNoleggio() == codNoleggio)
                return i;
        }
        return -1;
    }
    
    /**
    * Verifica l'esistenza di un veicolo a partire dal suo codice.
    * @param codVeicolo codice del veicolo
    * @return <strong>true</strong> se il veicolo esiste, <strong>false</strong> altrimenti
    */
    public boolean isPresenteVeicolo(int codVeicolo) {
        return autonoleggio.isPresenteVeicolo(codVeicolo);
    }
    
    /**
    * Verifica l'esistenza di un noleggio nelle prenotazioni a partire dal suo codice.
    * @param codNoleggio codice del noleggio
    * @return <strong>true</strong> se il noleggio esiste, <strong>false</strong> altrimenti
    */
    public boolean isPresenteNoleggio(int codNoleggio) {
        int pos = getPosizioneNoleggio(codNoleggio);
        if(pos < 0)
            return false;
        else
            return true;
    }
    
    /**
     * Verifica se il noleggio di un certo veicolo di cui si fornisce il codice può
     * essere effettuato nel periodo richiesto.
     * @param codVeicolo codice del veicolo
     * @param inizio data di inizio periodo
     * @param fine data di fine periodo
     * @return <strong>true</strong> se il noleggio è possibile, <strong>false</strong> altrimenti
     */
    public boolean isPrenotazionePossibile(int codVeicolo, Date inizio, Date fine) {
        boolean esito = true;
        Noleggio n;
        for(int i=0; i<numNoleggi; i++) {
            //si procura la prenotazione i-sima
            n = noleggi[i];
            //se essa riguarda il veicolo in oggetto
            if(n.getCodVeicolo() == codVeicolo) {
                //ipotizza la prenotazione non possibile
                esito = false;
                //verifica la compatibilità dei periodi
                if(fine.before(n.getDataInizio()) || inizio.after(n.getDataFine()))
                    esito = true;
                else
                    break;
            }
        }
        return esito;
    }
    
    /**
     * Aggiunge il noleggio di un veicolo nelle prenotazioni.
     * @param codVeicolo codice del veicolo da noleggiare
     * @param cliente Cliente che noleggia il veicolo
     * @param dataInizio data di inizio del noleggio
     * @param dataFine data di scadenza del noleggio
     * @return <strong>stringa di messaggio</strong> con la descrizione dell'errore,
     * se il codice non  esiste o il veicolo non è disponibile per il noleggio <strong>null</strong> altrimenti
     */
    public String aggiungiPrenotazione(int codVeicolo, Cliente cliente, Date dataInizio, Date dataFine) {
        if(!isPresenteVeicolo(codVeicolo))
            return "Codice del veicolo inesistente.";
        else if (!isPrenotazionePossibile(codVeicolo, dataInizio, dataFine)) {
            return "Il veicolo richiesto non è disponibile nel periodo indicato.";
        }
        else {
            //crea la prenotazione
            setVeicoloPrenotato(codVeicolo);
            codNoleggio++; //genera il codice del noleggio in autoincremento
            noleggi[numNoleggi] = new Noleggio(codNoleggio, codVeicolo, cliente, dataInizio, dataFine);
            numNoleggi++;
            return null;
        }
    }
    
    /**
     * Cancella un veicolo dall'autonoleggio a partire dal suo codice<br>
     * <strong>NOTA</strong> - Si accerta dell'esistenza del codice del veicolo ed effettua un
     * "ricompattamento" del vettore dei veicoli eliminando lo spazio vuoto creato nel
     * vettore con la cancellazione del veicolo.
     * @param codVeicolo codice identificativo del veicolo
     * @return <strong>stringa di messaggio</strong> con la descrizione dell'errore,
     * se il codice del veicolo non esiste, <strong>null</strong> altrimenti
     */    
    public String cancellaVeicolo(int codVeicolo) {
       return autonoleggio.cancellaVeicolo(codVeicolo);
    }
    
    /**
     * Porta un veicolo nello stato 'prenotato'.
     * @param codVeicolo codice del veicolo da noleggiare
     * @return <strong>stringa di messaggio</strong> con la descrizione dell'errore,
     * se codVeicolo è inesistente, <strong>null</strong> altrimenti
    */
    public String setVeicoloPrenotato(int codVeicolo) {
        return autonoleggio.setVeicoloPrenotato(codVeicolo);
    }
    
    /**
     * Cancella un noleggio al rientro del veicolo e aggiorna lo storico dei noleggi
     * su un file di testo in formato CSV che usa come delimitatore il carattere punto e virgola.
     * @param codNoleggio codice del noleggio
     * @param nomeFile nome del file su cui archiviare le registazioni dei noleggi
     * @return <strong>stringa di messaggio</strong> con la descrizione dell'errore,
     * se il codice del noleggio è errato, <strong>null</strong> altrimenti
     * @throws java.io.IOException in caso di errore di scrittura sul file
     */
    public String restituzioneVeicolo(int codNoleggio, String nomeFile) throws IOException {
        //si procura la posizione del noleggio
        int pos;
        pos = getPosizioneNoleggio(codNoleggio);
        if(pos < 0)
             return "Il 'codNoleggio' è errato.";
        //si procura codVeicolo
        int codVeicolo = noleggi[pos].getCodVeicolo();  
        //conta quante sono le prenotazioni di quel veicolo
        int np = 0; //contatore delle prenotazioni
        for(int i=0; i<numNoleggi; i++) {
            if(noleggi[i].getCodVeicolo() == codVeicolo)
                np++;
        }
        //se la prenotazione di quel veicolo è l'unica presente
        if(np ==1 ) {
            //porta il veicolo nello stato di 'non prenotato'
            autonoleggio.setVeicoloNonPrenotato(codVeicolo);        
        }
        //aggiorna il file dello storico dei noleggi
        aggiungiNoleggioToCsv(nomeFile, codNoleggio);
        //cancella la prenotazione del noleggio dal sistema e ricompatta il vettore dei noleggi per eliminare lo spazio vuoto
        noleggi[pos] = null;
        //crea un nuovo vettore dei noleggi per effettuare il travaso del vecchio vettore
        Noleggio [] n = new Noleggio[Autonoleggio.MAX_VEICOLI]; 
        int c = 0; //contatore il nuovo vettore dei noleggi
        for(int i=0; i<numNoleggi; i++) {
            if(i != pos){
                n[c] = noleggi[i];
                c++;
            }  
        }
        //aggiorna il riferimento del vettore dei noleggi con quello del vettore ricompattato
        noleggi = n;
        numNoleggi = c;
        return null;
        }  
    
    /**
    * Fornisce l'elenco dei noleggi in corso, con i dati dei veicoli e dei clienti.
    * @return stringa con l'elenco dei veicoli noleggiati
    */
    public String getElencoNoleggiAttivi() {
        String s = "";
        Veicolo v;
        Cliente c;
        for(int i=0; i<numNoleggi; i++) {
            //si procura il Veicolo i-simo noleggiato
            v = autonoleggio.getVeicolo(noleggi[i].getCodVeicolo());
            //si procura il Cliente corrispondente
            c = noleggi[i].getCliente();
            s += "[cod. noleggio: " + noleggi[i].getCodNoleggio() + "] " +
                    " dal " + dateToString(noleggi[i].getDataInizio()) + " al " + dateToString(noleggi[i].getDataFine()) +
                    " - "+ c.toString() + '\n' + v.toString() +
                    '\n' + "--------------------------------------------------------------------------------------------------------------------------" + '\n';
        }
        
        return s;
    }
    
    /**
     * Fornisce l'elenco di tutti i veicoli disponibili per il noleggio nel periodo richiesto.
     * @param dataInizio data inizio periodo
     * @param dataFine data fine periodo
     * @return stringa con l'elenco
     */
    public String getElencoVeicoliDisponibili(Date dataInizio, Date dataFine) {
        //si procura l'elenco dei veicoli disponibili che non hanno alcuna prenotazione
        String s = getElencoVeicoliNonPrenotati();
        //si procura l'elenco dei veicoli prenotati le cui prenotazioni non cadono nel periodo richiesto
        Veicolo v;
        for(int i=0; i<numNoleggi; i++) {
            //se il periodo richiesto è compatibile con quello della prenotazione i-sima  
            if(dataFine.before(noleggi[i].getDataInizio()) || dataInizio.after(noleggi[i].getDataFine())){
                //si procura il veicolo da includere fra quelli disponibili
                v = autonoleggio.getVeicolo(noleggi[i].getCodVeicolo());
                s += v.toString() + '\n';
            }
        }
        return s;
    }
    
    /**
     * Fornisce l'elenco dei veicoli che non risultano prenotati.
     * @return stringa con l'elenco
     */
    public String getElencoVeicoliNonPrenotati(){
        return autonoleggio.getVeicoliSenzaPrenotazioni();
    }
    
    /**
     * Fornisce l'elenco di tutti i veicoli che fanno parte del parco veicoli dell'autonoleggio.
     * @return stringa con l'elenco
     */
    public String getElencoTuttiVeicoli() {
        return autonoleggio.getTuttiVeicoli();
    }
    
    /**
     * Registra tutti i dati salvando l'oggetto di classe {@link Gestione} in un file binario di nome 
     * <strong>gestione.dat</strong> all'interno della directory corrente, tramite la serializzazione. 
     * @throws java.io.IOException In caso di errori di scrittura sul file.
     */
    public void salvaSuFile() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("gestione.dat"));
        out.writeObject(this);
        out.flush();
        out.close();
    }
    
    /**
     * Ricarica tutti i dati recuperando l'ultima istanza dell'oggetto di classe {@link Gestione} salvata in un 
     * file binario con il metodo {@link #salvaSuFile()}.
     * @return riferimento all'oggetto recuperato dal file
     * @throws java.io.IOException in caso di errori di input che non fanno andare a buon fine il recupero
     * @throws java.lang.ClassNotFoundException in caso di errori di serializzazione che non fanno andare a buon fine il recupero
     */
    public static Gestione caricaDaFile() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("gestione.dat"));
        Gestione g = (Gestione) in.readObject();
        in.close();
        return g;
    }
 
    /**
     * Esporta la <strong>tabella dei veicoli</strong> dell'autonoleggio in un file CSV<br>
     * <strong>NOTA</strong> - Il file CSV esportato presenta anche una riga di intestazione.
     * @param nomeFile Nome del file.
     * @throws java.io.IOException In caso di errori di scrittura sul file.
     */    
    public void esportaVeicoliInCsv(String nomeFile) throws IOException {
        autonoleggio.esportaInCsv(nomeFile);
    }
    
    /**
     * Importa veicoli da <strong>fonti di dati</strong> diverse (as es. fogli elettronici, database, altre applicazioni): 
     * aggiunge nuovi veicoli all'autonoleggio importandoli da un file in formato CSV che utilizza come carattere 
     * delimitatore il punto e virgola<br>
     * <strong>NOTA</strong> - Il file CSV ha una riga d'intestazione<br>
     * <strong>Suggerimento</strong> - Per saper 
     * qual è la struttura corretta del file CSV, eseguire prima un'esportazione in CSV.
     * @param nomeFile nome del file con suo percorso, da cui importare i veicoli
     * @throws java.io.FileNotFoundException in caso il file da cui importare non è disponibile o è insesistente
     */
    public void importaVeicoliDaCsv(String nomeFile) throws FileNotFoundException {
        autonoleggio.importaDaCsv(nomeFile);
    }
    
    /**
     * Aggiunge i <strong>dati di un noleggio</strong> nel file CSV dello storico dei noleggi<br>
     * <strong>NOTA</strong> - Il file CSV esportato ha anche la riga di intestazione.
     * @param nomeFile nome del file CSV dello storico dei noleggi
     * @param codNoleggio codice del noleggio da registrare nel file
     * @throws java.io.IOException in caso di errori di scrittura sul file
     */    
    public void aggiungiNoleggioToCsv(String nomeFile, int codNoleggio) throws IOException {
        File f = null;
        f = new File(nomeFile);
        //se il file non esiste ancora, aggiunge l'intestazione
        if (!f.exists()) {
            PrintWriter out = new PrintWriter(new FileWriter(nomeFile));
            out.println(getIntestazioniCSV());
            out.close();
        }
        //aggiunge la riga del noleggio al file aprendo il file in 'append'
        PrintWriter out = new PrintWriter(new FileWriter(nomeFile, true));
        out.println(getNoleggioToRigaCsv(codNoleggio));
        out.flush();
        out.close();
    }
    
    /**
     * Metodo statico che restituisce una stringa con l'intestazione del file CSV con cui 
     * esportare la tabella dei noleggi, con inoltre i dati del veicolo e del cliente.
     * @return stringa dell'intestazione del file CSV
     */
    public String getIntestazioniCSV() {
        return "Cod. noleggio" + ";" + "Data inizio" + ";" + "Data fine" +
                ";" + "Data rientro" + ";" + "targa" + ";" + "marca" + ";" + "modello" +
                ";" + "nominativo" + ";" + "documento" + ";" + "telefono";
    }
    
    /**
    * Fornisce i dati del noleggio sotto forma di una stringa di testo secondo il formato 
    * di un file CSV che utilizza il punto e virgola come delimitatore.
     * @param codNoleggio codice del noleggio
    * @return stringa con i dati del veicolo separati con punti e virgola
    */
    public String getNoleggioToRigaCsv(int codNoleggio) {
        String s = "";
        Date d = new Date();
        int pos = getPosizioneNoleggio(codNoleggio);
        int codVeicolo = noleggi[pos].getCodVeicolo();
        Veicolo v = autonoleggio.getVeicolo(codVeicolo);
        Cliente c = noleggi[pos].getCliente();
        s += noleggi[pos].getCodNoleggio() + ";" + dateToString(noleggi[pos].getDataInizio()) + ";" + 
                dateToString(noleggi[pos].getDataFine()) + ";" + d.toString() + ";" + v.getTarga() + 
                ";" + v.getMarca() + ";" + v.getModello() + ";" + c.getNominativo() + ";" + 
                c.getDocumento() + ";" + c.getTelefono();             
        return s;
    }
    
    /**
     * Formatta una data di tipo Date sotto forma di una stringa di testo.
     * @param d data di tipo Date
     * @return data sottoforma di stringa
     */
    public String dateToString(Date d) {
        DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        return formatoData.format(d);
    }
}