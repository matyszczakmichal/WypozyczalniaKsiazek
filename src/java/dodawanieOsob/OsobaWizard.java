package dodawanieOsob;

import configuration.DatabaseConfiguration;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;

@ManagedBean
@ViewScoped
public class OsobaWizard implements Serializable {

    //obiekt Klasy osoba, gettery i settery
    private Osoba osoba = new Osoba();

    private boolean skip;

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    // metoda dodaj() dodająca do bazy danych użytkownika poprzez wizarda
    public void dodaj() {
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
            String SQL = "INSERT INTO Osoba(imie,nazwisko,pesel,login,haslo) VALUES ('" + osoba.getImie() + "','" + osoba.getNazwisko() + "','" + osoba.getPesel() + "','" + osoba.getLogin() + "','" + osoba.getHaslo() + "');";
            boolean rs = stmt.execute(SQL);

            if (!rs) {
                SQL = "SELECT MAX(id_osoba) AS max FROM Osoba;";
                ResultSet result = stmt.executeQuery(SQL);
                result.next();
                String idOsoba = result.getString("max");

                SQL = "INSERT INTO stanowisko (rodzaj, id_osoba) VALUES ('" + osoba.getRodzaj() + "','" + idOsoba + "')";
                rs = stmt.execute(SQL);

                if (!rs) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodawanie pracownika do bazy ", "Sukces");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Błąd podczas dodawania pracownika", "Błąd");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Błąd podczas dodawania adresu", "Błąd");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (SQLException err) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas łączenia z serwerem lub wpisano istniejący pesel bądz login", "Błąd");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(err.getMessage());
        }

        System.out.println(osoba.getImie() + osoba.getNazwisko() + osoba.getPesel() + osoba.getRodzaj());

    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }
}
