package com.volshell.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
	public static void copyFile(File from, File to) {
		FileChannel in = null;
		FileChannel out = null;
		try {
			in = new FileInputStream(from).getChannel();
			out = new FileOutputStream(to).getChannel();

			in.transferTo(0, in.size(), out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
