package integration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by Aquador on 2016-12-11.
 */
public class sql {
    //java -cp .;mysql-connector-java-5.1.39-bin.jar DBJDBCM

    // DB connection variable
    static protected Connection con;
    // DB access variables
    private String URL = "jdbc:mysql:///allsporttvapp";
    private String driver = "com.mysql.jdbc.Driver";
    private String userID = "root";

    private String password = readPw();

    public sql() throws FileNotFoundException {
    }

    private String readPw() throws FileNotFoundException {
        try {
            FileReader fr = new FileReader("../db.txt");
            char[] a = new char[50];
            fr.read(a); // reads the content to the array
            password = new String(a);

        }catch(Exception e){
            System.out.println("Type in the pw instead.");
        }

        return password;
    }
    // method for establishing a DB connection
    public void connect()
    {
        try
        {
            // register the driver with DriverManager
            Class.forName(driver);
            //create a connection to the database
            con = DriverManager.getConnection(URL, userID, password);
            // Set the auto commit of the connection to false.
            // An explicit commit will be required in order to accept
            // any changes done to the DB through this connection.
            con.setAutoCommit(false);
            //Some logging
            System.out.println("Connected to " + URL + " using "+ driver);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet selectAllArenas() throws Exception{
        // Local variables
        String query;
        ResultSet rs;
        Statement stmt;

        // Set the SQL statement into the query variable
        query = "SELECT Namn,Stad,Kapacitet FROM Arena";

        // Create a statement associated to the connection con.
        // The new statement is placed in the variable stmt.
        stmt = con.createStatement();

        // Execute the SQL statement that is stored in the variable query
        // and store the result in the variable rs.

        rs = stmt.executeQuery(query);
        // Close the variable stmt and release all resources bound to it
        // Any ResultSet associated to the Statement will be automatically closed too.
        return rs;

    }

    public void createArena(String namn, String stad, int kapacitet) throws Exception{
        // Local variables
        String query;
        PreparedStatement stmt;

        // Set the SQL statement into the query variable
        query = "INSERT INTO Arena(Namn,Stad,Kapacitet) VALUES (?, ?, ?)";

        // Create a statement associated to the connection con.
        // The new statement is placed in the variable stmt.
        stmt = con.prepareStatement(query);

        stmt.setString(1, namn);
        stmt.setString(2, stad);
        stmt.setInt(3, kapacitet);

        // Execute the SQL statement that is stored in the variable query
        // and store the result in the variable rs.
        stmt.executeUpdate();
        stmt.close();
        con.commit();

    }

    public ResultSet selectAllTournaments() throws Exception{
        // Local variables
        String query;
        ResultSet rs;
        Statement stmt;

        // Set the SQL statement into the query variable
        query = "SELECT Namn,Fran,Till FROM Turnering";

        // Create a statement associated to the connection con.
        // The new statement is placed in the variable stmt.
        stmt = con.createStatement();

        // Execute the SQL statement that is stored in the variable query
        // and store the result in the variable rs.
        rs = stmt.executeQuery(query);

        return rs;
    }

    public void addArenaToTournament() throws Exception{
        // Local variables
        String query;
        ResultSet rs;
        PreparedStatement stmt;
        String turneringsnamn;
        String arenanamn;

        Scanner in = new Scanner(System.in);

        System.out.println("\nVilken turnering ska få en till arena?");
        turneringsnamn = in.nextLine();

        System.out.println("Vilken arena ska läggas till?");
        arenanamn = in.nextLine();

        query = "INSERT INTO turneringsarena (Arena, Turnering)VALUES ((SELECT arenaid FROM arena WHERE namn = ?),(SELECT turneringsid FROM turnering WHERE namn = ?));";

        stmt = con.prepareStatement(query);

        stmt.setString(1, arenanamn);
        stmt.setString(2, turneringsnamn);

        // Execute the SQL statement that is stored in the variable query
        // and store the result in the variable rs.
        stmt.executeUpdate();

        // Close the variable stmt and release all resources bound to it
        // Any ResultSet associated to the Statement will be automatically closed too.
        stmt.close();
    }

    public void showTournamentsForArena() throws Exception{
        // Local variables
        String query;
        ResultSet rs;
        PreparedStatement stmt;
        String arenanamn;

        Scanner in = new Scanner(System.in);

        System.out.println("\nVilken arena vill du se turneringar för?");
        arenanamn = in.nextLine();
        System.out.println();


        query = "SELECT turnering.namn FROM arena, turneringsarena,turnering WHERE arena.namn = ? AND arena.arenaid = turneringsarena.arena AND turneringsarena.turnering = turneringsid";

        stmt = con.prepareStatement(query);

        stmt.setString(1, arenanamn);

        rs = stmt.executeQuery();

        // Loop through the result set and print the results.
        // The method next() returns false when there are no more rows.
        System.out.println(arenanamn + "har följande turneringar.");
        while (rs.next())
        {
            System.out.println("Namn: " + rs.getString("namn"));
        }

        stmt.close();

    }
}
