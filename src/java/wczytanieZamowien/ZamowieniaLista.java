package wczytanieZamowien;

import configuration.DatabaseConfiguration;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "listaZamowienia")
@ApplicationScoped
public class ZamowieniaLista {

    public List<Zamowienia> tworzZamowienie() throws SQLException {
        List<Zamowienia> zamowienia = new ArrayList<>();

        try {

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException err) {
            }
            Connection con = java.sql.DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);

            FacesContext ctx = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
            String idosobaSesja = (String) session.getAttribute("osobaID");
            int rodzaj = (Integer) session.getAttribute("rodzaj");

            // jesli rodzaj uzytkownika 2 to dodaj do listy tylko dane z idosoby w sesji
            if (rodzaj == 2) {
                Statement stmt = con.createStatement();
                String SQL = "SELECT * FROM wypozyczenia JOIN osoba ON wypozyczenia.id_osoba = osoba.id_osoba JOIN ksiazki ON ksiazki.id_ksiazka = wypozyczenia.id_ksiazka WHERE osoba.id_osoba = '" + idosobaSesja + "'";
                ResultSet rs = stmt.executeQuery(SQL);

                while (rs.next()) {
                    String idzamowienia = rs.getString("wypozyczenia.id_wypozyczenie");
                    String idksiazka = rs.getString("wypozyczenia.id_ksiazka");
                    String data = rs.getString("datawypozyczenia");
                    String zrealizowano = rs.getString("zrealizowano");
                    String idosoba = rs.getString("wypozyczenia.id_osoba");
                    String tytul = rs.getString("tytul");
                    String pisarz = rs.getString("pisarz");
                    String cena = rs.getString("cena");
                    String imie = rs.getString("imie");
                    String nazwisko = rs.getString("nazwisko");
                    String pesel = rs.getString("pesel");

                    zamowienia.add(new Zamowienia(idzamowienia, data, zrealizowano, idosoba, idksiazka, tytul, pisarz, cena, imie, nazwisko, pesel));

                }
            } else // w przeciwnym wypadku wszystkie zamówienia
            {
                try {
                    Statement stmt = con.createStatement();
                    String SQL = "SELECT * FROM wypozyczenia JOIN osoba ON wypozyczenia.id_osoba = osoba.id_osoba JOIN ksiazki ON ksiazki.id_ksiazka = wypozyczenia.id_ksiazka";
                    ResultSet rs = stmt.executeQuery(SQL);

                    while (rs.next()) {
                        String idzamowienia = rs.getString("wypozyczenia.id_wypozyczenie");
                        String idksiazka = rs.getString("wypozyczenia.id_ksiazka");
                        String data = rs.getString("datawypozyczenia");
                        String zrealizowano = rs.getString("zrealizowano");
                        String idosoba = rs.getString("wypozyczenia.id_osoba");
                        String tytul = rs.getString("tytul");
                        String pisarz = rs.getString("pisarz");
                        String cena = rs.getString("cena");
                        String imie = rs.getString("imie");
                        String nazwisko = rs.getString("nazwisko");
                        String pesel = rs.getString("pesel");

                        zamowienia.add(new Zamowienia(idzamowienia, data, zrealizowano, idosoba, idksiazka, tytul, pisarz, cena, imie, nazwisko, pesel));

                    }

                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            }

            return zamowienia;

        } catch (SQLException err) {

        }
        return zamowienia;
    }
}
