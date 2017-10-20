import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class program3 {

	static int solution;
	static long time; // runtime in nanoseconds
	static Hashtable<String, Integer> matrix;	//memoization matrix

	/**
	 * Prints the information requested as specified in the directions
	 *
	 * @param filename
	 * @throws IOException
	 */
	static void printStuff(String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write(String.valueOf(solution));
		writer.newLine();
		writer.write(String.valueOf(time) + " nanoseconds");
		writer.close();
	}

	/**
	 *
	 * @param filename
	 * @return String representation of first line in file
	 *
	 *         Accepts a string as a filename, opens that file, reads the first
	 *         line of the file, then returns it.
	 */
	static String readInFile(String filename) {
		String line = null;
		try {
			FileReader fileReader = new FileReader(filename);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			line = bufferedReader.readLine();

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filename + "'");

		}
		return line.trim();
	}

	/**
	 * Recursively solves LCS using a hash table
	 * Hash table is accessed with a key that is the concatenation of strings a and b
	 * Values in the hash table are the solutions to the recursive calls
	 * @param a		first string
	 * @param b		second string
	 * @return		solution string
	 */
	public static int solve(String a, String b) {
		//System.out.println("CAlled");
		int i = a.length() - 1;
		int j = b.length() - 1;

		if (i < 0 || j < 0){	//Base case, one of the strings is exhausted
			return 0;			//returns 0
		}
		//substring returns the length of the substring starting at the given index
		if (a.substring(i).equals(b.substring(j))){	//if the two substrings are equal
			//solve the problem starting from where the two strings are done being equal
			//System.out.println("Equal	["+i+","+j+"]");

			//if(matrix[i][j]>=0)//a.substring(i).length()){
			//matrix[i][j] = a.substring(i).length();
			//	return matrix[i][j] + a.substring(i).length();
			//}
			if(matrix.get(a.substring(0, i)+b.substring(0, j))!=null)
				return matrix.get(a.substring(0, i)+b.substring(0, j))+a.substring(i).length();
			else
				return solve(a.substring(0, i), b.substring(0, j)) + a.substring(i).length();
		}
		else {
			//System.out.println("~Equal	["+i+","+j+"]");
			int x;
			int y;

			if(matrix.get(a+b.substring(0, j))!=null)
				x = matrix.get(a+b.substring(0, j));
			else{
				x = solve(a, b.substring(0, j));
				matrix.put(a+b.substring(0, j), x);
			}

			if(matrix.get(a.substring(0, i)+b)!=null)
				y = matrix.get(a.substring(0, i)+b);
			else{
				y = solve(a.substring(0, i), b);
				matrix.put(a.substring(0, i)+b, y);
			}
			//if(matrix[i][j]>=0)
			//	y = matrix[i][j];
			//else
			if (x > y){
				//matrix[i][j] = x;
				return x;	//if x has a better solution, return it
			}
			//matrix[i][j] = y;
			return y;	//else if y has a better solution, return that
		}
	}

	public static void main(String[] args) throws IOException {
		String x = readInFile(args[0]);
		String y = readInFile(args[1]);
		long oldTime = System.nanoTime(); // start timer

		//String x = "mississippi"; //debug code
		//String y = "sissy"; //debug code

		int sizeX = x.length();
		int sizeY = y.length();

		matrix = new Hashtable<String, Integer>();

		solution = solve(x, y);
		// gets the time it took for the algorithm to finish
		time = System.nanoTime() - oldTime;
		printStuff(args[2]);
		//printStuff("ham.txt"); //debug code
	}
}
