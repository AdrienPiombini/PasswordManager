package controleur;
import modele.ModelePassword;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Password {
    private String name, identifier, password;
    private int idPassword;
    public Password(int idPassword, String name, String identifier, String password){
        this.idPassword = idPassword;
        this.name = name;
        this.identifier = identifier;
        this.password = password;
    }

    public Password(String name, String identifier, String password){
        this.idPassword = 0;
        this.name = name;
        this.identifier = identifier;
        this.password = password;
    }

    public static void insertPassword(Password unPassword){
        ModelePassword.insertPassword(unPassword);
    }

    public static ArrayList<Password> selectAllPasswords(){
        return ModelePassword.selectAllPasswords();
    }

    public static Password selectLastPassword(){
        return ModelePassword.selectLastPassword();
    }

    public static Password selectWherePassword(int idPassword){
     return ModelePassword.selectWherePassword(idPassword);
    }

    public static void updatePassword(Password password){
        ModelePassword.updatePassword(password);
    }

    public int getIdPassword(){
        return idPassword;
    }
    public void setIdPassword(int idPassword){
        this.idPassword = idPassword;
    }
    public String getName() {
        return name;
    }

    public void setNom(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifiant(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
