import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorPanel extends JPanel {
    private boolean point_jud=true;//小数点合法性判断，true为可以输入小数点
    private boolean sym_jud =false;//运算符号合法性判断
    // 括号合法性判断，右括号数目小于左括号数目-1才能输入
    private int sym_l=0;
    private int sym_r=0;
    public static Font font1 = new Font("宋体", Font.BOLD, 15);
    public String str;
    private JButton lpt=new JButton();
    private JButton rpt=new JButton();

    private JButton answer=new JButton();
    private JButton button_add=new JButton();
    private JButton button_sub=new JButton();
    private JButton Button_mul=new JButton();
    private JButton button_div=new JButton();
    private JButton delete=new JButton();
    private JButton Button1=new JButton();
    private JButton Button2=new JButton();
    private JButton Button3=new JButton();
    private JButton Button4=new JButton();
    private JButton Button5=new JButton();
    private JButton Button6=new JButton();
    private JButton Button7=new JButton();
    private JButton Button8=new JButton();
    private JButton Button9=new JButton();
    private JButton Button0=new JButton();
    private JLabel show=new JLabel();
    public JTextArea output0= new JTextArea();
    public JTextArea input0=new JTextArea();
    private JButton AC=new JButton();
    private JButton point=new JButton();
    private JLabel bg=new JLabel();
    Calculator infix;
    public CalculatorPanel(LayoutManager layout) {
        super(layout);
        infix=new Calculator();


        input0.setBounds(10,10,320,50);
        input0.setText("");
        input0.setLineWrap(true);
        input0.setEditable(false);
        this.add(input0);

        show.setBounds(370,0,100,20);
        show.setText("历史");
        this.add(show);

        output0.setBounds(340,20,100,610);
        output0.setText("");
        output0.setLineWrap(true);
        output0.setEditable(false);
        this.add(output0);
        //按键宽80，高100,400*400按键区域

        AC.setIcon(new ImageIcon("img/AC.png"));
        AC.setBounds(10,70,80,100);
        this.add(AC);

        delete.setIcon(new ImageIcon("img/delete.png"));
        delete.setBounds(90,70,80,100);
        this.add(delete);

        lpt.setIcon(new ImageIcon("img/left.png"));
        lpt.setBounds(170,70,80,100);
        this.add(lpt);

        rpt.setIcon(new ImageIcon("img/right.png"));
        rpt.setBounds(250,70,80,100);
        this.add(rpt);

        Button7.setIcon(new ImageIcon("img/7.png"));
        Button7.setBounds(10,170,80,100);
        this.add(Button7);

        Button8.setIcon(new ImageIcon("img/8.png"));
        Button8.setBounds(90,170,80,100);
        this.add(Button8);

        Button9.setIcon(new ImageIcon("img/9.png"));
        Button9.setBounds(170,170,80,100);
        this.add(Button9);

        button_div.setIcon(new ImageIcon("img/div.png"));
        button_div.setBounds(250,170,80,100);
        this.add(button_div);

        Button4.setIcon(new ImageIcon("img/4.png"));
        Button4.setBounds(10,270,80,100);
        this.add(Button4);

        Button5.setIcon(new ImageIcon("img/5.png"));
        Button5.setBounds(90,270,80,100);
        this.add(Button5);

        Button6.setIcon(new ImageIcon("img/6.png"));
        Button6.setBounds(170,270,80,100);
        this.add(Button6);

        Button_mul.setIcon(new ImageIcon("img/mul.png"));
        Button_mul.setBounds(250,270,80,100);
        this.add(Button_mul);

        Button1.setIcon(new ImageIcon("img/1.png"));
        Button1.setBounds(10,370,80,100);
        this.add(Button1);

        Button2.setIcon(new ImageIcon("img/2.png"));
        Button2.setBounds(90,370,80,100);
        this.add(Button2);

        Button3.setIcon(new ImageIcon("img/3.png"));
        Button3.setBounds(170,370,80,100);
        this.add(Button3);

        button_add.setIcon(new ImageIcon("img/add.png"));
        button_add.setBounds(250,370,80,100);
        this.add(button_add);

        Button0.setIcon(new ImageIcon("img/0.png"));
        Button0.setBounds(10,470,80,100);
        this.add(Button0);

        point.setIcon(new ImageIcon("img/point.png"));
        point.setBounds(90,470,80,100);
        this.add(point);

        answer.setIcon(new ImageIcon("img/ans.png"));
        answer.setBounds(170,470,80,100);
        this.add(answer);

        button_sub.setIcon(new ImageIcon("img/sub.png"));
        button_sub.setBounds(250,470,80,100);
        this.add(button_sub);

        System.out.println(str);
        lpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sym_l += 1;
                input0.setText(input0.getText()+"(");
            }
        });
        rpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sym_r<=sym_l-1){
                    input0.setText(input0.getText()+")");
                    sym_r+=1;
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char add='+',sub='-',div='/',mul='*';

                String str = input0.getText();
                StringBuilder str2 = new StringBuilder();
                for (int i = 0; i < (str.length() - 1); i++) {
                    str2.append(str.charAt(i));
                }
                if (str2.toString().equals("")) {
                    input0.setText("");
                    sym_jud=false;
                    sym_l=0;
                    sym_r=0;
                } else {
                    input0.setText(str2.toString());
                    if(str.charAt(str.length()-1)==add||str.charAt(str.length()-1)==sub||str.charAt(str.length()-1)==div||str.charAt(str.length()-1)==mul)sym_jud=true;
                    else sym_jud=false;
                }

            }
        });
        Button0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input0.setText(input0.getText()+"0");
                sym_jud =true;
            }
        });
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input0.setText(input0.getText()+"1");
                sym_jud =true;

            }
        });

        Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input0.setText(input0.getText()+"2");
                sym_jud =true;
            }
        });
        Button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input0.setText(input0.getText()+"3");
                sym_jud =true;
            }
        });
        Button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input0.setText(input0.getText()+"4");
                sym_jud =true;
            }
        });
        Button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input0.setText(input0.getText()+"5");
                sym_jud =true;
            }
        });
        Button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input0.setText(input0.getText()+"6");
                sym_jud =true;
            }
        });
        Button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input0.setText(input0.getText()+"7");
                sym_jud =true;
            }
        });
        Button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input0.setText(input0.getText()+"8");
                sym_jud =true;
            }
        });
        Button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input0.setText(input0.getText()+"9");
                sym_jud =true;
            }
        });

        button_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sym_jud)input0.setText(input0.getText()+"+");
                sym_jud =false;
                point_jud=true;
            }
        });
        button_sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sym_jud)
                    input0.setText(input0.getText()+"-");
                sym_jud =false;
                point_jud=true;
            }
        });
        Button_mul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sym_jud) input0.setText(input0.getText()+"*");
                sym_jud =false;
                point_jud=true;
            }
        });
        button_div.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sym_jud) input0.setText(input0.getText()+"/");
                sym_jud =false;
                point_jud=true;
            }
        });
        answer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sym_l=0;
                sym_r=0;
                point_jud=true;
                sym_jud =false;
                str=input0.getText();
                output0.setText(output0.getText()+input0.getText()+"="+infix.test(str)+'\n');
                input0.setText("");
            }
        });

        AC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sym_l=0;
                sym_r=0;
                point_jud=true;
                sym_jud =false;
                input0.setText("");output0.setText("");
            }
        });
        point.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(point_jud){input0.setText(input0.getText()+".");
                    point_jud=false;
                }
            }
        });

    }
}
