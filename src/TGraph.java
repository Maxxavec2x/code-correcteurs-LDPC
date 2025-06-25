public class TGraph {
    private int n_r, w_r, n_c, w_c;
    private int[][] left;
    private int[][] right;

    public TGraph(Matrix H, int wc, int wr) {
        this.n_r = H.getRows();
        this.w_r = wr;
        this.n_c = H.getCols();
        this.w_c = wc;
        this.left = new int[n_r][w_r + 1];
        this.right = new int[n_c][w_c + 1];
        this.remplir_right(H);
        this.remplir_left(H);
    }

    private void remplir_right(Matrix h) {
        int count = 0;
        for (int j = 0; j < h.getCols(); j++) {
            count = 0;
            for (int i = 0; i < h.getRows(); i++) {
                if (h.getElem(i, j) == 1) {
                    right[j][count + 1] = i;
                    count++;
                }
            }
        }
    }
    private void remplir_left(Matrix h) {
        int count = 0;
        for (int i = 0; i < h.getRows(); i++) {
            count = 0;
            for (int j = 0; j < h.getCols(); j++) {
                if (h.getElem(i, j) == 1) {
                    left[i][count + 1] = j;
                    count++;
                }
            }
        }
    }

    public void display() {
        // Affichage de left
        System.out.print("[");
        for (int i = 0; i < n_r; i++) {
            if (i != 0) {
                System.out.print(" ");
            }

            System.out.print("[");

            for (int j = 0; j < w_r+1; j++) {
                System.out.printf("%d", left[i][j]);

                if (j != w_r) {
                    System.out.print(" ");
                }
            }

            System.out.print("]");

            if (i == n_r - 1) {
                System.out.print("]");
            }

            System.out.println();
        }
        System.out.println();

        // Affichage de right :


        System.out.print("[");
        for (int i = 0; i < n_c; i++) {
            if (i != 0) {
                System.out.print(" ");
            }

            System.out.print("[");

            for (int j = 0; j < w_c+1; j++) {
                System.out.printf("%d", right[i][j]);

                if (j != w_c) {
                    System.out.print(" ");
                }
            }

            System.out.print("]");

            if (i == n_c - 1) {
                System.out.print("]");
            }

            System.out.println();
        }
        System.out.println();
    }




}

