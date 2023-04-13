import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    test2();

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
    q1.printAll();
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

  public String infix() {
    return "[" + Tree.toStringInfix() + "]";
  }

  public String prefix() {
    return "[" + Tree.toStringPrefix() + "]";
  }

  public String postfix() {
    return "[" + Tree.toStringPostfix() + "]";
  }

  public String printAll() {
    return this.infix() + "\n" +  this.prefix() + "\n" +  this.postfix() + "\n";
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
  
  private static Tree toTop(Tree t) {
      while (!(t.topLeft == null && t.topRight == null)) {
          if (t.topLeft != null) t = t.topLeft;
          if (t.topRight != null) t = t.topRight;
        }
        return t;
    }

  public static String toStringInfix() {
    return infix(Root);
  }

  public static String toStringPrefix() {
    return prefix(Root);
  }

  public static String toStringPostfix() {
    return postfix(Root);
  }

  public static String infix(Tree t) {
    String s = "";
    if (t.botLeft == null && t.botRight == null) {
      s += t;
    } else {
      s += infix(t.botLeft);
      s += t;
      s += infix(t.botRight);
    }
    return s;
  }

  public static String prefix(Tree t) {
    String s = "";
    if (t.botLeft == null && t.botRight == null) {
      s += t;
    } else {
      s += t;
      s += prefix(t.botLeft);
      s += prefix(t.botRight);
    }
    return s;
  }

  public static String postfix(Tree t) {
    String s = "";
    if (t.botLeft == null && t.botRight == null) {
      s += t;
    } else {
      s += postfix(t.botLeft);
      s += postfix(t.botRight);
      s += t;
    }
    return s;
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
