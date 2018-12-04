package com.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Utils {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/SALLE?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "";

    /**
     * creates the tables in database
     * <p>
     * needs to be used only once !!!
     */
    public static void createTables() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();

            //Creation SALLE
            String sql = "CREATE TABLE SALLE " +
                    "(Id_salle INTEGER not NULL, " +
                    " Nom_salle VARCHAR(1024), " +
                    " PRIMARY KEY ( Id_salle ))";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            //Creation Reservation
            String sql1 = "CREATE TABLE RESERVATION " +
                    "(Id_salle INTEGER not NULL, " +
                    " Id_user INTEGER not NULL, " +
                    " Date_debut DATE, " +
                    " Date_fin DATE, " +
                    " PRIMARY KEY ( Id_salle, Id_user ))";

            stmt.executeUpdate(sql1);
            System.out.println("Created table in given database...");

            //Creation USER
            String sql2 = "CREATE TABLE USER " +
                    " (Mail_user VARCHAR(255) not NULL, " +
                    " Nom_user VARCHAR(255), " +
                    " Prenom_user VARCHAR(255)," +
                    " Password VARCHAR(255)," +
                    " PRIMARY KEY ( Mail_user ))";

            stmt.executeUpdate(sql2);
            System.out.println("Created table in given database...");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            }
            catch (SQLException ignored) {

            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

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

        String sql = "INSERT INTO USER(Mail_user, Nom_user, Prenom_user, mail, Password) VALUES('" + mail + "','" + nom + "','" + prenom + "','" + password + "')";
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

            String sql = "SELECT * FROM USER WHERE Mail = "+ mail + " AND Password = "+ password;
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

            String sql = "DELETE FROM RESERVATION WHERE Id_salle = "+ reservation.getSalle().getId() +" AND Mail_user = "+ reservation.getUtilisateur().getMail();
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
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
