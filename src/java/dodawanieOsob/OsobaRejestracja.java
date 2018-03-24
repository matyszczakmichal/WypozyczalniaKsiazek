/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodawanieOsob;

import configuration.DatabaseConfiguration;
import java.io.Serializable;
import static java.lang.System.err;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class OsobaRejestracja implements Serializable {
     private Osoba osoba = new Osoba();
     
    private boolean skip;
     
    public Osoba getOsoba() {
        return osoba;
    }
 
    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }
     
    
    // metoda dodaj() dodająca uzytkownika z index.html ( rejestracja samodzielna )
    public void dodaj() throws NoSuchAlgorithmException {  
        FacesMessage msg = null;
        
        String haslo = osoba.getHaslo();
        String powthaslo = osoba.getPowthaslo();
        
       try {
            //Połączenie z bazą danych            
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException err) {
            }
            Connection con = java.sql.DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);

            
            
            //Wykonanie wyrażenia SQL i dodanie rekordów
            
            // jeśli hasła są różne błąd
            if(haslo.equals(powthaslo))
            {
            Statement stmt = con.createStatement();
            String SQL = "INSERT INTO Osoba(imie,nazwisko,pesel,login,haslo) VALUES ('" + osoba.getImie() + "','" + osoba.getNazwisko() + "','" + osoba.getPesel() + "','" + osoba.getLogin() + "','" + haslo+ "');";
            boolean rs = stmt.execute(SQL);
            
            
            if(!rs)
            {
                        SQL = "SELECT MAX(id_osoba) AS max FROM Osoba;";
                        ResultSet result = stmt.executeQuery(SQL);
                        result.next();
                        String idOsoba = result.getString("max");
                        
                        SQL = "INSERT INTO stanowisko (id_osoba) VALUES ('" + idOsoba + "')";
                        rs = stmt.execute(SQL);
                        
                        if (!rs)
                        {                            
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie zarejestrowano ","Sukces");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                        else
                        {
                            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Błąd podczas tworzenia konta", "Błąd");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
            }
            else
            {
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła różnią się od siebie", "Błąd");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
            }
                    
                } catch (SQLException err) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas łączenia z serwerem lub pesel bądź login już istnieją", "Błąd");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    System.out.println(err.getMessage());
                }
        
       
            
        System.out.println(osoba.getImie() + osoba.getNazwisko() + osoba.getPesel() + osoba.getRodzaj() );

    }
    
}
