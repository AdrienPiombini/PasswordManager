package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private String server, db, user, password;
    private Connection myConnection;

    public Db(){
        this.myConnection = null;
        this.server = "Localhost:3306";
        this.db = "passwordManager";
        this.user = "adrien";
        this.password = "adrien";
    }

    public void loadingDriver() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException exp){
            System.out.println("No driver JDBC found");
        }
    }

    public void signIn(){
        String url = "jdbc:mysql://" + this.server + "/" + this.db + "?verifyServerCertificate=false&useSSL=false";
        try {
            this.myConnection = DriverManager.getConnection(url, this.user, this.password);
        } catch (SQLException exp) {
            System.out.println("Unable to connect to : " + url);
        }
    }

    public void logOut() {
        try {
            if (this.myConnection != null) {
                this.myConnection.close();
            }
        } catch (SQLException exp) {
            System.out.println("Unable to close connection");
        }
    }

    public Connection getMyConnection() {
        return myConnection;
    }

    public void setMyConnection(Connection myConnection) {
        this.myConnection = myConnection;
    }
}
