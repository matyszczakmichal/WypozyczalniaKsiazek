package dodawanieKsiazek;

import configuration.DatabaseConfiguration;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
public class DodajKsiazke 
{
    private String tytul;
    private String pisarz;
    private String iloscstron;
    private String cena;
    private String dostepnosc;

    public String getTytul() {
        return tytul;
    }

    public String getPisarz() {
        return pisarz;
    }

    public String getIloscstron() {
        return iloscstron;
    }

    public String getCena() {
        return cena;
    }

    public String getDostepnosc() {
        return dostepnosc;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public void setPisarz(String pisarz) {
        this.pisarz = pisarz;
    }

    public void setIloscstron(String iloscstron) {
        this.iloscstron = iloscstron;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public void setDostepnosc(String dostepnosc) {
        this.dostepnosc = dostepnosc;
    }
        
    
     
    /// metoda dodaj() dodająca nową książkę do bazy
    public void dodaj () throws SQLException
    {
        RequestContext context = RequestContext.getCurrentInstance();                
        FacesMessage msg = null;
        
        try {
            //Połączenie z bazą danych            
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException err) {
            }
            Connection con = java.sql.DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);

            //Wykonanie wyrażenia SQL i dodanie rekordów
            Statement stmt = con.createStatement();
            String SQL = "INSERT INTO ksiazki(tytul,pisarz,ilosc_stron,cena,stan_w_magazynie) VALUES ('" + tytul + "','" + pisarz +"','" + iloscstron + "','" + cena + "','" + dostepnosc + "');";
            boolean rs = stmt.execute(SQL); 
            
            if(!rs)
            {
                           
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodano książkę do bazy ","Sukces");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                        else
                        {
                            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Błąd podczas dodawania książki", "Błąd");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                                        
                } catch (SQLException err) {
                    
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas łączenia z serwerem", "Błąd");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    System.out.println(err.getMessage());
                }

    }
     
        
 
}
