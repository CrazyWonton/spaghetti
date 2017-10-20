import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class program1 {
	static int table[][]; // matrix to hold integer values for matches
	static char direction[][]; // matrix to hold direction of inheritance
	static char x[]; // one of the strings to be compared
	static char y[]; // the other string to be compared
	static int sizeX; // size of x[]+1, for the number of columns in the table
						// and direction matrices
	static int sizeY; // size of y[]+1, for the number of rows in the table and
						// direction matrices
	static char[] key; // set as the smaller of the two strings, useful in
						// calculations
	static LinkedList<Character> solution; // solution to LCS
	static long time;	//runtime in nanoseconds

	/**
	 * Goes through and populates the information in the matrices based on the
	 * algorithm to determine LCS in the lecture slides
	 */
	static void populateTable() {
		for(int i=0;i<sizeX;i++)
			direction[i][0]=' ';
		for(int i=0;i<sizeY;i++)
			direction[0][i]=' ';

		// nested loops go horizontally then vertically to process the matrices
		for (int j = 1, yi = 0; j < sizeY; j++, yi++) {
			for (int i = 1, xi = 0; i < sizeX; i++, xi++) {
				if (x[xi] == y[yi]) {
					direction[i][j] = '\\';
					table[i][j] = table[i - 1][j - 1] + 1;
				} else if (table[i - 1][j] >= table[i][j - 1]) {
					direction[i][j] = '|';
					table[i][j] = table[i - 1][j];
				} else{
					direction[i][j] = '-';
					table[i][j] = table[i][j - 1];
				}
			}
		}

	}

	/**
	 * Traces back the path to determine the LCS
	 */
	static void solve() {
		solution = new LinkedList<>();
		int i = sizeY - 1;
		int j = sizeX - 1;
		boolean xBig = true;
		int index;
		if (sizeX < sizeY) {
			index = sizeX - 2;
			xBig = false;
		} else
			index = sizeY - 2;

		while (i > 0 && j > 0) {

			if (direction[j][i] == '\\') {
				solution.push(key[index]);
				i--;
				j--;
				index--;
			} else if (direction[j][i] == '-') {
				i--;
				if (xBig)
					index--;
			} else if (direction[j][i] == '|') {
				j--;
				if (!xBig)
					index--;
			}
		}

	}
	
	/**
	 * Prints the information requested as specified in the directions
	 * @param filename
	 * @throws IOException
	 */
	static void printStuff(String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

		if (sizeX < 11 && sizeY < 11) {
			// prints the matrix if each string is less than or equal to 10
			writer.write("    ");
			for (int i = 0; i < sizeX - 1; i++)
				writer.write("  " + x[i]);
			writer.newLine();
			for (int i = 0; i < sizeY; i++) {
				if (i > 0) {
					writer.write(y[i - 1] + " ");
				} else
					writer.write("  ");
				for (int j = 0; j < sizeX; j++) {
					writer.write(direction[j][i]);
					writer.write(table[j][i] + " ");
				}
				writer.newLine();
			}

			// writes out the solution string
			for (char c : solution) {
				writer.write(c);
			}
		}
		else{
			writer.write(String.valueOf(solution.size()));
		}
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

	public static void main(String[] args) throws IOException {
		x = readInFile(args[0]).toCharArray();
		y = readInFile(args[1]).toCharArray();
		long oldTime = System.nanoTime();	//start timer
		
		//x = "banpae".toCharArray();	//debug code
		//y = "apple".toCharArray();	//debug code
		sizeX = x.length + 1;
		sizeY = y.length + 1;

		if (sizeX < sizeY)
			key = x;
		else
			key = y;

		table = new int[x.length + 1][y.length + 1];
		direction = new char[x.length + 1][y.length + 1];
		
		populateTable();
		solve();
		//gets the time it took for the algorithm to finish
		time = System.nanoTime() - oldTime;
		printStuff(args[2]);
		//printStuff("ham.txt");	//debug code
	}
}
