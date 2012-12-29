/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author Marcel
 */
public class Generatormatrix{
    /**
     * Anzahl der Linienelemente
     * 
     * @access  private
     * @var     int
     */
    private int _countLine;
    
    /**
     * Anzahl der Spaltenelemente
     * 
     * @access  private
     * @var     int
     */
    private int _countRows;
    
    /**
     * Generatormatrix
     * 
     * @access  private
     * @var     int-array
     */
    private int _gmatrix[][];

    /**
     * gueltige Codewoerter
     *
     * @access	private
     * @var		int-array
     */
    private List<int[]> _codewords = new ArrayList<>();

    /**
     * Galois-Feld
     *
     * @access	private
     * @var		int
     */
    private int _galois;

    /**
     * Konstruktor
     *
     * @access	public
     * @param	array Generatormatrix
     * @param	int array Standard 2 (GF(2))
     */
    public Generatormatrix(int[][] gmatrix, int galois){
	if(galois == 0){
		this._galois = 2;
	}else{
		this._galois = galois;
	}

        this._gmatrix = new int[2][4];
        
        this._gmatrix[0][0] = 1;
        this._gmatrix[0][1] = 0;
        this._gmatrix[0][2] = 1;
        this._gmatrix[0][3] = 1;
        
        this._gmatrix[1][0] = 0;
        this._gmatrix[1][1] = 1;
        this._gmatrix[1][2] = 1;
        this._gmatrix[1][3] = 2;
        
	setRowDimension();
	setLineDimension();

	int[] a = new int[4];
	a[0] = 1;
	a[1] = 0;
	a[2] = 1;
	a[3] = 1;

	int[] b = new int[4];
	b[0] = 0;
	b[1] = 1;
	b[2] = 1;
	b[3] = 2;

	// Werte aus Generatormatrix in Codewords uebernehmen 
        /*for(int i = 0; i < this._countLine; i++){
            int tmp[] = new int[this._gmatrix[i].length];
            for(int j = 0; j < this._countRows; j++){
                tmp[i] = this._gmatrix[i][j];
                this._codewords.add(tmp);
            }
        }*/

	this._codewords.add(a);
	this._codewords.add(b);

	generateCodewordMatrix();

	System.out.println(this._codewords.size());

	for(int i = 0; i < this._codewords.size(); i++){
            int[] k = this._codewords.get(i);
            for(int j = 0; j < k.length; j++){
                System.out.print(k[j]);
            }
            System.out.print("\n");
	}
    }
    
    /**
     * setzt die Anzahl des Zeilen der Arrays
     * 
     * @access  private
     */
    private void setLineDimension(){
        if(_gmatrix.length > 0){
            this._countLine = this._gmatrix.length;
        }
    }
    
    /**
     * setzt die Anzahl des Spalten (0-tes Zeile) der Arrays
     * 
     * @access  private
     */
    private void setRowDimension(){
        if(_gmatrix[0].length > 0){
            this._countRows = this._gmatrix[0].length;
        }
    }

    /**
     * ueberpruefe, ob Generatormatrix eine Einheitsmatrix enthaelt
     *
     * @return	boolean
     */
    private boolean checkGeneratorMatrix(){
        int dim = this._countLine;
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if(i == j && this._gmatrix[i][j] != 1){
                    return false;
                }else if(i != j && this._gmatrix[i][j] != 0){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Generiert ein neues gueltiges Codewort
     *
     * @param	int[] code
     * @param	int[] code
     */
    private void generateCode(int[] code_one, int[] code_two){
        if(code_one.length > 0 && code_two.length > 0 && code_one.length == code_two.length){
            int[] code = new int[code_one.length];
            for(int i = 0; i < code_one.length; i++){
                code[i] = ((code_one[i] + code_two[i]) % this._galois);
            }

            if(searchValue(code) == false){
                this._codewords.add(code);
                this._countLine++;
            }
        }else{
            throw new IllegalArgumentException("parameter is wrong");
        }
    }

    /**
     * generiert die Matrix mit allen Codewoertern
     *
     * @access	private
     */
    private void generateCodewordMatrix(){
        System.out.println("generateCodewordMatrix()");
        System.out.println(this._codewords.size());
        try{
            boolean completed = false;
            int current = 0;

            while(completed == false){
                System.out.println("Groesse: " + this._codewords.size());
                int k = this._codewords.size();
                for(int i = 0; i < k; i++){
                    System.out.println("groesse: " + this._codewords.size() + " curr: " + current + " i: " + i);
                    if(i == current){
                        continue;
                    }

                    generateCode(this._codewords.get(i), this._codewords.get(current));
                    
                    if(current == this._codewords.size()){
                        completed = true;
                    }
                }

                current++;
            }
        }catch(Exception e){
            System.out.println("exception caught: " + e.getMessage());
        }
    }

    /**
     * sucht einen gueltigen Code in der Liste aller gueltigen
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
     * @param args the command line arguments
     */
    public static void main(String[] args){
            int[][] code = new int[1][1];
            code[0][0] = 1;
            Generatormatrix gmatrix = new Generatormatrix(code, 3);
    }
}
