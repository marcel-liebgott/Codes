import java.util.ArrayList;

/**
 *
 * @author Marcel
 */
public class test {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try{
            ArrayList<int[]> code = new ArrayList<>();
            int[] l1 = new int[4];
            l1[0] = 1;
            l1[1] = 0;
            l1[2] = 1;
            l1[3] = 1;
            int[] l2 = new int[4];
            l2[0] = 0;
            l2[1] = 1;
            l2[2] = 1;
            l2[3] = 2;
            
            code.add(l1);
            code.add(l2);
            
            Generatormatrix gmatrix = new Generatormatrix(code, 3);
            
            gmatrix.showStatistics();
            
            gmatrix.showAllEnableCodes();
        }catch(Exception e){
            System.out.println("exception caught: " + e.getMessage());
        }
    }
}
