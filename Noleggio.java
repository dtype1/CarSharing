
import java.io.Serializable;
import java.util.Date;
 

public class Noleggio implements Serializable{
    private static final long serialVersionUID = 1L;
    
    //Attributi
    private int codNoleggio;
    private int codVeicolo; //codice del veicolo noleggiato
    private Cliente cliente; //riferimento al cliente che noleggia il veicolo
    private Date dataInizio;
    private Date dataFine;
    
    //Metodi
 
    public Noleggio(int codNoleggio, int codVeicolo, Cliente cliente, Date dataInizio, Date dataFine) {
        this.codNoleggio = codNoleggio;
        this.codVeicolo = codVeicolo;
        this.cliente = cliente;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }
 
    public int getCodVeicolo() {
        return codVeicolo;
    }
 
    public int getCodNoleggio() {
        return codNoleggio;
    }
 
    public Cliente getCliente() {
        return cliente;
    }
 
    public Date getDataInizio() {
        return dataInizio;
    }
 
    public Date getDataFine() {
        return dataFine;
    }    
}