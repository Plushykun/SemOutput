package No_sphinx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NameInput extends JFrame { //The variable NameInput combine with the Jframe functionalities
    private JTextField nameField;
    private JButton okButton;
    private Gui mainGui;

    public NameInput(Gui mainGui) {
        super("Enter Your Name"); // Ask for the name of the user
        this.mainGui = mainGui;
        
        //The sizes of the layout
        setLayout(new BorderLayout());
        
        //the text and button field
        nameField = new JTextField(20);
        okButton = new JButton("OK");
        
        
        add(nameField, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
        
        // Add the functionalities of the system, if the system works it display the time scheme application if not.. Display a notification button that says error
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = nameField.getText().trim();
                if (!userName.isEmpty()) {
                    mainGui.setUserName(userName);
                    mainGui.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(NameInput.this, "Please enter your name.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setSize(300, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
