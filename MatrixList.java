
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
    
    // a helper method to create a node matrix from 2D array.
    // this method constructs a zigzag linked list, and the calles addPointersToList to add more pointers
    // to the nodes and "fold" the list into a matrix.
    // it returns the head node of the matrix.
    private IntNodeMat matrixToNode(int[][] m) {     
        IntNodeMat prevNode = null;
        // int prevRowCount = 1; ///////////////////////////////////////////////////////////!!!! delete this
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
    
    public void phase1PointerTester() {
        // int[][] m = {
            // {1, 2, 3},
            // {4, 5, 6},
            // {7, 8, 9}
        // };
        
        // int[][] m = {
            // {1},
            // {2},
            // {3}
        // };
        
        // int[][] m = {
            // {1, 2, 3, 4},
            // {5, 6, 7, 8},
        // };
        
        int[][] m = {
            {1, 2},
            {3, 4},
            {5, 6}
        };
        IntNodeMat matirxHeadNode = matrixToNode(m);
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
