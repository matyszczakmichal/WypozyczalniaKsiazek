/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wczytanieZamowien;

import configuration.DatabaseConfiguration;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Michal
 */
public class Zamowienia {

    String idzamowienia;
    String data;
    String zrealizowano;
    String idosoba;
    String idksiazka;
    String tytul;
    String pisarz;
    String cena;
    String imie;
    String nazwisko;
    String pesel;

    public Zamowienia() {

    }

    public Zamowienia(String idzamowienia, String data, String zrealizowano, String idosoba, String idksiazka, String tytul, String pisarz, String cena, String imie, String nazwisko, String pesel) {
        this.idzamowienia = idzamowienia;
        this.data = data;
        this.zrealizowano = zrealizowano;
        this.idosoba = idosoba;
        this.idksiazka = idksiazka;
        this.tytul = tytul;
        this.pisarz = pisarz;
        this.cena = cena;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getTytul() {
        return tytul;
    }

    public String getPisarz() {
        return pisarz;
    }

    public String getCena() {
        return cena;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public void setPisarz(String pisarz) {
        this.pisarz = pisarz;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getIdzamowienia() {
        return idzamowienia;
    }

    public String getData() {
        return data;
    }

    public String getZrealizowano() {
        return zrealizowano;
    }

    public String getIdosoba() {
        return idosoba;
    }

    public String getIdksiazka() {
        return idksiazka;
    }

    public void setIdzamowienia(String idzamowienia) {
        this.idzamowienia = idzamowienia;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setZrealizowano(String zrealizowano) {
        this.zrealizowano = zrealizowano;
    }

    public void setIdosoba(String idosoba) {
        this.idosoba = idosoba;
    }

    public void setIdksiazka(String idksiazka) {
        this.idksiazka = idksiazka;
    }

    // metoda realizujaca wypozyczenie ( zmienia pole zrealizowano z nie na tak w tabeli wypozyczenia)
    public void zrealizuj() throws SQLException {
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
            String SQL = "UPDATE wypozyczenia SET zrealizowano ='" + "TAK" + "' WHERE id_wypozyczenie = '" + idzamowienia + "'";
            boolean rs = stmt.execute(SQL);

            if (!rs) {

                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Zrealizowano zamówienie ", "Sukces");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Błąd podczas dodawania książki", "Błąd");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException err) {

            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas łączenia z serwerem", "Błąd");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(err.getMessage());
        }
        context.addCallbackParam("edytuj", true);
    }

}
