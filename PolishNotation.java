import java.util.Scanner;

public class PolishNotation {
    static Scanner sc = new Scanner(System.in);
    static String s = sc.nextLine();

    public static void main(String[] args) {
        String tmp[] = s.split(" ");
        int n = tmp.length;
        Tree appleTree[] = new Tree[n]; 

        if (n < 3 || n%2 == 0) return;

        for (int i = 0; i < n; i++) {
            appleTree[i] = new Tree(tmp[i]);
        }

        Tree.createTree(appleTree);

        /* System.out.println();
        for (int i = 0; i < n; i++) {
            appleTree[i].ptest();
        } */

        Tree.toStringInfix();
        Tree.toStringPrefix();
        Tree.toStringPostfix();

    }

}

class Tree {
    private static Tree Root;
    private Tree topLeft;
    private Tree topRight;
    private Tree botLeft;
    private Tree botRight;
    private String value;

    public static Tree getRoot() {
        return Root;
    }

    public Tree getTopLeft() {
        return topLeft;
    }

    public Tree getTopRight() {
        return topRight;
    }

    public Tree getBotLeft() {
        return botLeft;
    }

    public Tree getBotRight() {
        return botRight;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Tree(String v) {
        value = v;
    }

    public static void createTree(Tree t[]) {
        int n = t.length;

        Tree preT = t[1];
        preT.botLeft = t[0];
        t[0].topRight = preT;

        for (int i = 2; i < n; i++) {
            Tree curT = t[i];
            String tmp = curT.value;
            if (tmp.equals("+") || tmp.equals("-")) {
                preT = toTop(preT);
                preT.topRight = curT;
                curT.botLeft = preT;
                preT = curT;
            } else if (tmp.equals("*") || tmp.equals("/")) {
                preT.botRight.topRight = curT;
                preT.botRight.topLeft = null;
                curT.botLeft = preT.botRight;
                curT.topLeft = preT;
                preT.botRight = curT;
                preT = curT;
            } else {
                preT.botRight = curT;
                curT.topLeft = preT;
            }
        }

        Root = toTop(preT);
    }

    public void ptest() {
        System.out.println("topl = " + topLeft);
        System.out.println("topr = " + topRight);
        System.out.println("botl = " + botLeft);
        System.out.println("botr = " + botRight);
        System.out.println("this = " + value);
        System.out.println("----------------");
    }

    private static Tree toTop(Tree t) {
        while(!(t.topLeft == null && t.topRight == null)) {
            if(t.topLeft != null) t = t.topLeft;
            if(t.topRight != null) t = t.topRight;
        }
        return t;
    }

    public String toString() {
        return value + " ";
    }

    public static void toStringInfix() {
        System.out.println("this is infix");
        infix(Root);
        System.out.println();
    }

    public static void toStringPrefix() {
        System.out.println("this is prefix");
        prefix(Root);
        System.out.println();
    }

    public static void toStringPostfix() {
        System.out.println("this is postfix");
        postfix(Root);
        System.out.println();
    }

    public static void infix(Tree t) {
        if(t.botLeft == null && t.botRight == null) {
            System.out.print(t);
        } else {
            infix(t.botLeft);
            System.out.print(t);
            infix(t.botRight);
        }
    }

    public static void prefix(Tree t) {
        if(t.botLeft == null && t.botRight == null) {
            System.out.print(t);
        } else {
            System.out.print(t);
            prefix(t.botLeft);
            prefix(t.botRight);
        }
    }

    public static void postfix(Tree t) {
        if(t.botLeft == null && t.botRight == null) {
            System.out.print(t);
        } else {
            postfix(t.botLeft);
            postfix(t.botRight);
            System.out.print(t);
        }
    }

}
