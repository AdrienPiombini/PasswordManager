package vue;
import controleur.Password;
import controleur.Table;
import modele.ModelePassword;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class InterfacePrincipal extends JFrame implements ActionListener {

    private static Password password = null;
    // JTable interface listing password
    private JTable tableIdInterface;
    private Table aTable;
    private JButton btAddNew = new JButton("Add a new Password");
    private JButton btCancel = new JButton("Cancel");
    private JButton btDelete = new JButton("Delete");

    // Panel interface for insert password in our list
    private JPanel panelFormAddPassword = new JPanel();
    private JTextField txtName = new JTextField();
    private JTextField txtIdentifier = new JTextField();
    private JTextField txtPassword = new JTextField();
    private JButton btAddPassword = new JButton("Add");
    private JButton btGeneratePassword = new JButton("Generate");
    // Panel interface for see the password
    public InterfacePrincipal(){
        this.setTitle("Password Generator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(200,100,1000,1000);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(224, 224, 224));
        this.setLayout(null);


        // Construction de la JTable
        String headers[] = {"ID", "Name"};
        Object[][] data = this.getDonnees();
        this.aTable = new Table(data, headers);
        this.tableIdInterface = new JTable(this.aTable);
        JScrollPane aScrollBar = new JScrollPane(this.tableIdInterface);
        aScrollBar.setBounds(50,50,300,500);

        //Construction du panelform pour ajouter/modifier un MDP
        this.panelFormAddPassword.setBounds(500, 50,400,500 );
        this.panelFormAddPassword.setLayout(new GridLayout(4,2));
        this.panelFormAddPassword.add(new JLabel("Name : "));
        this.panelFormAddPassword.add(this.txtName);
        this.panelFormAddPassword.add(new JLabel("Identifier : "));
        this.panelFormAddPassword.add(this.txtIdentifier);
        this.panelFormAddPassword.add(new JLabel("Password : "));
        this.panelFormAddPassword.add(this.txtPassword);
        this.panelFormAddPassword.add(new JLabel("Generate random pass :"));
        this.panelFormAddPassword.add(btGeneratePassword);

        this.add(aScrollBar);
        btAddNew.setBounds(30, 750, 200, 40);
        btCancel.setBounds(280, 750, 200, 40);
        btDelete.setBounds(780, 750, 200, 40);
        this.btDelete.setVisible(false);
        this.add(btCancel);
        this.add(btAddNew);
        this.add(btDelete);

        //Panel insert Password
        btAddPassword.setBounds(530,750,200,40);
        this.add(btAddPassword);
        this.btAddPassword.setVisible(false);
        this.add(panelFormAddPassword);
        this.panelFormAddPassword.setVisible(false);

        this.setVisible(true);
        this.btAddNew.addActionListener(this);
        this.btCancel.addActionListener(this);
        this.btAddPassword.addActionListener(this);
        this.btGeneratePassword.addActionListener(this);
        this.btDelete.addActionListener(this);

        this.tableIdInterface.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int lineNumber = tableIdInterface.getSelectedRow();
                int idPassword = Integer.parseInt(tableIdInterface.getValueAt(lineNumber, 0).toString());
                if(e.getClickCount()>=1){
                    panelFormAddPassword.setVisible(true);
                    btAddPassword.setText("Update");
                    btAddPassword.setVisible(true);
                    btDelete.setVisible(true);
                    password = Password.selectWherePassword(idPassword);
                    txtIdentifier.setText(password.getIdentifier());
                    txtName.setText(password.getName());
                    txtPassword.setText(password.getPassword());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btAddNew){
            this.panelFormAddPassword.setVisible(true);
            this.btAddPassword.setText("Add");
            this.emptyField();
            this.btAddPassword.setVisible(true);
        }
        else if(e.getSource()==this.btCancel){
            this.panelFormAddPassword.setVisible(false);
            this.btAddPassword.setVisible(false);
            this.btDelete.setVisible(false);
            this.emptyField();
        }
        else if(e.getSource()==this.btAddPassword && this.btAddPassword.getText().equals("Add")){
            if(txtName.getText().isEmpty() || txtIdentifier.getText().isEmpty() || txtPassword.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
            }
            else{
                Password password = new Password(txtName.getText(),txtIdentifier.getText(),txtPassword.getText());
                ModelePassword.insertPassword(password);
                JOptionPane.showMessageDialog(null, "New password insert in your manager");
                this.emptyField();
                password = ModelePassword.selectLastPassword();
                Object line[] = {password.getIdPassword(),password.getName()};
                this.aTable.insertLigne(line);
            }
        }
        else if(e.getSource()==this.btAddPassword && this.btAddPassword.getText().equals("Update")){
            if(txtName.getText().isEmpty() || txtIdentifier.getText().isEmpty() || txtPassword.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
            }
            else{
                password = new Password(password.getIdPassword(),txtName.getText(),txtIdentifier.getText(),txtPassword.getText());
                ModelePassword.updatePassword(password);
                JOptionPane.showMessageDialog(null, "Password has been update in your manager");
                this.emptyField();
                password = ModelePassword.selectWherePassword(password.getIdPassword());
                Object line[] = {password.getIdPassword(),password.getName()};
                int lineNumber = this.tableIdInterface.getSelectedRow();
                this.aTable.updateLigne(lineNumber,line);
            }
        }
        else if(e.getSource()==this.btDelete){
            int response = JOptionPane.showConfirmDialog(null, "are you sure you want too delete this password ?");
            if (response==0){
                int lineNumber = tableIdInterface.getSelectedRow();
                ModelePassword.deletePassword(password.getIdPassword());
                this.aTable.deleteLigne(lineNumber);
                this.panelFormAddPassword.setVisible(false);
                JOptionPane.showMessageDialog(null, "Remove successful");
            }
        }
        else if(e.getSource()==this.btGeneratePassword){
            this.txtPassword.setText(this.generatePassword());
        }
    }

    public Object[][] getDonnees(){
        ArrayList<Password> allPasswords = Password.selectAllPasswords();
        Object[][] matrice = new Object[allPasswords.size()][2];
        int i = 0;
        for (Password onePassword : allPasswords){
            matrice[i][0] = onePassword.getIdPassword();
            matrice[i][1] = onePassword.getName();
            i++;
        }
        return matrice;
    }

    public void emptyField(){
        this.txtIdentifier.setText("");
        this.txtName.setText("");
        this.txtPassword.setText("");
    }

    public String generatePassword(){
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789&@!*$ù%:;,?=+-_(){}/£€#";
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
