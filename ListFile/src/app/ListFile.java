package app;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ListFile {
	private RandomAccessFile file;

	public static void initialize(String listFileName) {
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(listFileName, "rws");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ListFile(String listFileName) {
		try {
			file = new RandomAccessFile(listFileName, "rws");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delete(String listFileName) {
		File chosenFile = new File(listFileName);
		chosenFile.delete();
	}

	public void close() {
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long newEntry(Entry entry) {
		long offset = 0;
		try {
			offset = file.length();
			putEntry(offset, entry);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return offset;
	}

	public Entry getEntry(long offset) {
		Entry entry = null;
		try {
			file.seek(offset);
			int size = file.readInt();
			byte[] str = new byte[size];
			for (int x = 0; x < size; x++) {
				str[x] = file.readByte();
			}
			String entryString = str.toString();
			entry = new Entry(entryString, file.readLong(), file.readLong());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entry;
	}

	public void putEntry(long offset, Entry entry) {
		try {
			file.seek(offset);
			writeEntry(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeEntry(Entry entry) {
		int length = entry.getString().length();
		try {
			file.writeInt(length);
			file.writeBytes(entry.getString());
			file.writeLong(entry.getValue());
			file.writeLong(entry.getLink());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
