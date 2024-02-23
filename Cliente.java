
import java.io.Serializable;
 

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //Attributi
   
    private String nominativo;
    private String documento;
    private String telefono;
    
    //Metodi
 
    public Cliente(String nominativo, String documento, String telefono) {
        this.nominativo = nominativo;
        this.documento = documento;
        this.telefono = telefono;
    }
 
    public String getNominativo() {
        return nominativo;
    }
 
    public String getDocumento() {
        return documento;
    }
 
    public String getTelefono() {
        return telefono;
    }
 
    @Override
    public String toString() {
        return nominativo + " - Rif. documento: " + documento + " - tel. " + telefono;
    }  
}