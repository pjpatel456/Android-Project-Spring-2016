package com.example.android.moveyourthings;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ByteReadAndWrite {

	public byte[] readbytedata(String SourceFileName, int CHUNK_SIZE)
			throws IOException {
		File willBeRead = new File(SourceFileName);
		int FILE_SIZE = (int) willBeRead.length();
		ArrayList<String> nameList = new ArrayList<String>();
		int NUMBER_OF_CHUNKS = 0;
		byte[] file_data = new byte[FILE_SIZE];
		try {
			InputStream inStream = null;
			int totalBytesRead = 0;

			try {
				inStream = new BufferedInputStream(new FileInputStream(
						willBeRead));

				while (totalBytesRead < FILE_SIZE) {
					String PART_NAME = "data" + NUMBER_OF_CHUNKS + ".bin";
					int bytesRemaining = FILE_SIZE - totalBytesRead;
					if (bytesRemaining < CHUNK_SIZE) {
						CHUNK_SIZE = bytesRemaining;
					}
					int bytesRead = inStream.read(file_data, totalBytesRead,
							CHUNK_SIZE);
					if (bytesRead > 0) {
						totalBytesRead += bytesRead;
						NUMBER_OF_CHUNKS++;
					}
				}

			} finally {
				inStream.close();

			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return file_data;
	}




}