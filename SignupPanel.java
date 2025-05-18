import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SignupPanel extends JPanel {
    public SignupPanel(LoginSignupFrame parent, HashMap<String, User> users) {
        setLayout(new GridLayout(5, 1));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton signupBtn = new JButton("Create Account");
        JButton switchToLogin = new JButton("Back to Login");

        add(new JLabel("Choose Username:"));
        add(usernameField);
        add(new JLabel("Choose Password:"));
        add(passwordField);
        add(signupBtn);
        add(switchToLogin);

        signupBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (!users.containsKey(user)) {
                users.put(user, new User(user, pass));
                DataHandler.saveUsers(users);
                JOptionPane.showMessageDialog(this, "Account created!");
                parent.switchTo("Login");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists.");
            }
        });

        switchToLogin.addActionListener(e -> parent.switchTo("Login"));
    }
}