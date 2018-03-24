package wczytywanieksiazek;

import configuration.DatabaseConfiguration;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;

// klasa Książki 
public class Ksiazki implements Serializable {

    String idksiazka;
    String tytul;
    String pisarz;
    String iloscstron;
    String cena;
    int stanwmagazynie;

    public Ksiazki() {

    }

// konstruktor Ksiazki
    public Ksiazki(String idksiazka, String tytul, String pisarz, String iloscstron, String cena, int stanwmagazynie) {
        this.idksiazka = idksiazka;
        this.tytul = tytul;
        this.pisarz = pisarz;
        this.iloscstron = iloscstron;
        this.cena = cena;
        this.stanwmagazynie = stanwmagazynie;

    }

    public String getIdksiazka() {
        return idksiazka;
    }

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

    public int getStanwmagazynie() {
        return stanwmagazynie;
    }

    public void setIdksiazka(String idksiazka) {
        this.idksiazka = idksiazka;
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

    public void setStanwmagazynie(int stanwmagazynie) {
        this.stanwmagazynie = stanwmagazynie;
    }

// metoda odpowiadająca potwierdzenie wypożyczenia książki
    public void wypozycz() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String data = dateFormat.format(date);
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43

        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msga = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);

        String idosoba = (String) session.getAttribute("osobaID");
        try {

            try {
                Class.forName("org.mysql.jdbl.Driver");
            } catch (ClassNotFoundException err) {
            }

            // jeśli magazyn pusty to nie wypozyczaj
            if (stanwmagazynie == 0) {
                msga = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak książki w magazynie", "Spróbuj za parę dni");
                FacesContext.getCurrentInstance().addMessage(null, msga);
            } else {
                Connection con = java.sql.DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);

                Statement stmt = con.createStatement();
                String SQL = "INSERT INTO wypozyczenia(datawypozyczenia,id_osoba,id_ksiazka) VALUES ('" + data + "','" + idosoba + "','" + idksiazka + "');";

                boolean rs = stmt.execute(SQL);

                if (!rs) {
                    // jesli wypozyczono odejmij ilosc sztuk w magazynie o 1
                    int nowyStan = stanwmagazynie - 1;
                    String SQL1 = "UPDATE ksiazki SET stan_w_magazynie ='" + nowyStan + "' WHERE id_ksiazka = '" + idksiazka + "'";
                    rs = stmt.execute(SQL1);
                    msga = new FacesMessage(FacesMessage.SEVERITY_INFO, "Wypozyczono książkę ", "");
                    FacesContext.getCurrentInstance().addMessage(null, msga);
                } else {
                    msga = new FacesMessage(FacesMessage.SEVERITY_WARN, "Nie można zaktualizować danych pacjenta", "");
                    FacesContext.getCurrentInstance().addMessage(null, msga);
                }

            }
        } catch (SQLException err) {
            msga = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas łączenia z serwerem", "Błąd");
            FacesContext.getCurrentInstance().addMessage(null, msga);
            System.out.println(err.getMessage());
        }

        context.addCallbackParam("wypozycz", true);

        //System.out.println(idosoba + " " + idksiazka);
    }
}
