package wczytanieZamowien;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "listazamowienia")
@ViewScoped
public class DataListViewZamowienia implements Serializable {

    private List<Zamowienia> zamowienie;

    private Zamowienia wybrany;

    @ManagedProperty("#{listaZamowienia}")
    private ZamowieniaLista lista;

    @PostConstruct
    public void init() {
        try {
            zamowienie = lista.tworzZamowienie();
        } catch (SQLException ex) {
            Logger.getLogger(DataListViewZamowienia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void odswiezZamowieniaLista() {
        try {
            zamowienie = lista.tworzZamowienie();
        } catch (SQLException ex) {
            Logger.getLogger(DataListViewZamowienia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Zamowienia> getZamowienia() {
        return zamowienie;
    }

    public void setService(ZamowieniaLista lista) {
        this.lista = lista;
    }

    public ZamowieniaLista getLista() {
        return lista;
    }

    public void setLista(ZamowieniaLista lista) {
        this.lista = lista;
    }

    public Zamowienia getWybrany() {
        return wybrany;
    }

    public void setWybrany(Zamowienia wybrany) {
        this.wybrany = wybrany;
    }
}
