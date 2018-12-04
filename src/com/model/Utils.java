package com.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Utils {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/SALLE?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "";

    /**
     * registers a new user in the database
     *
     * @param nom    the name of the user
     * @param prenom the firstname of the user
     */
    public static void registerUser(String nom, String prenom, String mail, String password) throws SQLException, ClassNotFoundException {
        Connection connection;
        Statement statement;

        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USER, "");
        statement = connection.createStatement();

        String sql = "INSERT INTO USER(mail_user, nom_user, prenom_user, password) VALUES('" + mail + "','" + nom + "','" + prenom + "','" + password + "')";
        statement.executeUpdate(sql);

        statement.close();
        connection.close();

    }

    public static Utilisateur connectUser(String mail, String password) {
        Connection connection;
        Statement statement;
        Utilisateur user = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, "");
            statement = connection.createStatement();

            String sql = "SELECT * FROM user WHERE mail_user = "+ mail + " AND password = "+ password;
            ResultSet set = statement.executeQuery(sql);

            while(set.next())
                user = new Utilisateur(set.getString(2),set.getString(1),set.getString(0),set.getString(3));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<LocalTime> checkDispoDebutSalle(int id_salle, LocalDate date) {
        Connection connection;
        Statement statement;
        List<LocalTime> dates_dispos = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, "");
            statement = connection.createStatement();

            String sql = "SELECT Date_debut, Date_fin FROM RESERVATION WHERE Id_salle = " + id_salle +" AND Date_debut Like"+ date;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * reservation d'une salle
     * Le début et la fin de réservation sont traités en précondition
     * post-condition : ajouter reservation dans l'utilisateur après l'appel de cette fonction
     * @param reservation la réservation a effectuer
     */
    public static void reserveSalle(Reservation reservation) {
        Connection connection;
        Statement statement;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, "");
            statement = connection.createStatement();

            String sql = "INSERT INTO RESERVATION VALUES('"+ reservation.getSalle().getId() +"',"+ reservation.getUtilisateur().getMail() +
                    "','"+ reservation.getDateDebut() +"','"+ reservation.getDateFin() +")";
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post-condition : supprimer reservation de l'utilisateur après l'appel de cette fonction
     * @param reservation la reservation a supprimer
     */
    public static void annulerReservationSalle(Reservation reservation) {
        Connection connection;
        Statement statement;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, "");
            statement = connection.createStatement();

            String sql = "DELETE FROM reservation WHERE id_salle = "+ reservation.getSalle().getId() +" AND mail_user = "+ reservation.getUtilisateur().getMail();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * chiffre le mot de passe utilisateur
     * @param password le mot de pass à chiffrer
     * @return le mot de pass chiffré
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("sha-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
