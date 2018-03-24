package wczytywanieksiazek;

import configuration.DatabaseConfiguration;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "listaks")
@ApplicationScoped
public class KsiazkiLista {

    /// stworzenie oobiektu arraylist przechowujacego ksiazki pobrane z bazy
    public List<Ksiazki> tworzKsiazka() {
        List<Ksiazki> ksiazka = new ArrayList<>();

        try {

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException err) {
            }
            Connection con = java.sql.DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);

            // wyszukanie ksiazek za pomoca selecta
            Statement stmt = con.createStatement();
            String SQL = "SELECT id_ksiazka, tytul, pisarz, ilosc_stron, cena, stan_w_magazynie FROM ksiazki;";
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String idksiazka = rs.getString("id_ksiazka");
                String tytul = rs.getString("tytul");
                String pisarz = rs.getString("pisarz");
                String iloscstron = rs.getString("ilosc_stron");
                String cena = rs.getString("cena");
                int stanwmagazynie = rs.getInt("stan_w_magazynie");

                ///  nowy obiekt Ksiazki dodany do arraylist
                ksiazka.add(new Ksiazki(idksiazka, tytul, pisarz, iloscstron, cena, stanwmagazynie));

            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

        return ksiazka;

    }
}
