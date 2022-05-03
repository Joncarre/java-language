package data;

/**
 * Interface ICustomWriteFile
 * @author J. Carrero
 *
 */
public interface ICustomWriteFile {
	/** Closes the write file */
	public abstract void closeWriteFile(CustomWriteFile file);
	/** Write to the write file */
	public abstract void writeVector(CustomWriteFile file, String text);
}

