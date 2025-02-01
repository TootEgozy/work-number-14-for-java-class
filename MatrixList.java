
/**
 * Write a description of class MatrixList here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MatrixList
{
    IntNodeMat _m00;

    public MatrixList() {
        _m00 = null;
    }
    
    
    private IntNodeMat matrixToNode(int[][] m) {
        // // check if we are out of bounds, and if so return null
        // if(row < 0 || row > (m.length - 1) || col < 0 || col > (m[0].length - 1)) {
            // return null;   
        // }
        
        // // if we are in bounds create a new node
        // IntNodeMat node = new IntNodeMat(m[row][col]);
        
        // node.setNextRow(matrixToNode(m, row + 1, col));
        // node.setNextCol(matrixToNode(m, row, col + 1));
        // node.setPrevRow(matrixToNode(m, row - 1, col));
        // node.setPrevCol(matrixToNode(m, row, col - 1));
        
        // return node;
        
        
        IntNodeMat prevNode = null;
        int prevRowCount = 1;
        boolean reverse = false;
        
        int rowCount = m[0].length;
        int colCount = m.length;
        
        for(int i = 0; i < rowCount; i++) {
            if(reverse) {
                for(int j = colCount - 1; j >= 0; j--) {
                    IntNodeMat newNode = new IntNodeMat(m[j][i]); 
                    System.out.println(newNode.getData());
                    if(j == colCount - 1) {
                        newNode.setPrevRow(prevNode);    
                    } else {
                        newNode.setNextCol(prevNode);
                    }
                    if(j == 0) reverse = !reverse;
                    prevNode = newNode;
                }
                
            } else {
                    for(int j = 0; j < colCount; j++ ) {
                    IntNodeMat newNode = new IntNodeMat(m[j][i]); 
                    System.out.println(newNode.getData());
                    if(j == 0) {
                        newNode.setPrevRow(prevNode);    
                    } else {
                        newNode.setPrevCol(prevNode);
                    }
                    if(j == colCount - 1) reverse = !reverse;
                    prevNode = newNode;
                }
            }
        }
        
        return prevNode;
    }

    public MatrixList(int[][]mat) {
        // _m00 = matrixToNode(mat, 0, 0);
        
    }
    
    public void tester() {
        int[][] m = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        IntNodeMat matirxHeadNode = matrixToNode(m);
        System.out.println("done creating the matrix");
        printList(matirxHeadNode);
    }
    

    public int getData_i_j (int i, int j) {
        return 0;
    }

    public void setData_i_j (int data, int i, int j) {

    }
    
    private void printList(IntNodeMat headNode) {
        
        IntNodeMat node = headNode;
        String direction = "L";
        while (node != null) {
            System.out.println(node.getData());
            if(direction == "L") {
                if(node.getPrevCol() != null) node = node.getPrevCol();
                else if(node.getPrevRow() != null) {
                    node = node.getPrevRow();
                    direction = "R";
                }
                else node = null;
            } else {
                if(node.getNextCol() != null) node = node.getNextCol();
                else if(node.getPrevRow() != null) {
                    node = node.getPrevRow();
                    direction = "L";
                }
                else node = null;   
            }
        }
    }
    
    // a recursive function to create the string to print the matrix.
    // we are zigzaging down the matrix, starting from the top left node, and progressing on the row 
    // using the direction flag - "R" to continue right and "L" for left. 
    // when we reached the end of the row, we go down a row and flip the direction, until the end is reached and a string is returned.
    // each recursion returns the value of the node along with a seperating character - tab or newline.
    // please notice that I hard-coded the direction flag "L" or "R" intentionally, instead of passing the parameter, 
    // because in my opinion it improves the code readability. 
    private String stringify(IntNodeMat node, String direction) {
        if (direction == "R") {
            if(node.getNextRow() != null) return node.getData() + "\t" + stringify(node.getNextRow(), "R");
            else if (node.getNextCol() != null) return node.getData() + "\n" + stringify(node.getNextCol(), "L");
            else return node.getData() + "\n";
        } else { // direction is "L"
            if(node.getPrevRow() != null) return node.getData() + "\t" + stringify(node.getPrevRow(), "L");
            else if (node.getNextCol() != null) return node.getData() + "\n" + stringify(node.getNextCol(), "R");
            else return node.getData() + "\n";
        }
    }


    public String toString() {
        System.out.print(stringify(_m00, "R"));
        return null;
    }

    public int findMax() {
        return 0;
    }

    public int howManyX(int x) {
        return 0;
    }

}
