public class Block {
        static final int InTREE = 1;
        static final int OutTree = 0;
        //迷宫块是否访问过
        public boolean visited = false;
        // 迷宫块的位置
        private int x = -1;
        private int y = -1;
        // 标识迷宫块是否已加入树中
        private int flag = OutTree;
        // 迷宫块的父节点
        private Block father = null;
        public Block(int x0, int y0) {
            x = x0;
            y = y0;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public int getFlag() {
            return flag;
        }
        public Block getFather() {
            return father;
        }
        public void setFather(Block f) {
            father = f;
        }
        public void setFlag(int f) {
            flag = f;
        }
        public String toString() {
            return new String("(" + x + "," + y + ")\n");
        }
}
