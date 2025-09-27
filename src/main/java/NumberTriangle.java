import java.io.*;

/**
 * EasterQUiz for surprise
 * This is the provided NumberTriangle class to be used in this coding task.
 *<p>
 * Note: This is like a tree, but some nodes in the structure have two parents.
 * <p>
 * The structure is shown below. Observe that the parents of e are b and c, whereas
 * d and f each only have one parent. Each row is complete and will never be missing
 * a node. So each row has one more NumberTriangle object than the row above it.
 * <p>
 *                  a
 *                b   c
 *              d   e   f
 *            h   i   j   k
 * <p>
 * Also note that this data structure is minimally defined and is only intended to
 * be constructed using the loadTriangle method, which you will implement
 * in this file. We have not included any code to enforce the structure noted above,
 * and you don't have to write any either.
 * <p>
 *
 * See NumberTriangleTest.java for a few basic test cases.
 * <p>
 * Extra: If you decide to solve the Project Euler problems (see main),
 *        feel free to add extra methods to this class. Just make sure that your
 *        code still compiles and runs so that we can run the tests on your code.
 *
 */
public class NumberTriangle {

    private final int root;

    private NumberTriangle left;
    private NumberTriangle right;

    public NumberTriangle(int root) {
        this.root = root;
    }

    public void setLeft(NumberTriangle left) {
        this.left = left;
    }


    public void setRight(NumberTriangle right) {
        this.right = right;
    }

    public int getRoot() {
        return root;
    }


    /**
     * [not for credit]
     * Set the root of this NumberTriangle to be the max path sum
     * of this NumberTriangle, as defined in Project Euler problem 18.
     * After this method is called, this NumberTriangle should be a leaf.
     * <p>
     * Hint: think recursively and use the idea of partial tracing from first year :)
     * <p>
     * Note: a NumberTriangle contains at least one value.
     */
    public void maxSumPath() {
        // for fun [not for credit]:
    }


    public boolean isLeaf() {
        return right == null && left == null;
    }


    /**
     * Follow path through this NumberTriangle structure ('l' = left; 'r' = right) and
     * return the root value at the end of the path. An empty string will return
     * the root of the NumberTriangle.
     * <p>
     * You can decide if you want to use a recursive or an iterative approach in your solution.
     * <p>
     * You can assume that:
     * the length of path is less than the height of this NumberTriangle structure.
     * each character in the string is either 'l' or 'r'
     * <p>
     * Hence return the final value followed by the trace provided
     *
     * @param path the path to follow through this NumberTriangle
     *
     */
    public int retrieve(String path) {
        // TODO implement this method; Finished
        NumberTriangle curr = this;

        for (char step : path.toCharArray()) {
            if (step == 'l') {
                curr = curr.left;
            } else if (step == 'r') {
                curr = curr.right;
            } else {
                throw new IllegalArgumentException("Invalid character in path: " + step);
            }
        }
        return curr.root;
    }

    /** Read in the NumberTriangle structure from a file.
     * <p>
     * You may assume that it is a valid format with a height of at least 1,
     * so there is at least one line with a number on it to start the file.
     * <p>
     * See resources/input_tree.txt for an example NumberTriangle format.
     *
     * @param fname the file to load the NumberTriangle structure from
     * @return the topmost NumberTriangle object in the NumberTriangle structure read from the specified file
     * @throws IOException may naturally occur if an issue reading the file occurs
     */
    public static NumberTriangle loadTriangle(String fname) throws IOException {
        // open the file and get a BufferedReader object whose methods
        // are more convenient to work with when reading the file contents.
        InputStream inputStream = NumberTriangle.class.getClassLoader().getResourceAsStream(fname);
        assert inputStream != null;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        // TODO define any variables that you want to use to store things

        // will need to return the top of the NumberTriangle,
        // so might want a variable for that.
        NumberTriangle top = null;

        // Keep track of the row we built before
        java.util.List<NumberTriangle> prevRow = null;

        String line = br.readLine();
        while (line != null) {

            // Split the line into numbers (like "7 4" → [7, 4])
            String[] tokens = line.trim().split("\\s+");

            // Make a list for the current row
            java.util.List<NumberTriangle> currRow = new java.util.ArrayList<>();

            // Turn each number into a NumberTriangle node
            for (String tok : tokens) {
                int val = Integer.parseInt(tok);
                NumberTriangle node = new NumberTriangle(val);
                currRow.add(node);
            }

            // If this is the very first row, set the top
            if (top == null) {
                top = currRow.get(0);
            }

            // If there was a row before, connect parents to children
            if (prevRow != null) {
                for (int i = 0; i < prevRow.size(); i++) {
                    NumberTriangle parent = prevRow.get(i);
                    parent.setLeft(currRow.get(i));      // left child
                    parent.setRight(currRow.get(i + 1)); // right child
                }
            }

            // Move down: current row becomes previous row for next loop
            prevRow = currRow;

            //read the next line
            line = br.readLine();
        }
        br.close();
        return top;
    }

    public static void main(String[] args) throws IOException {

        NumberTriangle mt = NumberTriangle.loadTriangle("input_tree.txt");

        // [not for credit]
        // you can implement NumberTriangle's maxPathSum method if you want to try to solve
        // Problem 18 from project Euler [not for credit]
        mt.maxSumPath();
        System.out.println(mt.getRoot());
    }
}
