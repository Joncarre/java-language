package files;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

/**
 * Interface ICustomReadFile
 * @author J. Carrero
 */
public interface ICustomReadFile {
	/** Closes the read file */
	public abstract void closeReadFile(CustomReadFile file);
	/** Reads a vector from the read file 
	 * @throws IOException */
	public abstract Vector<Double> readVector(Scanner in) throws IOException;
	/** Reads a double of read file */
	public abstract double readDouble(Scanner in);
	/** Reads an array from the read file */
	public abstract int[] readArray(Scanner in, int sizeArray);
	/** Reads an int from a read file */
	int readInt(Scanner in);
}