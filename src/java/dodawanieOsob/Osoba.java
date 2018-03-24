/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodawanieOsob;

import java.io.Serializable;

// klasa Osoba wykorzystywana do rejestracji
public class Osoba implements Serializable {

    private String imie;
    private String nazwisko;
    private String pesel;

    private String login;
    private String haslo;
    private String powthaslo;
    private String rodzaj;

    public String getPowthaslo() {
        return powthaslo;
    }

    public void setPowthaslo(String powthaslo) {
        this.powthaslo = powthaslo;
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

}
