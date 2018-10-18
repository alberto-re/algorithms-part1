import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;

    public KdTree() {
        size = 0;
    }

    public static void main(String[] args) {
        // comment needed by the grader
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));
        System.out.println(tree.nearest(new Point2D(0.046, 0.301)));
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            root = new Node(p);
            size = 1;
        } else {
            if (!contains(p)) {
                append(root, p, 0);
                ++size;
            }
        }
    }

    private void append(Node node, Point2D p, int level) {
        if (level % 2 == 1) { // split by y axis
            if (p.y() > node.p.y()) {
                if (node.right == null) {
                    node.right = new Node(p);
                } else {
                    append(node.right, p, ++level);
                }
            } else {
                if (node.left == null) {
                    node.left = new Node(p);
                } else {
                    append(node.left, p, ++level);
                }
            }
        } else { // split by x axis
            if (p.x() > node.p.x()) {
                if (node.right == null) {
                    node.right = new Node(p);
                } else {
                    append(node.right, p, ++level);
                }
            } else {
                if (node.left == null) {
                    node.left = new Node(p);
                } else {
                    append(node.left, p, ++level);
                }
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return false;
        }
        return recurContains(root, p, 0, 0) > 0;
    }

    private int recurContains(Node node, Point2D p, int level, int n) {
        if (node == null) {
            return n;
        }
        if (p.equals(node.p)) {
            return ++n;
        } else {
            if (level % 2 == 1) {
                if (p.y() > node.p.y()) {
                    return recurContains(node.right, p, level + 1, n);
                } else {
                    return recurContains(node.left, p, level + 1, n);
                }
            } else {
                if (p.x() > node.p.x()) {
                    return recurContains(node.right, p, level + 1, n);
                } else {
                    return recurContains(node.left, p, level + 1, n);
                }
            }
        }
    }

    public void draw() {
        recurDraw(root);
    }

    private void recurDraw(Node node) {
        if (node == null) {
            return;
        }
        node.p.draw();
        recurDraw(node.left);
        recurDraw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> pointsInside = new ArrayList<>();
        pointsInside.addAll(recurRange(root, rect, 0));
        return pointsInside;
    }

    private List<Point2D> recurRange(Node node, RectHV rect, int level) {
        List<Point2D> pointsInside = new ArrayList<>();
        if (node == null) {
            return pointsInside;
        }
        if (rect.contains(node.p)) {
            pointsInside.add(node.p);
        }


        if (level % 2 == 1) {
            if (rect.ymin() <= node.p.y()) {
                pointsInside.addAll(recurRange(node.left, rect, level + 1));
            }
            if (rect.ymax() > node.p.y()) {
                pointsInside.addAll(recurRange(node.right, rect, level + 1));
            }
        } else {
            if (rect.xmin() <= node.p.x()) {
                pointsInside.addAll(recurRange(node.left, rect, level + 1));
            }
            if (rect.xmax() > node.p.x()) {
                pointsInside.addAll(recurRange(node.right, rect, level + 1));
            }
        }
        return pointsInside;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Point2D nearest = recurNearest(root, p, null);
        return nearest;
    }

    private Point2D recurNearest(Node node, Point2D p, Point2D pNearest) {
        if (node == null) {
            return pNearest;
        }
        if (pNearest == null) {
            pNearest = node.p;
        }

        Point2D pNearestLeft = recurNearest(node.left, p, pNearest);
        if (node.p.distanceSquaredTo(p) < pNearestLeft.distanceSquaredTo(p)) {
            pNearestLeft = node.p;
        }
        Point2D pNearestRight = recurNearest(node.right, p, pNearest);
        if (node.p.distanceSquaredTo(p) < pNearestRight.distanceSquaredTo(p)) {
            pNearestRight = node.p;
        }

        if (pNearestLeft.distanceSquaredTo(p) < pNearest.distanceSquaredTo(p)) {
            pNearest = pNearestLeft;
        }
        if (pNearestRight.distanceSquaredTo(p) < pNearest.distanceSquaredTo(p)) {
            pNearest = pNearestRight;
        }
        return pNearest;
    }

    private class Node {

        Point2D p;
        Node left;
        Node right;

        Node(Point2D p) {
            this.p = p;
        }
    }
}