import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    CalculatorPanel calculatorPanel=new CalculatorPanel(null);
    public MainWindow() {
        Container container = getContentPane();
        container.add(calculatorPanel);
        this.setTitle("计算器 BY 废旧螺栓机甲");
        this.setSize(470, 620);
        this.setLocation(100, 100);
        this.setResizable(false);
        this.setVisible(true);
        this.setFocusable(true);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        MainWindow mainWindow=new MainWindow();
    }
}
