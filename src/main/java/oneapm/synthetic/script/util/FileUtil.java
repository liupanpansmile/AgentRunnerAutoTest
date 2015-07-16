package oneapm.synthetic.script.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {

	// write script to file
	public static void writeFile(String filename, String content) {
		FileWriter file = null;
		try {
			file = new FileWriter(filename);
			file.write(content + "\r\n\r\n");
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				if (file != null) {
					file.flush();
					file.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// read template script
	public static String readFile(String path) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
