import java.util.ArrayList;
import java.util.Collection;

interface FindCycle {
    public boolean hasCycle(GraphNode rootNode);
}

class MyFindCycle implements FindCycle {
    public boolean hasCycle(GraphNode node) {
        return false;
    }
}

final class GraphNode {
    final Integer id;
    final Collection<GraphNode> children = new ArrayList<GraphNode>();

    public GraphNode(int id) {
        this(id, null);
    }

    public GraphNode(int id, GraphNode parent) {
        this.id = id;
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public void addChild(GraphNode graphNode) {
        children.add(graphNode);
    }

    public Collection<GraphNode> getChildren() {
        return new ArrayList<GraphNode>(children);
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphNode graphNode = (GraphNode) o;

        if (id != null ? !id.equals(graphNode.id) : graphNode.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

public class GraphNodeTest {

    /**
     * Standard tree
     *      |--->6
     * 1--->2--->5
     * |--->3
     * |--->4
     */
    private static GraphNode createSampleTree() {
        GraphNode one = new GraphNode(1);
        GraphNode two = new GraphNode(2, one);
        new GraphNode(5, two);
        new GraphNode(6, two);
        new GraphNode(3, one);
        new GraphNode(4, one);
        return one;
    }

    /**
     * 7 points back at root, cycle
     * |-------------|
     * v    |--->5-->7
     * 1--->2--->6
     * |--->3
     * |--->4
     */
    private static GraphNode createSampleGraphWithLeftCycle() {
        GraphNode one = new GraphNode(1);
        GraphNode two = new GraphNode(2, one);
        GraphNode seven = new GraphNode(7, new GraphNode(5, two));
        new GraphNode(6, two);
        new GraphNode(3, one);
        new GraphNode(4, one);
        seven.addChild(one);
        return one;
    }

    /**
     * 7 and 1 point at 3, not a cycle
     * |--->6
     * 1--->2--->5-->7
     * |--->3<-------|
     * |--->4
     */
    private static GraphNode createSampleGraphWithAlmostCycleThatHasNoChildren() {
        GraphNode one = new GraphNode(1);
        GraphNode two = new GraphNode(2, one);
        GraphNode seven = new GraphNode(7, new GraphNode(5, two));
        new GraphNode(6, two);
        GraphNode three = new GraphNode(3, one);
        new GraphNode(4, one);
        seven.addChild(three);
        return one;
    }

    /**
     * 2 and 3 point at 4
     * 1--->2------>3-->7
     *      |-->4<--|
     *
     */
    private static GraphNode createX() {
        GraphNode one = new GraphNode(1);
        GraphNode two = new GraphNode(2, one);
        GraphNode three = new GraphNode(3, one);
        GraphNode four = new GraphNode(4, two);
        three.addChild(four);
        return one;
    }

    /**
     * 1 and 7 both point at 3 which has 8 as a child, no cycle.
     *
     * 1----->2--->5-->7
     * |      |--->6   |
     * |----->3<-------|
     * |--->4  \-->8
     */

    private static GraphNode createSampleGraphWithAlmostCycleThatHasAChild() {
        GraphNode one = new GraphNode(1);
        GraphNode two = new GraphNode(2, one);
        GraphNode seven = new GraphNode(7, new GraphNode(5, two));
        new GraphNode(6, two);
        GraphNode three = new GraphNode(3, one);
        new GraphNode(4, one);
        seven.addChild(three);
        new GraphNode(8, three);
        return one;
    }

    /**
     * 3 points back at the root, cycle
     * |>1-->2
     * | |-->3
     * |-----|
     */
    private static GraphNode createSampleGraphWithRightCycle() {
        GraphNode one = new GraphNode(1);
        GraphNode two = new GraphNode(2);
        GraphNode three = new GraphNode(3);
        one.addChild(two);
        one.addChild(three);
        three.addChild(one);

        return one;
    }

    private static FindCycle createFindCycle() {
        return new MyFindCycle();
    }

    public static void test1() {
        assert(!createFindCycle().hasCycle(createSampleTree()));
    }

    public static void test2() {
        assert(createFindCycle().hasCycle(createSampleGraphWithLeftCycle()));
    }

    public static void test3() {
        assert(!createFindCycle().hasCycle(createSampleGraphWithAlmostCycleThatHasNoChildren()));
    }

    public static void test6() {
        assert(!createFindCycle().hasCycle(createSampleGraphWithAlmostCycleThatHasAChild()));
    }

    public static void test4() {
        assert(createFindCycle().hasCycle(createSampleGraphWithRightCycle()));
    }

    public static void test7() {
        assert(!createFindCycle().hasCycle(createX()));
    }

    public static void testSingleNode() {
        GraphNode root = new GraphNode(1);
        assert(!createFindCycle().hasCycle(root));
    }

    public static void testSingleNode2() {
        GraphNode root = new GraphNode(1);
        root.addChild(root);
        assert(createFindCycle().hasCycle(root));
    }

    public static void test5() {
        assert(!createFindCycle().hasCycle(null));
    }
    
    public static void main(String[] args) {
        try { testSingleNode(); } catch (AssertionError e) { System.out.println("testSingleNode failed: " + e); }
        try { testSingleNode2(); } catch (AssertionError e) { System.out.println("testSingleNode2 failed: " + e); }
        try { test1(); } catch (AssertionError e) { System.out.println("test1 failed: " +e ); }
        try { test2(); } catch (AssertionError e) { System.out.println("test2 failed: " +e ); }
        try { test3(); } catch (AssertionError e) { System.out.println("test3 failed: " +e ); }
        try { test4(); } catch (AssertionError e) { System.out.println("test4 failed: " +e ); }
        try { test5(); } catch (AssertionError e) { System.out.println("test5 failed: " +e ); }
        try { test6(); } catch (AssertionError e) { System.out.println("test6 failed: " +e ); }
        try { test7(); } catch (AssertionError e) { System.out.println("test7 failed: " +e ); }
        System.out.println("Tests complete.");
    }
}

