/**
 * This class will be calculate all enabled codewords for a generatormatrix
 * in a galois field. It check it for double elements in the result and would be 
 * to give u a small statistic about the result.
 * 
 * @author  Marcel
 * @version 1.00  
 */

import java.util.*;

public class Generatormatrix{
    /**
     * count of lines
     * 
     * @access  private
     * @var     int
     */
    private int _countLine;
    
    /**
     * count of rows
     * 
     * @access  private
     * @var     int
     */
    private int _countRows;
    
    /**
     * generatormatrix
     * 
     * @access  private
     * @var     int-array
     */
    private int _gmatrix[][];

    /**
     * enabled codewords
     *
     * @access	private
     * @var		int-array
     */
    private List<int[]> _codewords;

    /**
     * galois field
     *
     * @access	private
     * @var		int
     */
    private int _galois;

    /**
     * constructor
     *
     * @access	public
     * @param	array generatormatrix
     * @param	int array standard 2 (GF(2))
     * @throws  Exception
     */
    public Generatormatrix(ArrayList<int[]> gmatrix, int galois) throws Exception{
        try {
            if(!(gmatrix instanceof ArrayList)){
                throw new IllegalArgumentException("generatormatrix must be a instance of ArrayList");
            }
            
            if(galois == 0){
                    this._galois = 2;
            }else{
                    this._galois = galois;
            }
            
            if(checkGeneratorMatrix() == true){
                this._codewords = new ArrayList<int[]>(gmatrix);

                generateCodewordMatrix();

                setStatistics();
            }
        }catch(IllegalArgumentException | IndexOutOfBoundsException ee){
            System.out.println("exception caught: " + ee.getMessage());
        }catch(Exception ex){
            System.out.println("exception caught: " + ex.getMessage());
        }
    }
    
    /**
     * get all statistics
     * 
     * @access  private
     */
    private void setStatistics(){
        setLineDimension();
        setRowDimension();
    }
    
    /**
     * set count of line
     * 
     * @access  private
     */
    private void setLineDimension(){
        if(this._codewords.size() > 0){
            this._countLine = this._codewords.size();
        }
    }
    
    /**
     * set count of rows
     * 
     * @access  private
     */
    private void setRowDimension(){
        if(this._codewords.size() > 0){
            // get the first element
            int[] tmp = this._codewords.get(0);

            this._countRows = tmp.length;
        }
    }

    /**
     * check the generatormatrix for the identity matrix and
     * the maximum value of his elements like the galois value
     *
     * @return	boolean
     * @throws  Exception
     */
    private boolean checkGeneratorMatrix() throws Exception{
        int dim = this._countLine;
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if(this._gmatrix[i][j] < this._galois){
                    if(i == j && this._gmatrix[i][j] != 1){
                        return false;
                    }else if(i != j && this._gmatrix[i][j] != 0){
                        return false;
                    }
                }else{
                    throw new Exception("generator matrix have a wrong element");
                }
            }
        }
        return true;
    }

    /**
     * generate a new codeword
     *
     * @param	int[] code
     * @param	int[] code
     * @throws  Exception
     */
    private void generateCode(int[] code_one, int[] code_two){
        if(code_one.length > 0 && code_two.length > 0 && code_one.length == code_two.length){
            int[] code = new int[code_one.length];
            for(int i = 0; i < code_one.length; i++){
                code[i] = ((code_one[i] + code_two[i]) % this._galois);
            }

            if(searchValue(code) == false){
                this._codewords.add(code);
            }
        }else{
            throw new IllegalArgumentException("parameter is wrong");
        }
    }

    /**
     * generate the codeword matrix
     *
     * @access	private
     * @throws  Exception
     */
    private void generateCodewordMatrix(){
        try{
            boolean completed = false;
            int current = 0;

            while(completed == false || current < this._codewords.size()){
                int k = this._codewords.size();
                for(int i = 0; i < k; i++){
                    if(i == current){
                        continue;
                    }

                    if(current == this._codewords.size()){
                        completed = true;
                        break;
                    }
                    
                    generateCode(this._codewords.get(i), this._codewords.get(current));
                }

                current++;
            }
        }catch(IndexOutOfBoundsException ee){
            System.out.println("exception caught: " + ee.getMessage());
        }catch(Exception e){
            System.out.println("exception caught: " + e.getMessage());
        }
    }

    /**
     * check the new codeword with all enabled codewords for double items
     *
     * @param	int-array
     * @return	boolean
     */
    private boolean searchValue(final int[] value){
        for(final int[] arr : this._codewords){
            if(Arrays.equals(arr, value)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * get all enabled codewords
     * 
     * @access  public
     * @return  List<int[]>
     * @throws  Exception
     */
    public final List<int[]> getEnableCodewords() throws Exception{
        if(!(this._codewords instanceof ArrayList)){
            throw new Exception("codewords has a wrong type");
        }
        
        return this._codewords;
    }
    
    /**
     * show all statistics
     * 
     * @access  public
     */
    public final void showStatistics(){
        System.out.println("dimension: " + this._countRows + "x" + this._countLine);
        System.out.println("size: " + this._codewords.size());
        System.out.println("galois: " + this._galois);
    }
    
    /**
     * show all enable Codewords
     * 
     * @access  public
     * @throws  Exception 
     */
    public void showAllEnableCodes() throws Exception{
        if(this._codewords.size() > 0){
            for(int i = 0; i < this._codewords.size(); i++){
                int[] j = this._codewords.get(i);                
                
                if(j.length == this._countRows){
                    System.out.print((i + 1) + ": ");
                    for(int k = 0; k < this._countRows; k++){
                        System.out.print(j[k]);
                    }
                    System.out.print("\n");
                }else{
                    throw new Exception("somethink wrong by calculation of enable codewords");
                }
            }
        }
    }
}