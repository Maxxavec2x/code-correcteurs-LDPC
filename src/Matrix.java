import java.util.*;
import java.io.*;

public class Matrix {
    private byte[][] data = null;
    private int rows = 0, cols = 0;
    
    public Matrix(int r, int c) {
        data = new byte[r][c];
        rows = r;
        cols = c;
    }
    
    public Matrix(byte[][] tab) {
        rows = tab.length;
        cols = tab[0].length;
        data = new byte[rows][cols];
        for (int i = 0 ; i < rows ; i ++)
            for (int j = 0 ; j < cols ; j ++) 
                data[i][j] = tab[i][j];
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public byte getElem(int i, int j) {
        return data[i][j];
    }
    
    public void setElem(int i, int j, byte b) {
        data[i][j] = b;
    }
    
    public boolean isEqualTo(Matrix m){
        if ((rows != m.rows) || (cols != m.cols))
            return false;
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                if (data[i][j] != m.data[i][j])
                    return false;
                return true;
    }
    
    public void shiftRow(int a, int b){
        byte tmp = 0;
        for (int i = 0; i < cols; i++){
            tmp = data[a][i];
            data[a][i] = data[b][i];
            data[b][i] = tmp;
        }
    }
    
    public void shiftCol(int a, int b){
        byte tmp = 0;
        for (int i = 0; i < rows; i++){
            tmp = data[i][a];
            data[i][a] = data[i][b];
            data[i][b] = tmp;
        }
    }
     
    public void display() {
        System.out.print("[");
        for (int i = 0; i < rows; i++) {
            if (i != 0) {
                System.out.print(" ");
            }
            
            System.out.print("[");
            
            for (int j = 0; j < cols; j++) {
                System.out.printf("%d", data[i][j]);
                
                if (j != cols - 1) {
                    System.out.print(" ");
                }
            }
            
            System.out.print("]");
            
            if (i == rows - 1) {
                System.out.print("]");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                result.data[j][i] = data[i][j];
    
        return result;
    }
    
    public Matrix add(Matrix m){
        Matrix r = new Matrix(rows,m.cols);
        
        if ((m.rows != rows) || (m.cols != cols))
            System.out.printf("Erreur d'addition\n");
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                r.data[i][j] = (byte) ((data[i][j] + m.data[i][j]) % 2);
        return r;
    }
    
    public Matrix multiply(Matrix m){
        Matrix r = new Matrix(rows,m.cols);
        
        if (m.rows != cols)
            System.out.printf("Erreur de multiplication\n");
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                r.data[i][j] = 0;
                for (int k = 0; k < cols; k++){
                    r.data[i][j] =  (byte) ((r.data[i][j] + data[i][k] * m.data[k][j]) % 2);
                }
            }
        }
        
        return r;
    }

    public void addRow(int a, int b) {
        for (int i = 0; i < cols; i++){
            data[b][i] = (byte) ((data[b][i] + data[a][i]) %2);
        }
    }
    public void addCol(int a, int b) {
        for (int i = 0; i < rows; i++){
            data[i][b] = (byte) ((data[i][b] +  data[i][a]) %2);
        }
    }

    public Matrix sysTransform() {
        int indice = cols - rows; // On commence à partir de la column égale à l'indice
        int j = indice;
        int i2;
        Matrix result = new Matrix(data);
        for (int i = 0; i < rows; i++ ) {
            for (i2 = i; i2 < rows; i2++) { // on parcourt les lignes en dessous pour trouver un 1.
                if (result.data[i2][j] == 1) { // On a un 1, donc c'est bon
                    result.shiftRow(i2, i);
                    break;
                }
            }
            for (int i3 = i2 + 1; i3 < rows; i3++) {
                if (result.data[i3][j] == 1) { // On a un 1, donc c'est bon
                    result.addRow(i, i3);
                }
            }
            j++;
        }
        j = cols - 1;
        // Deuxième étape :
        for (int i = rows - 1; i > 0; i-- ) {
            for (int i3 = i - 1; i3 >= 0; i3--) {
                if (result.data[i3][j] == 1) { // On a un 1, donc c'est bon
                    result.addRow(i, i3);
                }
            }
            j--;
        }
        return result;
    }

    public Matrix GenG() {
        Matrix control_systematique = this.sysTransform();
        int indice = cols - rows; //indice à partir duquel commence l'id
        byte[][] R = new byte[rows][indice];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < indice; j++) {
                R[i][j] = control_systematique.getElem(i, j);
            }
        }
        Matrix RMatrix = new Matrix(R);
        RMatrix = RMatrix.transpose();
        System.out.println("RMATRIX TRANSPOSE");

        RMatrix.display();
        Matrix result = new Matrix(indice, this.rows + indice); // matrice résultat transposée
        // On remplit l'identitée :
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.rows; j++) {
                if (i == j) {
                    result.setElem(i, j, (byte) 1);
                } else {
                    result.setElem(i, j, (byte) 0);
                }
            }
        }
        // Puis on remplit la transposée
        for (int i = 0; i< result.rows; i++) {
            for (int j = result.rows; j < result.cols; j ++) {
                result.data[i][j] = RMatrix.data[i][j - result.rows];
            }
        }
        return result;
    }

}

