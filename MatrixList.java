
/**
 Toot Egozy 313384612
 קיבלתי אישור להגיש את הממ"ן באיחור, אשמח להתחשבות בכך, תודה רבה 
 */
public class MatrixList
{
    IntNodeMat _m00;

    public MatrixList() {
        _m00 = null;
    }
    
    // a helper method which connects a node to the one directly above it in the matrix.
    // this method uses a loop to go up the list. hop is the number of required iterations to get to the node exactly 
    // above the given node. once the loop is over the nodes pointers are set to point up / down at each other.
    // if there is no node above (we are in the first row of the matrix) the method does nothing.    
    private void setUpperPointer(IntNodeMat node, int hop, boolean reverse) {
        IntNodeMat tempNode = node;
        boolean isReversed = reverse;
        
        // this loop just goes up the list (hop) times, and changes nothing with the nodes. 
        // with each iteration it sets tempNode to be the node it reached.
        for(int i = 0; i < hop; i++) {
            if(isReversed) {
                if(tempNode.getPrevCol() != null) tempNode = tempNode.getPrevCol(); // move left until the end of the row.
                else if(tempNode.getPrevRow() != null) {
                    tempNode = tempNode.getPrevRow(); // go up
                    isReversed = !isReversed;
                } else return; // if there is no upper row exit the function.
            } else {
                if(tempNode.getNextCol() != null) tempNode = tempNode.getNextCol(); // move left until the end of the row.
                else if(tempNode.getPrevRow() != null) {
                    tempNode = tempNode.getPrevRow(); // go up
                    isReversed = !isReversed;
                } else return; // if there is no upper row exit the function.                
            }
        }
        // we reached the end of the loop so tempNode is the node above node. 
        // set their up / down pointers to each other.
        node.setPrevRow(tempNode);
        tempNode.setNextRow(node);
    }
    
    // a helper method which gets the tail of the list, and traverses it to the beginning, 
    // setting the node pointers to each other to "fold" the list into a matrix.
    // the method returns the head of the list.
    private IntNodeMat addPointersToList(IntNodeMat tail, int colCount, int matLength) {
        // the direction we begin with.
        boolean reverse = true;
        if(tail.getPrevCol() == null) reverse = false;
        
        // hop is the distance (in nodes) which we need to traverse on the linear list to get to the node directly above it.
        int hop = (colCount * 2) - 1;
        IntNodeMat node = tail;
        IntNodeMat temp = node;
        int counter = 0; // safty to avoid endless looping in case of a bug. 
        
        // this loop handles pointing following nodes to each other (which are already connected by 1 pointer).
        // to connect the nodes which are above or below each other in the matrix, we call the method setUpperPointer.
        while(counter < matLength) {
            if((reverse && node.getPrevCol() == null) || (!reverse && node.getNextCol() == null )) {// node is the last of the row
                if(node.getPrevRow() == null) return node; // node is also the end of the list so return it.
                // node is the last of the row and there's a row above it. 
                // set the pointer of the node above it to point down to it, and reset the variables to start a new row.
                temp = node;
                node = node.getPrevRow();
                node.setNextRow(temp);
                reverse = !reverse;
                hop = (colCount * 2) -1;
            } else { // continue on the same row
                // set the pointers between the node and the one directly above it.
                setUpperPointer(node, hop, reverse);
                hop = hop - 2;
                // set the node pointer to the next or previous column.
                if(reverse) {
                    temp = node;
                    node = node.getPrevCol();
                    node.setNextCol(temp);                    
                } else { // direction is right
                    temp = node;
                    node = node.getNextCol();
                    node.setPrevCol(temp);                    
                }
                
            }
            counter++;
        }
        return null;
    }
    
    private boolean isMatrixEmpty(int[][] m) {
        if(m == null || m.length == 0 || m[0].length == 0) return true;
        return false;
    }
    
    // a helper method to create a node matrix from 2D array.
    // this method constructs a zigzag linked list, and the calles addPointersToList to add more pointers
    // to the nodes and "fold" the list into a matrix.
    // it returns the head node of the matrix.
    private IntNodeMat matrixToNode(int[][] m) {
        
        // ****************** IMPORTANT ******************
        // * find out wht to return if matrix is empty *
        // ***********************************************
        if(isMatrixEmpty(m)) return new IntNodeMat(0);
        
        IntNodeMat prevNode = null;
        
        // we start with the direction left to right.
        boolean reverse = false;
        
        // these variables might be redundent but I believe that they help to simplify the code and add readability, 
        // therefore I chose to define them here.
        int rowCount = m[0].length;
        int colCount = m.length;
        
        for(int i = 0; i < rowCount; i++) {
            // for each matrix row, go over it in the ascribed direction. for each value, create a new node.
            // point it to the previous node that was created, using the column pointer (prev or next) 
            // if we are in the middle of the row,or the prevRow pointer if we are on the last cell of the row.
            if(reverse) {
                for(int j = colCount - 1; j >= 0; j--) {
                    IntNodeMat newNode = new IntNodeMat(m[j][i]); 
                    if(j == colCount - 1) newNode.setPrevRow(prevNode);    
                    else newNode.setNextCol(prevNode);
                    
                    if(j == 0) reverse = !reverse;
                    prevNode = newNode;
                }
                
            } else {
                    for(int j = 0; j < colCount; j++ ) {
                    IntNodeMat newNode = new IntNodeMat(m[j][i]); 
                    if(j == 0) newNode.setPrevRow(prevNode);    
                    else newNode.setPrevCol(prevNode);

                    if(j == colCount - 1) reverse = !reverse;
                    prevNode = newNode;
                }
            }
        }
        // pass the linked list that was created to addPointersToList, so more pointers are added
        // and all of the nodes are linked to their adjacent nodes.
        // we pass the list last node and get back the head.
        IntNodeMat head = addPointersToList(prevNode, colCount, rowCount * colCount);
        return head;
    }

    public MatrixList(int[][]mat) {
        _m00 = matrixToNode(mat);

    }
    
    private IntNodeMat makeMatrix() {
        IntNodeMat node1 = new IntNodeMat(1);
        IntNodeMat node2 = new IntNodeMat(2);
        
        IntNodeMat node3 = new IntNodeMat(3);
        IntNodeMat node4 = new IntNodeMat(4);
        
        IntNodeMat node5 = new IntNodeMat(5);
        IntNodeMat node6 = new IntNodeMat(6);
        
        node1.setNextCol(node3);
        node1.setNextRow(node2);
        
        node2.setPrevRow(node1);
        node2.setNextCol(node4);
        
        node3.setPrevCol(node1);
        node3.setNextRow(node4);
        node3.setNextCol(node5);
        
        node4.setPrevCol(node2);
        node4.setPrevRow(node3);
        node4.setNextCol(node6);
        
        node5.setPrevCol(node3);
        node5.setNextRow(node6);
        
        node6.setPrevCol(node4);
        node6.setPrevRow(node5);
        
        return node1;
    };
    
    public void testPrint() {
        int[][] m1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        int[][] m2 = {
            {1},
            {2},
            {3}
        };
        
        int[][] m3 = {
            {1, 2, 3}
        };
        int[][] m4 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
        };
        
        int[][] m5 = {
            {1, 2},
            {3, 4},
            {5, 6}
        };
        
        int[][] m6 = {{}};
        
        IntNodeMat node1 = matrixToNode(m1);
        IntNodeMat node2 = matrixToNode(m2);
        IntNodeMat node3 = matrixToNode(m3);
        IntNodeMat node4 = matrixToNode(m4);
        IntNodeMat node5 = matrixToNode(m5);
        IntNodeMat node6 = matrixToNode(m6);
        
        String str1 = stringify(node1, "R");
        System.out.println(str1);
        
        String str2 = stringify(node2, "R");
        System.out.println(str2);
        
        String str3 = stringify(node3, "R");
        System.out.println(str3);
        
        String str4 = stringify(node4, "R");
        System.out.println(str4);
        
        String str5 = stringify(node5, "R");
        System.out.println(str5);
        
        String str6 = stringify(node6, "R");
        System.out.println(str6);
        
    }
    
    public void testSetGetIndex() {
        int[][] m1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        int[][] m2 = {
            {1},
            {2},
            {3}
        };
        
        int[][] m3 = {
            {1, 2, 3}
        };
        int[][] m4 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
        };
        
        int[][] m5 = {
            {1, 2},
            {3, 4},
            {5, 6}
        };
        
        int[][] m6 = {{}};
        
        MatrixList mn2 = new MatrixList(m2);
        System.out.println(mn2.getData_i_j(0, 1));
        
    }
    
    
    // a helper method to find a node in the matrix from row and column indexes.
    // the method starts with the head of the matrix, uses a loop which repeats i times 
    // to move down the rows of the matrix, and another loop which repeats j times to move to the j node.
    // each iteration assigns the new node to the variable tempNode.
    // when the 2 loops are finished tempNode should be the i, j node, and the method returns it.
    // if we got an invalid index, the method returns null. 
    private IntNodeMat findNodeFromIndexes(int i, int j) {
        IntNodeMat tempNode = _m00;
        
        for(int row = 0; row < i; row ++) {
            if(tempNode.getNextRow() != null) tempNode = tempNode.getNextRow();
            else return null;
        }
        
        for(int col = 0; col < j; col++) {
            if(tempNode.getNextCol() != null) tempNode = tempNode.getNextCol();
            else return null;
        }
        
        return tempNode;
    }
    
    
    // a method which receives the row index i and column index j and returns 
    // the data of the [i][j] node in the matrix. if the index is invalid, 
    // the method returns the minimal value of integer.
    // this method calls the helper method findNodeFromIndexes,which returns the node if found or null if not. 
    public int getData_i_j (int i, int j) {
        IntNodeMat node = findNodeFromIndexes(i, j);
        
        if(node == null) return Integer.MIN_VALUE;
               
        return node.getData();
    }
    
    
    // a method which receives the row index i, the column index j and a number, and sets 
    // the number as the data of the [i][j] node in the matrix.
    // this method uses the helper method findNodeFromIndexes, and if a node is returned from it it's dta is set.
    // if findNodeFromIndexes returns null, which means that the indexes are invalid, the method does nothing.
    public void setData_i_j (int data, int i, int j) {
        IntNodeMat node = findNodeFromIndexes(i, j);
        
        if(node != null) node.setData(data);
    }
    
    // a recursive helper function to create the string to print the matrix.
    // start from the head node and go right using the direction flag and the method getNextCol() of node, 
    // creacte a string from the nodes data and tab chars for the row.
    // once the end of the row is reached, a newline char is returned, and the direction flag is flipped.
    // the next calls for the recursion traverse to the left of the new row without adding chars,
    // until the beginning of the row is reached. 
    // then the direction flag switches to right and the method goes over the row to add the data and tab chars.
    // this method returns the compelete string which represents the matrix.
    private String stringify(IntNodeMat node, String direction) {
        if(node == null) return "";
        else if (direction == "R") { // direction is left to right, compile the node data and tab chars.
            if (node.getNextCol() != null) return node.getData() + "\t" + stringify(node.getNextCol(), "R");
            else if(node.getNextRow() != null) return node.getData() + "\n" + stringify(node.getNextRow(), "L");
            else return node.getData() + "\n";
        } else { // direction is right to left, move to the beginning of the row.
            if(node.getPrevCol() != null) return stringify(node.getPrevCol(), "L");
            else if(node.getNextCol() != null) return node.getData() + "\t" + stringify(node.getNextCol(), "R");
            else if(node.getNextRow() != null) return node.getData() + "\n" + stringify(node.getNextRow(), "L");
            else return node.getData() + "\n";
        }
    }
    
    public void printToString() {
        System.out.print(stringify(_m00, "R"));
    }

    // return the string repersentation of the node matrix, using tab and newline chars as seperators.
    public String toString() {
        return stringify(_m00, "R");
    }

    public int findMax() {
        return 0;
    }

    public int howManyX(int x) {
        return 0;
    }

}
