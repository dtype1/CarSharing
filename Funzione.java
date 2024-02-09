import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
 
/**
 * Libreria di metodi statici utili per testare il funzionamento delle 
 * classi del package.

 */
public class Funzione {
     /**
     * Chiede all'utente di inserire un codiceVeicolo controllandone l'esistenza nell'autonoleggio.
     * @param g la gestione corrente dell'autonoleggio di classe {@link Gestione}
     * @return <strong>codiceVeicolo</strong> inserito se esso è un codice veicolo esiste nell'autonoleggio, 
     * <strong>-1</strong> se l'operazione di input viene annullata, <strong>-2</strong> se il codice inserito 
     * non esiste nell'autonoleggio
     */
    public static int chiediCodiceVeicolo(Gestione g) {
        int nCodice;
        String n;
        do
            try {
                n = JOptionPane.showInputDialog(null, "Insierici il codice del veicolo: ", "Richiesta codice", JOptionPane.PLAIN_MESSAGE);
                //se l'utente annulla l'operazione di input
                if(n == null) {
                    return -1;
                }
                nCodice = Integer.parseInt(n);
                //controlla l'esistenza del codice
                if(!g.isPresenteVeicolo(nCodice))
                    //se il codice inserito non esiste
                    return -2;
                else
                //se il codice inserito esiste
                return nCodice;
            } catch (NumberFormatException e) {
                //se il codice inserito non è un numero intero
                JOptionPane.showMessageDialog(null, "Formato numerico errato. Il codice dei veicoli è un numero intero.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        while(true);
    }
    
    /**
     * Chiede all'utente di inserire il codice di un noleggio in corso, controllandone l'esistenza.
     * @param g la gestione corrente dell'autonoleggio di classe {@link Gestione}
     * @return <strong>codiceNoleggio</strong> inserito se esso è un codice di un noleggio che esiste, 
     * <strong>-1</strong> se l'operazione di input viene annullata, <strong>-2</strong> se il codice inserito non esiste
     */
    public static int chiediCodiceNoleggio(Gestione g) {
        int codNoleggio;
        String n;
        do
            try {
                n = JOptionPane.showInputDialog(null, "Insierici il codice del noleggio: ", "Richiesta codice", JOptionPane.PLAIN_MESSAGE);
                //se l'utente annulla l'operazione di input
                if(n == null) {
                    return -1;
                }
                codNoleggio = Integer.parseInt(n);
                //controlla l'esistenza del codice
                if(!g.isPresenteNoleggio(codNoleggio))
                    //se il codice inserito non esiste
                    return -2;
                else
                //se il codice inserito esiste
                return codNoleggio;
            } catch (NumberFormatException e) {
                //se il codice inserito non è un numero intero
                JOptionPane.showMessageDialog(null, "Formato numerico errato. Il codice dei noleggi è un numero intero.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        while(true);
    }
    
    /**
     * Chiede all'utente di inserire i dati del cliente che effettua il noleggio.
     * @return {@link Cliente} inserito o <strong>null</strong> se l'utente annulla l'inserimento
     */
    public static Cliente chiediCliente() {
        //si procura i dati del cliente da aggiungere
        String nominativo = JOptionPane.showInputDialog("Inserisci il nominativo"); if (nominativo == null) return null;
        String documento = JOptionPane.showInputDialog("Inserisci gli estremi del documento di riconoscimento"); if (documento == null) return null;
        String telefono = JOptionPane.showInputDialog("Inserisci il numero di telefono"); if (telefono == null) return null;
        return new Cliente(nominativo, documento, telefono);
    }
    
    /**
     * Chiede all'utente di inserire una data come un testo nel formato gg/mm/aaaa.
     * @param msg stringa di testo da visualizzare nella finestra di dialogo
     * @return la data convertita nel <strong>tipo Date</strong> o <strong>null</strong> se l'utente annulla l'inserimento
     */
    public static Date chiediData(String msg) {
        String s;
        Date d = new Date();
        do {
            s = JOptionPane.showInputDialog(null, msg, dateToString(d)); if (s == null) return null;
        //converte la stringa della data in un oggetto di classe Date
            try{
                DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
               //imposta che i calcoli di conversione della data siano rigorosi
               formatoData.setLenient(false);
               d = formatoData.parse(s);
               return d;
            } catch (ParseException e) {
                System.out.println("Formato data non valido.");
            }
        } while (true);  
    }
    
    /**
     * Chiede all'utente di inserire i dati di un veicolo.
     * @return {@link Veicolo} inserito o <strong>null</strong> se l'utente annulla l'inserimento
     */
    public static Veicolo chiediVeicolo() {
        String n;
        int nPosti;
        //si procura i dati del veicolo da aggiungere
        String targa = JOptionPane.showInputDialog("Inserisci la targa"); if (targa == null) return null;
        String marca = JOptionPane.showInputDialog("Inserisci la marca della casa costruttrice"); if (marca == null) return null;
        String modello = JOptionPane.showInputDialog("Inserisci il modello"); if (modello == null) return null;
        do {
            try {
                n = JOptionPane.showInputDialog("Inserisci il n. di posti");
                nPosti = Integer.parseInt(n);
                break;
            } catch (NumberFormatException e) {
                   JOptionPane.showMessageDialog(null, "Formato numero errato. Il numero di posti deve essere un numero intero.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        } while(true);
        Veicolo v = new Veicolo(targa, marca, modello, nPosti);
        return v;
    }
    
    /**
     * Converte una data di tipo Date in una stringa
     * @param d data di tipo Date da convertire
     * @return stringa della data
     */
    public static String dateToString(Date d) {
        DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        return formatoData.format(d);
    }
}