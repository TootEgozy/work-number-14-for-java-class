
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
    
    
    private IntNodeMat addPointersToList(IntNodeMat tail, int colCount) {
        boolean reverse = true;
        if(tail.getNextCol() == null) reverse = false;
        
        int hop = (colCount * 2) - 1;
        IntNodeMat currentNode = tail;
        boolean done =  false;
        
        while(!done) {
            System.out.println("priniting from the while loop in addPointers. current node is: "+ currentNode.getData());
            if(!reverse) {
                if(currentNode.getPrevCol() == null) {
                    if(currentNode.getPrevRow() == null) {
                        // we reached the end of the list. return the last node which is _m00.
                        return currentNode;
                    }   
                    // we reached the end of a row. set the row pointers of the node and the one above it 
                    // to each other and switch the direction of the row.
                    IntNodeMat temp = currentNode;
                    currentNode = currentNode.getPrevRow();
                    currentNode.setNextRow(temp);
                    reverse = !reverse;
                    System.out.println("setting the up node "+currentNode.getData() + " to point down at this node "+temp.getData());
                } else { // we have more nodes in the row.
                    // add a pointer to the current node from the next one.
                    IntNodeMat nextNode = currentNode.getPrevCol();
                    nextNode.setNextCol(currentNode);
                    System.out.println("setting " + nextNode.getData() + " to point to the next col: "+currentNode.getData());
                    
                    // this loop searches for the node above the current one.
                    // then we set their row pointers to each other.
                    IntNodeMat tempNextNodeUp = currentNode;
                    for(int i = 0; i < hop; i++) {
                        if(tempNextNodeUp.getPrevCol() != null) tempNextNodeUp = tempNextNodeUp.getPrevCol();
                        else if (tempNextNodeUp.getPrevRow() != null) tempNextNodeUp = tempNextNodeUp.getPrevRow();
                        else if (tempNextNodeUp.getNextCol() != null) tempNextNodeUp = tempNextNodeUp.getNextCol();
                        else tempNextNodeUp = null;
                    }
                    if(tempNextNodeUp != null) {
                        currentNode.setPrevRow(tempNextNodeUp);
                        tempNextNodeUp.setNextRow(currentNode);
                        System.out.println("setting " + currentNode.getData() + " to point up to " + tempNextNodeUp.getData());
                        System.out.println("setting " + tempNextNodeUp.getData() + " to point down to " + currentNode.getData());
                    }
                    currentNode = currentNode.getPrevCol();
                }
                
            } else {
                if(currentNode.getNextCol() == null) {
                    if(currentNode.getPrevRow() == null) {
                        // we reached the end of the list. return the last node which is _m00.
                        return currentNode;
                    }
                    // we reached the end of a row. set the row pointers of the node and the one above it 
                    // to each other and switch the direction of the row.
                    IntNodeMat temp = currentNode;
                    currentNode = currentNode.getPrevRow();
                    currentNode.setNextRow(temp);
                    reverse = !reverse;
                    System.out.println("setting the up node "+currentNode.getData() + " to point down at this node "+temp.getData());
                } else { // we have more nodes in the row.
                    // add a pointer to the current node from the next one.
                    IntNodeMat nextNode = currentNode.getNextCol();
                    nextNode.setPrevCol(currentNode);
                    System.out.println("setting " + nextNode.getData() + " to point to the prev col: "+currentNode.getData());
                    
                    // this loop searches for the node above the current one.
                    // then we set their row pointers to each other.
                    IntNodeMat tempNextNodeUp = currentNode;
                    for(int i = 0; i < hop; i++) {
                        if(tempNextNodeUp.getNextCol() != null) tempNextNodeUp = tempNextNodeUp.getNextCol();
                        else if (tempNextNodeUp.getPrevRow() != null) tempNextNodeUp = tempNextNodeUp.getPrevRow();
                        else if (tempNextNodeUp.getPrevCol() != null) tempNextNodeUp = tempNextNodeUp.getPrevCol();
                        else tempNextNodeUp = null;
                    }
                    if(tempNextNodeUp != null) {
                        currentNode.setPrevRow(tempNextNodeUp);
                        tempNextNodeUp.setNextRow(currentNode);
                        System.out.println("setting " + currentNode.getData() + " to point up to " + tempNextNodeUp.getData());
                        System.out.println("setting " + tempNextNodeUp.getData() + " to point down to " + currentNode.getData());
                    }
                    currentNode = currentNode.getNextCol();
                }
            }
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
        IntNodeMat head = addPointersToList(prevNode, colCount);
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
