import javax.swing.SwingUtilities;

/**
 * CodeAlpha Java Chatbot - Main Entry Point
 * @author CodeAlpha Internship
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatBotGUI gui = new ChatBotGUI();
            gui.setVisible(true);
        });
    }
}
