public class Main {
    public static void main(String[] args) {
        DataHandler handler = new DataHandler();
        handler.loadFromFile();

        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginSignupFrame frame = new LoginSignupFrame(handler);
            frame.setVisible(true);
        });
    }
}
