import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class program2 {

	static int solution;
	static long time; // runtime in nanoseconds

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
	 * Recursively solves LCS
	 * @param a		first string
	 * @param b		second string
	 * @return		solution string
	 */
	public static int solve(String a, String b) {
	//	System.out.println("here " + a + ", " + b);
		int i = a.length() - 1;
		int j = b.length() - 1;

		if (i < 0 || j < 0)	//Base case, one of the strings is exhausted
			return 0;	//returns empty string

		//substring returns the substring starting at the given index
		if (a.substring(i).equals(b.substring(j)))	//if the two substrings are equal
			//solve the problem starting from where the two strings are done being equal
			return solve(a.substring(0, i), b.substring(0, j)) + a.substring(i).length();
		else {
			int x = solve(a, b.substring(0, j));
			int  y = solve(a.substring(0, i), b);
			if (x > y)
				return x;	//if x has a better solution, return it
			return y;	//else if y has a better solution, return that
		}
	}

	public static void main(String[] args) throws IOException {
		String x = readInFile(args[0]);
		String y = readInFile(args[1]);
		long oldTime = System.nanoTime(); // start timer

		//String x = "mississippi"; //debug code
		//String y = "sissy"; //debug code

		solution = solve(x, y);
		// gets the time it took for the algorithm to finish
		time = System.nanoTime() - oldTime;
		printStuff(args[2]);
		//printStuff("ham.txt"); //debug code
	}
}
