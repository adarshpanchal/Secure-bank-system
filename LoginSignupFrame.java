import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class LoginSignupFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private HashMap<String, User> users;

    public LoginSignupFrame() {
        users = DataHandler.loadUsers();
        setTitle("Secure Bank - Login / Sign Up");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(new LoginPanel(this, users), "Login");
        cardPanel.add(new SignupPanel(this, users), "Signup");

        add(cardPanel);
        setVisible(true);
    }

    public void switchTo(String name) {
        cardLayout.show(cardPanel, name);
    }
}