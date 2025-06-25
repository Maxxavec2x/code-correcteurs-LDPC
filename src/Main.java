import java.util.*;
import java.io.*;

public class Main {
    
    public static Matrix loadMatrix(String file, int r, int c) {
        byte[] tmp =  new byte[r * c];
        byte[][] data = new byte[r][c];
        try {
            FileInputStream fos = new FileInputStream(file);
            fos.read(tmp);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < r; i++)
            for (int j = 0; j< c; j++)
                data[i][j] = tmp[i * c + j];
            return new Matrix(data);
    }
    
    public static void main(String[] arg){

        Matrix hbase1 = loadMatrix("data/matrix-15-20-3-4", 15, 20);
        Matrix hbase2 = loadMatrix("data/matrix-2000-6000-5-15", 2000, 6000);
        Matrix g = hbase1.GenG();
        byte[][] tab = {{1,0,1,1,0,1,0,1,0,1,1,0,0,0,1,1,0,0,1,1}};
        Matrix mot = new Matrix(tab);

        TGraph monGraph = new TGraph(hbase1, 3, 4);
        monGraph.decode(mot, 50).display();

    }
}
