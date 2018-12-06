package com.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public abstract class Utils {
    private static Connection connection;
    private static Properties properties;
    private static String lastMail;

    /**
     * registers a new user in the database
     *
     * @param nom    the name of the user
     * @param prenom the firstname of the user
     */
    public static void registerUser(String nom, String prenom, String mail, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(mail_user, nom_user, prenom_user, password) value (?,?,?,?)");
        preparedStatement.setString(1, mail);
        preparedStatement.setString(2, nom);
        preparedStatement.setString(3, prenom);
        preparedStatement.setString(4, password);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }


    public static Salle getSalle(int id_salle) {
        Salle salle = null;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from salle where id_salle = ?");
            statement.setInt(1, id_salle);

            ResultSet set = statement.executeQuery();

            while (set.next())
                salle = new Salle(set.getInt(1), set.getString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salle;
    }

    public static Salle getSalle(String nom_salle) {
        Salle salle = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from salle where nom_salle = ?");
            preparedStatement.setString(1, nom_salle);

            ResultSet set = preparedStatement.executeQuery();

            while (set.next())
                salle = new Salle(set.getInt(1), set.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salle;
    }

    public static List<Salle> getSalles() {
        List<Salle> salles = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("select * from salle");
            ResultSet set = statement.executeQuery();
            while (set.next())
                salles.add(new Salle(set.getInt(1), set.getString(2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salles;
    }

    public static Utilisateur connectUser(String mail, String password) {
        Utilisateur user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE mail_user = ? and password = ?");
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);
            ResultSet set = preparedStatement.executeQuery();

            while (set.next())
                user = new Utilisateur(set.getString(3), set.getString(2), set.getString(1), set.getString(4));

            if (user != null) {
                preparedStatement = connection.prepareStatement("SELECT * from reservation where mail_user = ?");
                preparedStatement.setString(1, user.getMail());

                set = preparedStatement.executeQuery();
                while (set.next()) {
                    Salle salle = getSalle(set.getInt(1));
                    user.addReservation(new Reservation(user, salle, set.getDate(3), set.getDate(4)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<Reservation> checkAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT nom_user, prenom_user, nom_salle, date_debut, date_fin FROM " +
                            "(user INNER JOIN reservation ON user.mail_user = reservation.mail_user) INNER JOIN salle" +
                            " ON salle.id_salle = reservation.id_salle");
            ResultSet set = preparedStatement.executeQuery();

            while (set.next())
                reservations.add(new Reservation(new Utilisateur(set.getString(1), set.getString(0)), new Salle(set.getString(2)),
                        set.getDate(3), set.getDate(4)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public static List<Date> checkDispoDebutSalle(int id_salle, Date date) {
        List<Date> dates_dispos = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT date_debut, date_fin from reservation where id_salle = ? " +
                    "and date_debut like ?");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * reservation d'une salle
     * Le début et la fin de réservation sont traités en précondition
     * post-condition : ajouter reservation dans l'utilisateur après l'appel de cette fonction
     *
     * @param reservation la réservation a effectuer
     */
    public static void reserveSalle(Reservation reservation) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reservation value(?,?,?,?)");
            preparedStatement.setInt(1, reservation.getSalle().getId());
            preparedStatement.setString(2, reservation.getUtilisateur().getMail());
            preparedStatement.setDate(3, reservation.getDateDebut());
            preparedStatement.setDate(4, reservation.getDateFin());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post-condition : supprimer reservation de l'utilisateur après l'appel de cette fonction
     *
     * @param reservation la reservation a supprimer
     */
    public static void annulerReservationSalle(Reservation reservation) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM reservation WHERE id_salle = ? and date_debut = ? and date_fin = ?");
            preparedStatement.setInt(1, reservation.getSalle().getId());
            preparedStatement.setDate(2, reservation.getDateDebut());
            preparedStatement.setDate(3, reservation.getDateFin());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * chiffre le mot de passe utilisateur
     *
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

    public static void connection() {
        try {
            properties = new Properties();
            properties.loadFromXML(new FileInputStream("properties.xml"));

            /*String JDBC_DRIVER = properties.get("driver").toString();
            Class.forName(JDBC_DRIVER);*/
            String DB_URL = properties.get("bdd_url").toString();
            String PASS = properties.get("password").toString();
            String USER = properties.get("user").toString();
            lastMail = properties.get("lastMail").toString();

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getPassword(String mail) {
        String password = null;
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("select password from user where mail_user = ?");
            preparedStatement.setString(1, mail);

            ResultSet set = preparedStatement.executeQuery();

            while (set.next())
                password = set.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public static String getLastMail() {
        return lastMail;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Properties getProperties() {
        return properties;
    }
}
