
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
    
    private void connectUpDownPointers(IntNodeMat node, int hop) {
        IntNodeMat upperNode = null;
    }
    
    private void setUpperPointer(IntNodeMat node, int hop, boolean reverse) {
        IntNodeMat tempNode = node;
        boolean isReversed = reverse;
        // go up the list and look for the node above node using hop to set their up / down pointers at each other.
        // if there is no node above (last row) do nothing.
        for(int i = 0; i < hop; i++) {
            if(isReversed) {
                if(tempNode.getPrevCol() != null) tempNode = tempNode.getPrevCol(); // move left until the end of the row.
                else if(tempNode.getPrevRow() != null) {
                    tempNode = tempNode.getPrevRow();
                    isReversed = !isReversed;
                } else return; // if there is no upper row exit the function.
            } else {
                if(tempNode.getNextCol() != null) tempNode = tempNode.getNextCol(); // move left until the end of the row.
                else if(tempNode.getPrevRow() != null) {
                    tempNode = tempNode.getPrevRow();
                    isReversed = !isReversed;
                } else return; // if there is no upper row exit the function.                
            }
        }
        // we reached the end of the loop so tempNode is the node above node. 
        // set their up / down pointers to each other.
        node.setPrevRow(tempNode);
        tempNode.setNextRow(node);
        
        // ^ this was the end, following is debug:
        int nodeValue = node.getData();
        int tempNodeValue = tempNode.getData();
        System.out.println("setting up and down pointers from setUpperPointer: ");
        System.out.println(nodeValue +" up to: "+tempNodeValue);
    }
    
    
    private IntNodeMat addPointersToList(IntNodeMat tail, int colCount, int matLength) {
        boolean reverse = true;
        if(tail.getPrevCol() == null) reverse = false;
        
        int hop = (colCount * 2) - 1;
        IntNodeMat node = tail;
        IntNodeMat temp = node;
        int counter = 0; // safty for the while loop to avoid endless looping in case of a bug. 
        
        while(counter < matLength) {
            System.out.println("node is: "+node.getData());
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
                setUpperPointer(node, hop, reverse);
                hop = hop - 2;
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
    
    private IntNodeMat matrixToNode(int[][] m) {     
        IntNodeMat prevNode = null;
        int prevRowCount = 1;
        boolean reverse = false;
        
        int rowCount = m[0].length;
        int colCount = m.length;
        
        for(int i = 0; i < rowCount; i++) {
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
        System.out.println("Finished creating the list, now adding pointers:");
        IntNodeMat head = addPointersToList(prevNode, colCount, rowCount * colCount);
        return head;
    }

    public MatrixList(int[][]mat) {
        // _m00 = matrixToNode(mat, 0, 0);
        
    }
    
    public void phase1PointerTester() {
        int[][] m = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        // int[][] m = {
            // {1},
            // {2},
            // {3}
        // };
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
