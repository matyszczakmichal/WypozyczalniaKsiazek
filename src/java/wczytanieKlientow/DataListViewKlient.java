package wczytanieKlientow;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "listaklienci")
@ViewScoped
public class DataListViewKlient implements Serializable {

    private List<Klient> klient;

    private Klient wybrany;

    @ManagedProperty("#{listaKlient}")
    private KlientLista lista;

    @PostConstruct
    public void init() {
        klient = lista.tworzKlient();
    }

    public void odswiezKlientLista() {
        klient = lista.tworzKlient();
    }

    public List<Klient> getKlient() {
        return klient;
    }

    public void setService(KlientLista lista) {
        this.lista = lista;
    }

    public KlientLista getLista() {
        return lista;
    }

    public void setLista(KlientLista lista) {
        this.lista = lista;
    }

    public Klient getWybrany() {
        return wybrany;
    }

    public void setWybrany(Klient wybrany) {
        this.wybrany = wybrany;
    }
}
