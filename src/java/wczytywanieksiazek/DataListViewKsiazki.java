package wczytywanieksiazek;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "listaksiazki")
@ViewScoped
public class DataListViewKsiazki implements Serializable {

    private List<Ksiazki> ksiazka;

    private Ksiazki wybrany;

    @ManagedProperty("#{listaks}")
    private KsiazkiLista lista;

    @PostConstruct
    public void init() {
        ksiazka = lista.tworzKsiazka();
    }

    public void odswiezKsiazkiLista() {
        ksiazka = lista.tworzKsiazka();
    }

    public List<Ksiazki> getKsiazka() {
        return ksiazka;
    }

    public void setService(KsiazkiLista lista) {
        this.lista = lista;
    }

    public KsiazkiLista getLista() {
        return lista;
    }

    public void setLista(KsiazkiLista lista) {
        this.lista = lista;
    }

    public Ksiazki getWybrany() {
        return wybrany;
    }

    public void setWybrany(Ksiazki wybrany) {
        this.wybrany = wybrany;
    }
}
