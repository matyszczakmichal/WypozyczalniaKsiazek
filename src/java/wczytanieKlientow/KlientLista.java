package wczytanieKlientow;

import configuration.DatabaseConfiguration;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "listaKlient")
@ApplicationScoped
public class KlientLista {

    public List<Klient> tworzKlient() {
        List<Klient> klient = new ArrayList<>();

        try {

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException err) {
            }
            Connection con = java.sql.DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);

            /// pobranie listy klientów
            Statement stmt = con.createStatement();
            String SQL = "SELECT osoba.id_osoba, imie, nazwisko, pesel, login, haslo, rodzaj FROM osoba JOIN stanowisko ON stanowisko.id_osoba = osoba.id_osoba;";
            ResultSet rs = stmt.executeQuery(SQL);

               while(rs.next())
            {
                String idosoba = rs.getString("osoba.id_osoba");
                String imie = rs.getString("imie");
                String nazwisko = rs.getString("nazwisko");
                String pesel = rs.getString("pesel");
                String login = rs.getString("login");
                String haslo = rs.getString("haslo");
                String rodzaj = rs.getString("rodzaj");
                
                if(rodzaj.equals("2"))
                {
                klient.add(new Klient(idosoba, imie, nazwisko, pesel, login, haslo, rodzaj));
                }
            
            
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

        return klient;

    }
}

