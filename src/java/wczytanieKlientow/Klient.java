/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wczytanieKlientow;

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
public class Klient {

    String idosoba;
    String imie;
    String nazwisko;
    String pesel;
    String login;
    String haslo;
    String rodzaj;

    public Klient() {

    }

    public Klient(String idosoba, String imie, String nazwisko, String pesel, String login, String haslo, String rodzaj) {
        this.idosoba = idosoba;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.login = login;
        this.haslo = haslo;
        this.rodzaj = rodzaj;
    }

    public String getIdosoba() {
        return idosoba;
    }

    public void setIdosoba(String idosoba) {
        this.idosoba = idosoba;
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

    public String getLogin() {
        return login;
    }

    public String getHaslo() {
        return haslo;
    }

    public String getRodzaj() {
        return rodzaj;
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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    // edycja klientów 
    public void edytuj() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msga = null;
        try {

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException err) {
            }
            Connection con = java.sql.DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);

            Statement stmt = con.createStatement();
            String SQL = "UPDATE osoba SET imie ='" + imie + "', nazwisko = '" + nazwisko + "', pesel = '" + pesel + "' WHERE id_osoba = '" + idosoba + "';";

            boolean rs = stmt.execute(SQL);

            if (!rs) {

                msga = new FacesMessage(FacesMessage.SEVERITY_INFO, "Zaktualizowano dane pracownika ", "");
                FacesContext.getCurrentInstance().addMessage(null, msga);
            } else {
                msga = new FacesMessage(FacesMessage.SEVERITY_WARN, "Nie można zaktualizować danych pracownika", "");
                FacesContext.getCurrentInstance().addMessage(null, msga);
            }

        } catch (SQLException err) {
            msga = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas łączenia z serwerem", "Błąd");
            FacesContext.getCurrentInstance().addMessage(null, msga);
            System.out.println(err.getMessage());
        }

        context.addCallbackParam("edytuj", true);

    }

}
