package app.solver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Z3Runner {
	public static List<String> runZ3(String filename) 
			throws IOException {
		List<String> result = new ArrayList<String>();
		String pathToZ3 = "z3\\bin\\z3.exe";
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", pathToZ3 + " -smt2 -st -T:1 " + filename);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
			result.add(line);
		}
		return result;
	}
	
	public static List<String> runZ3(String filename, int time) 
			throws IOException {
		List<String> result = new ArrayList<String>();
		String pathToZ3 = "z3\\bin\\z3.exe";
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", pathToZ3 + " -smt2 -st -T:" + time + filename);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
			result.add(line);
		}
		return result;
	}
}
