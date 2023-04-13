import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String s = sc.nextLine(); sc.close();
    String str[] = s.split(" ");
    int n = str.length;
    Tree aTree[] = new Tree[n];
    for (int i = 0; i < n; i++) 
      aTree[i] = new Tree(str[i]);
    Tree.createTree(aTree);

    System.out.print("\nInfix : ");
    Tree.toStringInfix();
    System.out.print("\nPrefix : ");
    Tree.toStringPrefix();
    System.out.print("\nPostfix : ");
    Tree.toStringPostfix();
  }

  static void test1() {
    Scanner sc = new Scanner(System.in);
    String s = sc.nextLine();
    sc.close();

    PolishNotation q1 = new PolishNotation(s);
    q1.printAll();
  }

  static void test2() {
    PolishNotation q1 = new PolishNotation("1 * 2 - 4 + 34 - 6 * 4");
    PolishNotation q2 = new PolishNotation("12 + 3 - 6 * 11 * 3 - 18 / 3 + 14");
    PolishNotation q3 = new PolishNotation("6 * 11 * 3 - 18");
    System.out.println("q1");
    q1.printAll();
    System.out.println("q2");
    q2.printAll();
    System.out.println("q3");
    q3.printAll();
  }

}

class PolishNotation {
  private String str[];
  private Tree aTree[];
  private int n;

  public PolishNotation(String s) {
    str = s.split(" ");
    n = str.length;
    aTree = new Tree[n];

    if (n < 3 || n % 2 == 0) return;

    for (int i = 0; i < n; i++) {
      aTree[i] = new Tree(str[i]);
    }

    Tree.createTree(aTree);

  }

  public void infix() {
    Tree.toStringInfix();
    System.out.println();
    /* return "[" + Tree.toStringInfix() + "]"; */
  }

  public void prefix() {
    Tree.toStringPrefix();
    System.out.println();
    /* return "[" + Tree.toStringPrefix() + "]"; */
  }

  public void postfix() {
    Tree.toStringPostfix();
    System.out.println();
    /* return "[" + Tree.toStringPostfix() + "]"; */
  }

  public void printAll() {
    System.out.print("Infix : ");
    this.infix();
    System.out.print("Prefix : ");
    this.prefix();
    System.out.print("Postfix : ");
    this.postfix();

    /* return this.infix() + "\n" +  this.prefix() + "\n" +  this.postfix() + "\n"; */
  }

  public void ptest() {
    for (int i = 0; i < n; i++) {
      aTree[i].ptest();
    }
  }

}

class Tree {
  private static Tree Root;
  private Tree topLeft;
  private Tree topRight;
  private Tree botLeft;
  private Tree botRight;
  private String value;

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
      if (tmp.equals("+") ||
       tmp.equals("-")) {
        preT = toTop(preT);
        preT.topRight = curT;
        curT.botLeft = preT;
        preT = curT;
      } else if (tmp.equals("*") ||
       tmp.equals("/")) {
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

  private static Tree toTop(Tree t) {
    while (!(t.topLeft == null &&
     t.topRight == null)) {
      if (t.topLeft != null) 
      t = t.topLeft;
      if (t.topRight != null) 
      t = t.topRight;
    }
    return t;
  }

  public static void toStringInfix() {
    infix(Root);
    /* return infix(Root); */
  }

  public static void toStringPrefix() {
    prefix(Root);
    /* return prefix(Root); */
  }

  public static void toStringPostfix() {
    postfix(Root);
    /* return postfix(Root); */
  }

  public static void infix(Tree t) {
    if (t.botLeft == null &&
     t.botRight == null) {
      System.out.print(t);
    } else {
      infix(t.botLeft);
      System.out.print(t);
      infix(t.botRight);
    }
  }

  public static void prefix(Tree t) {
    if (t.botLeft == null &&
     t.botRight == null) {
      System.out.print(t);
    } else {
      System.out.print(t);
      prefix(t.botLeft);
      prefix(t.botRight);
    }
  }

  public static void postfix(Tree t) {
    if (t.botLeft == null &&
     t.botRight == null) {
      System.out.print(t);
    } else {
      postfix(t.botLeft);
      postfix(t.botRight);
      System.out.print(t);
    }
  }

  public String toString() {
    return value + " ";
  }

  public void ptest() {
    System.out.println("topl = " + topLeft);
    System.out.println("topr = " + topRight);
    System.out.println("botl = " + botLeft);
    System.out.println("botr = " + botRight);
    System.out.println("this = " + value);
    System.out.println("----------------");
  }
}
