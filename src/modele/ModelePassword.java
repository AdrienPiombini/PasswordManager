package modele;

import controleur.Password;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ModelePassword {
    private static Db db = new Db();

    public static void insertPassword(Password aPassword){
       String query = "insert into password values (null, '" + aPassword.getName() + "', '"
                + aPassword.getIdentifier() + "', '" + aPassword.getPassword() + "');";
        try {
            db.signIn();
            Statement aStatement = db.getMyConnection().createStatement();
            aStatement.execute(query);
            aStatement.close();
            db.logOut();
        }
        catch (SQLException exp){
            System.out.println("Error : " + query);
        }
    }

    public static ArrayList<Password> selectAllPasswords(){
        String query = "select * from password;";
        ArrayList<Password> allPassword = new ArrayList<>();
        try{
            db.signIn();
            Statement aStatement = db.getMyConnection().createStatement();
            ResultSet result = aStatement.executeQuery(query);
            while(result.next()){
                Password onePassword = new Password(result.getInt("idPassword"),result.getString("name"), result.getString("identifier"), result.getString("password"));
                allPassword.add(onePassword);
            }
            aStatement.close();
            db.logOut();
        }
        catch(SQLException exp){
            System.out.println("Error : " + query);
        }
        return allPassword;
    }

    public static Password selectLastPassword(){
        String query = "select * from password where idPassword = (select max(idPassword) from password);";
        Password password = null;
        try {
            db.signIn();
            Statement aStatement = db.getMyConnection().createStatement();
            ResultSet result = aStatement.executeQuery(query);
            if(result.next()){
                password = new Password(result.getInt("idPassword"),result.getString("name"), result.getString("identifier"), result.getString("password"));

            }
            aStatement.close();
            db.logOut();
        }
        catch (SQLException exp){
            System.out.println("Error : " + query);
        }
        return password;
    }

    public static Password selectWherePassword(int idPassword){
        String query = "select * from password where idPassword = " + idPassword + ";";
        Password password = null;
        try {
            db.signIn();
            Statement aStatement = db.getMyConnection().createStatement();
            ResultSet result = aStatement.executeQuery(query);
            if(result.next()){
                password = new Password(result.getInt("idPassword"),result.getString("name"),
                        result.getString("identifier"), result.getString("password"));
            }
            aStatement.close();
            db.logOut();
        }
        catch (SQLException exp){
            System.out.println("Error : " + query);
        }
        return password;
    }

    public static void updatePassword(Password password){
        String query = "update password set name = '" + password.getName() + "', identifier = '" + password.getIdentifier()
                + "', password = '" + password.getPassword() +"' where idPassword = " + password.getIdPassword() + ";";
        try {
            db.signIn();
            Statement aStatement = db.getMyConnection().createStatement();
            aStatement.execute(query);
            aStatement.close();
            db.logOut();
        }
        catch (SQLException exp){
            System.out.println("Error : " + query);
        }
    }

    public static void deletePassword(int idPassword){
        String query = "delete from password where idPassword =" + idPassword + ";";
        try{
            db.signIn();
            Statement aStatement = db.getMyConnection().createStatement();
            aStatement.execute(query);
            aStatement.close();
            db.logOut();
        }
        catch(SQLException exp){
            System.out.println("Error : " + query);
        }
    }
}
