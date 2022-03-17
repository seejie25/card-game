import javax.swing.*;
import java.awt.*;

public class SwingMain extends JFrame {
    public static void main(String[] args) {
        SwingGame newGame = new SwingGame();
        newGame.homePage();
    }

    JButton button = new JButton();
    JLabel title = new JLabel();
    JLabel text = new JLabel();
    JTextField textField = new JTextField();

    public SwingMain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Card Game");
        setSize(1280, 800); 
    }

    // button styling
    public static void customButton(JButton button) {
        button.getFont();
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15)); // set font style
        button.setFocusPainted(false);
    }

    // title styling
    public static void customTitle(JLabel title) {
        title.getFont();
        title.setFont(new Font(Font.SERIF, Font.BOLD, 30)); // set font style
    }

    // text styling
    public static void customText(JLabel text) {
        text.getFont();
        text.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
    }

    // textField styling
    public static void customTextField(JTextField textField) {
        textField.getFont();
        textField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
    }

    // text styling
    public static void customEndText(JLabel text) {
        text.getFont();
        text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
    }
}