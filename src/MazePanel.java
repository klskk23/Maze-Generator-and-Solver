import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Stack;

public class MazePanel extends JPanel {
    public static Font font1 = new Font("宋体", Font.BOLD, 20);//定义字体1
    private boolean repeat=true;
    private int blocks, width, offSet; // blocks：迷宫大小；width：每个格子的宽度和高度
    private Block[][] maze;
    private int startX, startY; // 球的位置，在第几行第几列格子上
    private boolean drawPath = false; //是否画出路径
    public static void main(String[] args) {
        final int n = 30, width = 800, offset = 50, locationX = 200, locationY = 100;//n迷宫格数目width迷宫宽度offset是迷宫的边框
        JPanel p = new MazePanel(n, (width - 2*offset) / n, offset-7);//按比例设置迷宫窗口大小
        JFrame frame = new JFrame("Maze Generator and Solver");
        frame.getContentPane().add(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width , width + 2*offset);
        frame.setLocation(locationX, locationY);
        frame.setVisible(true);
    }
    MazePanel(int blocks, int width, int offset) {
        this.setLayout(null);
        this.blocks = blocks;
        this.width = width;
        this.offSet = offset;
        maze = new Block[blocks][blocks];
        for (int i = 0; i <= blocks - 1; i++)
            for (int j = 0; j <= blocks - 1; j++)
                maze[i][j] = new Block(i, j);
        createMaze();
        setKeyListener();
        this.setFocusable(true);
         //开始按钮,重置游戏
        JButton game_start = new JButton();
        game_start.setText("重新生成迷宫");
        game_start.setForeground(Color.black);
        game_start.setFont(font1);
        game_start.setBounds(0, 780, 390, 82);
        game_start.setBackground(Color.white);
        game_start.setFocusable(false);
        this.add(game_start);
        game_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
            }
        });
        //路径显示
        JButton findPath = new JButton();
        findPath.setText("显示/关闭路径");
        findPath.setForeground(Color.black);
        findPath.setFont(font1);
        findPath.setBounds(390, 780, 395, 82);
        findPath.setBackground(Color.white);
        findPath.setFocusable(false);
        this.add(findPath);

        findPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(repeat){drawPath=true;repaint();repeat=false;}else{drawPath=false;repaint();repeat=true;}

            }
        });
    }


    // 初始化游戏
    private void init() {
        //迷宫块需要奇数个,初始化迷宫块
        for (int i = 0; i <= blocks - 1; i++)
            for (int j = 0; j <= blocks - 1; j++) {
                maze[i][j].setFather(null);
                maze[i][j].setFlag(Block.OutTree);
                maze[i][j].visited=false;
            }
        startX = 0;
        startY = 0;
        drawPath = false;
        createMaze();
        // setKeyListener();
        this.setFocusable(true);
        repaint();
    }

    // 得到格子中心点的像素X,得到格子中心点的像素Y
    public int getXc(int x) {
        return offSet + x * width + width / 2;
    }
    public int getYc(int y) {
        return offSet + y * width + width / 2;
    }
    public int getXc(Block p) {
        return offSet + p.getY() * width + width / 2;
    }
    public int getYc(Block p) {
        return offSet + p.getX() * width + width / 2;
    }
    // 出界判断
    private boolean isOutOfBorder(Block p) {
        return isOutOfBorder(p.getX(), p.getY());
    }
    private boolean isOutOfBorder(int x, int y) {
        return (x > blocks - 1 || y > blocks - 1 || x < 0 || y < 0) ? true : false;
    }
    // 检查是否到达终点
    private void MazeOver() {
        if (startX == blocks - 1 && startY == blocks - 1) {
            JOptionPane.showMessageDialog(null, "你成功了 !", "",
                    JOptionPane.PLAIN_MESSAGE);
            init();
        }
    }
    // 移动小球
    synchronized private void move(int c) {
        int tx = startX, ty = startY;
        // System.out.println(c);
        switch (c) {
            case KeyEvent.VK_LEFT :
                ty--;
                break;
            case KeyEvent.VK_RIGHT :
                ty++;
                break;
            case KeyEvent.VK_UP :
                tx--;
                break;
            case KeyEvent.VK_DOWN :
                tx++;
                break;
            default :
        }
        // 若移动后未出界且格子之间有路径，则进行移动，更新小球位置，否则不进行移动
        if (!isOutOfBorder(tx, ty)
                && (maze[tx][ty].getFather() == maze[startX][startY]
                || maze[startX][startY].getFather() == maze[tx][ty])) {
            startX = tx;
            startY = ty;
        }
    }
    private void setKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int c = e.getKeyCode();
                move(c);
                repaint();
                MazeOver();
            }
        });
    }

    // 获取邻居单元格
    private Block[] getNeighbors(Block p) {
        final int[] adds = {-1, 0, 1, 0, -1};
        if (isOutOfBorder(p)) {
            return null;
        }
        Block[] Neighbors = new Block[4]; // 四个邻居格子，顺序为上右下左，出界的邻居为null
        int xt;
        int yt;
        for (int i = 0; i <= 3; i++) {
            xt = p.getX() + adds[i];
            yt = p.getY() + adds[i + 1];
            if (isOutOfBorder(xt, yt))
                continue;
            Neighbors[i] = maze[xt][yt];
        }
        return Neighbors;
    }
    // 构建随机树，创建迷宫
    private void createMaze() {
        // 选择起始点

        int rx=0;
        int ry=0;
        // 深度优先遍历
        Stack<Block> stack = new Stack<Block>();
        Block block = maze[rx][ry];
        Block []neighbors = null;
        stack.push(block);
        while (!stack.isEmpty()) {//直到栈空，遍历完成
            // 将初始block出栈，加入树中
            block = stack.pop();
            block.setFlag(Block.InTREE);
            //得到所有邻居节点并且进行判断，如果节点已经在树中或者为空就排除
            Random random = new Random();
            neighbors = getNeighbors(block);
            int ran = Math.abs(random.nextInt());
            ran%=4;//余数是1,2,3或者4
            for (int i = 0; i <= 3; i++) {
                ran++;
                System.out.println(ran);
                ran %= 4;
                if (neighbors[ran] == null || neighbors[ran].getFlag() == Block.InTREE)
                    continue;
                stack.push(neighbors[ran]);//随机选择一个邻居节点入栈
                neighbors[ran].setFather(block);//设置父节点，用来打通墙壁
            }
        }
    }

    // 画迷宫

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 画墙体
        for (int i = 0; i <= blocks; i++) {
            g.drawLine(offSet + i * width, offSet, offSet + i * width,
                    offSet + blocks * width);
        }
        for (int j = 0; j <= blocks; j++) {
            g.drawLine(offSet, offSet + j * width, offSet + blocks * width,
                    offSet + j * width);
        }
        // 打破墙体,使用背景色重绘
        g.setColor(this.getBackground());
        for (int i = blocks - 1; i >= 0; i--) {
            for (int j = blocks - 1; j >= 0; j--) {
                Block f = maze[i][j].getFather();
                if (f != null) {
                    int fx = f.getX(), fy = f.getY();
                    clearFence(i, j, fx, fy, g);
                }
            }
        }

        // 画左上角的入口
        g.drawLine(offSet, offSet + 1, offSet, offSet + width - 1);
        int last = offSet + blocks * width;
        // 画右下角出口
        g.drawLine(last, last - 1, last, last - width + 1);
        // 画轨迹
        g.setColor(Color.lightGray);
        for(int i = 0; i < blocks; i++)
        {
            for(int j = 0; j< blocks; j++)
            {
                if(maze[i][j].visited==true)
                {
                    g.fillOval(getXc(j)-width/3, getYc(i)-width/3,width/2,width/2);
                }
            }
        }
        // 画小球
        g.setColor(Color.RED);
        maze[startX][startY].visited=true;
        g.fillOval(getXc(startY) - width / 3, getYc(startX) - width / 3,
                width / 2, width / 2);
        if (drawPath == true)
            drawPath(g);
    }
    // 打通墙壁
    private void clearFence(int i, int j, int fx, int fy, Graphics g) {
        int sx = offSet + ((j > fy ? j : fy) * width),
                sy = offSet + ((i > fx ? i : fx) * width),
                dx = (i == fx ? sx : sx + width),
                dy = (i == fx ? sy + width : sy);
        if (sx != dx) {
            sx++;
            dx--;
        } else {
            sy++;
            dy--;
        }
        g.drawLine(sx, sy, dx, dy);
    }

    private void drawPath(Graphics g) {
        Color PATH_COLOR = Color.BLUE, BOTH_PATH_COLOR = Color.PINK;
        if (drawPath == true)
            g.setColor(PATH_COLOR);
        else
            g.setColor(this.getBackground());
        Block p = maze[blocks - 1][blocks - 1];
        while (p.getFather() != null) {
            p.setFlag(2);
            p = p.getFather();
        }
        p = maze[0][0];
        while (p.getFather() != null) {
            if (p.getFlag() == 2) {
                p.setFlag(3);
                g.setColor(this.getBackground());
            }
            g.drawLine(getXc(p), getYc(p), getXc(p.getFather()),
                    getYc(p.getFather()));
            p = p.getFather();
        }

        g.setColor(PATH_COLOR);
        p = maze[blocks - 1][blocks - 1];
        while (p.getFather() != null) {
            if (p.getFlag() == 3)
                break;
            g.drawLine(getXc(p), getYc(p), getXc(p.getFather()),
                    getYc(p.getFather()));
            p = p.getFather();
        }
    }
}