import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn_GUI extends JFrame {
    private static JLabel password;
    private static JLabel username;
    private static JTextField Username;
    private static JPasswordField Password;
    private static JButton login_button;

    public LogIn_GUI() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        setBounds(300, 200, 450, 300);
        setContentPane(panel);
        username = new JLabel("Username:");
        username.setBounds(100, 8, 70, 20);
        panel.add(username);
        Username = new JTextField();
        Username.setBounds(100, 27, 193, 28);
        panel.add(Username);
        password = new JLabel("Password:");
        password.setBounds(100, 55, 70, 20);
        panel.add(password);
        Password = new JPasswordField();
        Password.setBounds(100, 75, 193, 28);
        panel.add(Password);
        login_button = new JButton("Login");
        login_button.setBounds(100, 110, 90, 25);
        login_button.setForeground(Color.WHITE);
        login_button.setBackground(Color.BLUE);
        panel.add(login_button);
        login_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String my_username = Username.getText();
                String my_password = Password.getText();
                Account acc = Game.getInstance().logIn(my_username, my_password);
                if (acc != null) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect email or password!");
                }
            }
        });
    }
}
