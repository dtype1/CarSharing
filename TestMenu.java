
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;
 
/**
 * Un semplice menu di scelta "a finestre" che ha il solo scopo di testare il corretto funzionamento delle 
 * classi del package<br><strong>NOTA</strong> - Questo MENU DI TEST utilizza solo finestre di dialogo.
 *
 */
public class TestMenu {
    public static void main(String[] args) throws IOException {
        Gestione g;
        int codVeicolo = 0;
        int codNoleggio = 0;
        Cliente cliente;
        Veicolo veicolo;
        String esito;
        Date dataInizio;
        Date dataFine;
        //prova a caricare l'ultima istanza della gestione dell'autonoleggio salvata su file, se esiste
        try {
            g = Gestione.caricaDaFile();
        } catch (Exception e) {
            //crea una nuova gestione con un autonoleggio vuoto
            g = new Gestione();
            JOptionPane.showMessageDialog(null, "E' stato inizializzato un autonoleggio vuoto.", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
        String menu="                              GESTIONE AUTONOLEGGIO                            \n" +
                "- Gestisci noleggi\n" +
                "   1) Nuovo noleggio\n" +
                "   2) Rientro veicolo con aggiornamento dello storico noleggi\n" +
                "\n" +
                "- Visualizza\n" +
                "   3) Tutti i veicoli\n" +
                "   4) Veicoli diponibili nel periodo indicato\n" +
                "   5) Noleggi attivi\n" +
                "\n" +
                "- Gestisci veicoli\n" +
                "   6) Aggiungi veicolo\n" +
                "   7) Cancella veicolo\n" +
                "\n" +
                "- File\n" +
                "   8) Salva gestione su file\n" +
                "   9) Importa veicoli da file CSV\n" +
                "   10) Esporta veicoli su file CSV\n" +
                "\n" +
                "   0) Chiudi\n" +
                "\n" +
                "Scegli l'operazione da eseguire:\n";
        String stringa;
        int scelta =1;
        do {
           stringa = JOptionPane.showInputDialog(null, menu, "Autonoleggio v. 1.0", JOptionPane.PLAIN_MESSAGE);
           if(stringa == null) {
               //esce quando si preme 'Annulla'
               try {
                      g.salvaSuFile();
                  } catch (Exception e) {
                      int risposta = JOptionPane.showConfirmDialog(null, "Salvataggio su file non riuscito. Uscire lo stesso?\n"
                              + "Attenzione, se confermi perderai tutti i dati che inseriti dall'ultimo salvataggio.", "Errore", JOptionPane.WARNING_MESSAGE);
                      if(risposta == JOptionPane.OK_OPTION)
                          return;
                  }
               return;
           } 
            try {
                scelta =Integer.parseInt(stringa);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Formato numero della scelta errato.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                continue;
            }
           switch(scelta) {
              case  1:
                  codVeicolo = chiediCodiceVeicolo(g);
                  //controlla l'esito
                            if (codVeicolo == -1) {
                                JOptionPane.showMessageDialog(null, "Operazione annullata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                            else if (codVeicolo == -2) {
                                JOptionPane.showMessageDialog(null, "Codice insesistente", "Attenzione", JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                  dataInizio = chiediData("Inserisci la DATA INIZIALE nel formato: gg/mm/yyyy");
                  //controlla l'esito
                            if(dataInizio == null) { 
                                JOptionPane.showMessageDialog(null, "Operazione annullata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                  dataFine = chiediData("Inserisci la DATA di SCADENZA nel formato: gg/mm/yyyy");
                  //controlla l'esito
                            if(dataFine == null) { 
                                JOptionPane.showMessageDialog(null, "Operazione annullata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                            if(dataInizio.after(dataFine)){
                                JOptionPane.showMessageDialog(null, "Data di inizio e data di fine periodo incompatibili.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                            if(!g.isPrenotazionePossibile(codVeicolo, dataInizio, dataFine)) {
                                 JOptionPane.showMessageDialog(null, "Il veicolo richiesto non è disponibile nel periodo indicato.", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }    
                  cliente = chiediCliente();
                  //controlla l'esito
                            if(cliente == null) { 
                                JOptionPane.showMessageDialog(null, "Operazione annullata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                                    
                  g.aggiungiPrenotazione(codVeicolo, cliente, dataInizio, dataFine);
                  JOptionPane.showMessageDialog(null, "Operazione eseguita", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                  break;
              case  2:
                  codNoleggio = chiediCodiceNoleggio(g);
                  //controlla l'esito
                            if (codNoleggio == -1) {
                                JOptionPane.showMessageDialog(null, "Operazione annullata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                            else if (codNoleggio == -2) {
                                JOptionPane.showMessageDialog(null, "Codice insesistente", "Attenzione", JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                  esito = g.restituzioneVeicolo(codNoleggio, "storico_noleggi.csv");
                  //controlla l'esito dell'operazione
                            if (esito != null)
                                JOptionPane.showMessageDialog(null, esito, "Attenzione", JOptionPane.WARNING_MESSAGE);
                            else
                                JOptionPane.showMessageDialog(null, "Operazione eseguita", "Messaggio", JOptionPane.INFORMATION_MESSAGE); 
                            break;
              case 3:
                  JOptionPane.showMessageDialog(null, g.getElencoTuttiVeicoli(), "Tutti i veicoli", JOptionPane.PLAIN_MESSAGE);
                  break;
              case 4:
                  dataInizio = chiediData("Inserisci la DATA INIZIALE nel formato: gg/mm/yyyy");
                  //controlla l'esito
                            if(dataInizio == null) { 
                                JOptionPane.showMessageDialog(null, "Operazione annullata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                  dataFine = chiediData("Inserisci la DATA di SCADENZA nel formato: gg/mm/yyyy");
                  //controlla l'esito
                            if(dataFine == null) { 
                                JOptionPane.showMessageDialog(null, "Operazione annullata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                            if(dataInizio.after(dataFine)){
                                JOptionPane.showMessageDialog(null, "Data di inizio e data di fine periodo incompatibili.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                  JOptionPane.showMessageDialog(null, g.getElencoVeicoliDisponibili(dataInizio, dataFine), "Veicoli disponibili", JOptionPane.PLAIN_MESSAGE);
                  break;                   
              case 5:
                  JOptionPane.showMessageDialog(null, g.getElencoNoleggiAttivi(), "Noleggi in corso", JOptionPane.PLAIN_MESSAGE);
                  break;
              case 6:
                  veicolo = chiediVeicolo();
                  if(veicolo != null) {
                      g.aggiungiVeicolo(veicolo);
                      JOptionPane.showMessageDialog(null, "Operazione eseguita", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                  }
                  else
                      JOptionPane.showMessageDialog(null, "Operazione annullata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                  break; 
              case 7:
                  codVeicolo = chiediCodiceVeicolo(g);
                  //controlla l'esito
                            if (codVeicolo == -1) {
                                JOptionPane.showMessageDialog(null, "Operazione annullata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                            else if (codVeicolo == -2) {
                                JOptionPane.showMessageDialog(null, "Codice insesistente", "Attenzione", JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                  esito = g.cancellaVeicolo(codVeicolo);
                  //controlla l'esito                 
                            if(esito != null)
                                JOptionPane.showMessageDialog(null, esito, "Attenzione", JOptionPane.WARNING_MESSAGE);
                            else
                            JOptionPane.showMessageDialog(null, "Operazione eseguita", "Messaggio", JOptionPane.INFORMATION_MESSAGE);    
                  break;                   
              case 8:
                  try {
                      g.salvaSuFile();
                      JOptionPane.showMessageDialog(null, "Operazione eseguita", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                  } catch (Exception e) {
                      JOptionPane.showMessageDialog(null,"Operazione di salvataggio del file non riuscita", "Attenzione", JOptionPane.WARNING_MESSAGE);
                  }
                  break;
                 
              case 9:
                  try {
                      g.importaVeicoliDaCsv("autonoleggio.csv");
                      JOptionPane.showMessageDialog(null, "Importazione eseguita correttamente", "Attenzione", JOptionPane.PLAIN_MESSAGE);
                  } catch (Exception e) {
                      JOptionPane.showMessageDialog(null, "Importazione non riuscita", "Attenzione", JOptionPane.WARNING_MESSAGE);
                  }
                  
                  break;
              case 10:
                  try {
                      g.esportaVeicoliInCsv("autonoleggio.csv");
                      JOptionPane.showMessageDialog(null, "Esportazione eseguita correttamente", "Attenzione", JOptionPane.PLAIN_MESSAGE);
                  } catch (Exception e) {
                      JOptionPane.showMessageDialog(null, "Esportazione non riuscita", "Attenzione", JOptionPane.WARNING_MESSAGE);
                  }  
                  break;
                
              default: // OPZIONE NON PREVISTA
                  JOptionPane.showMessageDialog(null, "Scelta non prevista", "Attenzione", JOptionPane.WARNING_MESSAGE);
                  break;
              case  0: //ESCE
                  try {
                      g.salvaSuFile();
                  } catch (Exception e) {
                      int risposta = JOptionPane.showConfirmDialog(null, "Salvataggio su file non riuscito. Uscire lo stesso?\n"
                              + "Attenzione, se confermi perderai tutti i dati che hai inserito dall'ultimo salvataggio.", "Errore", JOptionPane.WARNING_MESSAGE);
                      if(risposta == JOptionPane.OK_OPTION)
                          return;
                  }
                  break;
           }
        }
      while(scelta != 0);
    }

	private static Veicolo chiediVeicolo() {
		// TODO Auto-generated method stub
		return null;
	}

	private static int chiediCodiceNoleggio(Gestione g) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static Cliente chiediCliente() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Date chiediData(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static int chiediCodiceVeicolo(Gestione g) {
		// TODO Auto-generated method stub
		return 0;
	}
}