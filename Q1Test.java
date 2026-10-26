import java.util.*;
import java.util.stream.Collectors;

public class Q1Test {

     public static List<Integer> stringToArr(String s) {
          ArrayList<String> list = new ArrayList<>(Arrays.asList(s.split("-")));
          return list.stream().map(Integer::parseInt).collect(Collectors.toList());
     }

     public static class BinarySearchTree {

          private class Node {
               int value;
               Node left;
               Node right;

               Node(int value) {
                    this.value = value;
               }
          }

          private Node root;

          // ---------- Insert ----------

          public void insert(int value) {
               root = insertRec(root, value);
          }

          private Node insertRec(Node node, int value) {
               if (node == null) {
                    return new Node(value);
               }
               if (value < node.value) {
                    node.left = insertRec(node.left, value);
               } else if (value > node.value) {
                    node.right = insertRec(node.right, value);
               }
               // Duplicates are ignored
               return node;
          }

          // ---------- Search ----------

          public boolean contains(int value) {
               return containsRec(root, value);
          }

          private boolean containsRec(Node node, int value) {
               if (node == null) {
                    return false;
               }
               if (value == node.value) {
                    return true;
               }
               return value < node.value
                         ? containsRec(node.left, value)
                         : containsRec(node.right, value);
          }

          // ---------- Traversals ----------

          public List<Integer> inorder() {
               List<Integer> result = new ArrayList<>();
               inorderRec(root, result);
               return result;
          }

          private void inorderRec(Node node, List<Integer> result) {
               if (node != null) {
                    inorderRec(node.left, result);
                    result.add(node.value);
                    inorderRec(node.right, result);
               }
          }

          public List<Integer> preorder() {
               List<Integer> result = new ArrayList<>();
               preorderRec(root, result);
               return result;
          }

          private void preorderRec(Node node, List<Integer> result) {
               if (node != null) {
                    result.add(node.value);
                    preorderRec(node.left, result);
                    preorderRec(node.right, result);
               }
          }

          public List<Integer> postorder() {
               List<Integer> result = new ArrayList<>();
               postorderRec(root, result);
               return result;
          }

          private void postorderRec(Node node, List<Integer> result) {
               if (node != null) {
                    postorderRec(node.left, result);
                    postorderRec(node.right, result);
                    result.add(node.value);
               }
          }
     }

     public static String verify(String t1, String t2, String t3) {
          List<List<Integer>> lists = new ArrayList<>();
          lists.add(stringToArr(t1));
          lists.add(stringToArr(t2));
          lists.add(stringToArr(t3));

          // same elements check: every sorted copy must match
          List<Integer> sorted = new ArrayList<>(lists.get(0));
          Collections.sort(sorted);
          for (List<Integer> l : lists) {
               List<Integer> copy = new ArrayList<>(l);
               Collections.sort(copy);
               if (!copy.equals(sorted)) {
                    return "Invalid traversals";
               }
          }

          // i = inorder index, j = preorder index, k = postorder index
          for (int i = 0; i < 3; i++) {
               if (!lists.get(i).equals(sorted)) {
                    continue;
               }
               for (int j = 0; j < 3; j++) {
                    if (j == i) {
                         continue;
                    }
                    int k = 3 - i - j;

                    BinarySearchTree bst = new BinarySearchTree();
                    for (int x : lists.get(j)) {
                         bst.insert(x);
                    }

                    if (bst.preorder().equals(lists.get(j))
                              && bst.postorder().equals(lists.get(k))) {
                         String[] labels = new String[3];
                         labels[i] = "Inorder";
                         labels[j] = "Preorder";
                         labels[k] = "Postorder";

                         StringBuilder sb = new StringBuilder();
                         for (int n = 0; n < 3; n++) {
                              if (n > 0) {
                                   sb.append(", ");
                              }
                              sb.append("Traversal ").append(n + 1)
                                        .append(" - ").append(labels[n]);
                         }
                         return sb.toString();
                    }
               }
          }
          return "Invalid traversals";
     }

     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     }

     // write your codes here
     // public static String verify(String traversal1, String traversal2, String
     // traversal3){
     // return "";
     // }
}