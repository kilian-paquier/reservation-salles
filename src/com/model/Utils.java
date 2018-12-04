package com.model;

import java.sql.*;

public class Utils {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/SALLE?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "";

    /**
     * creates the tables in database
     *
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
                    "(Id_user INTEGER not NULL, " +
                    " Nom_user VARCHAR(255), " +
                    " Prenom_user VARCHAR(255)," +
                    " PRIMARY KEY ( Id_user ))";

            stmt.executeUpdate(sql2);
            System.out.println("Created table in given database...");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

}
