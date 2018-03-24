package logowanie;

import configuration.DatabaseConfiguration;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import java.sql.Connection;
import java.sql.DriverManager;

@ManagedBean
@SessionScoped
public class Logowanie {

    private String idosoba;
    private String username;
    private String password;
    int rodzaj;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

// sprawdzanie czy któryś z użytkowników jest zalogowany czy nie, jeśli nie to nie można wejśc przez link do danego pliku xhtml
    public String sprawdzLogowanieKlient() {
        if (rodzaj == 2) {
            return null;
        } else {
            return "index.xhtml?faces-redirect=true";
        }
    }

    public String sprawdzLogowaniePracownik() {
        if (rodzaj == 1) {
            return null;
        } else {
            return "index.xhtml?faces-redirect=true";
        }
    }

    // metoda do wylogowywania
    public String wyloguj() {

        username = "";
        password = "";
        rodzaj = 0;

        return "index.xhtml?faces-redirect=true";

    }

    //metoda do zalogowania
    public void login() throws InstantiationException, IllegalAccessException {

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
        boolean pracownikLogged = false;
        boolean klientLogged = false;
        FacesMessage message = null;
        try {
            //Połączenie z bazą danych            
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException err) {
            }
            Connection con = DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);

            //Wykonanie wyrażenia SQL i załadowanie rekordów
            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM osoba JOIN stanowisko ON stanowisko.id_osoba = osoba.id_osoba WHERE login= '" + username + "' and haslo= '" + password + "'";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {

                idosoba = rs.getString("osoba.id_osoba");
                rodzaj = rs.getInt("rodzaj");
                System.out.println(rodzaj);
                System.out.println(idosoba);

                // zmiana zmiennych boolean na true w celu przeniesienia do odpowiedniego pliku
                if (rodzaj == 2) {
                    klientLogged = true;
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Witaj " + rs.getString("imie") + " " + rs.getString("nazwisko"), "");
                }
                if (rodzaj == 1) {
                    pracownikLogged = true;
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Witaj " + rs.getString("imie") + " " + rs.getString("nazwisko"), "");
                }

            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Niepoprawny login lub hasło", "Błąd logowania");
            }

        } catch (SQLException err) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas łączenia z serwerem", "Błąd");
            FacesContext.getCurrentInstance().addMessage(null, message);

            //System.out.println(err.getMessage());
        }

        // zapisanie atrybutów sesji
        session.setAttribute("osobaID", idosoba);
        session.setAttribute("rodzaj", rodzaj);

        // przeslanie danych do javascript w index.xhtml
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("klientLogged", klientLogged);
        context.addCallbackParam("pracownikLogged", pracownikLogged);

    }

}
