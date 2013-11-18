package cookpad;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

public class A1 {

	public static void main(String args[]) throws Exception {
		Scanner in = new Scanner(System.in);
		List<String> outStrList = new ArrayList<String>();

		while (in.hasNext()) {
			String buf = in.nextLine();
			outStrList.add(getString(buf));
		}
		for (String outStr : outStrList) {
			System.out.println(outStr);
		}
	}

	public static String getString(String line) {
		String str = "";
		LinkedHashMap<String, Integer> strMap = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < line.length(); i++) {
			String tmpStr = line.substring(i, i + 1);
			if (!strMap.containsKey(tmpStr)) {
				strMap.put(tmpStr, 1);
			} else {
				strMap.remove(tmpStr);
			}
		}
		for (Entry<String, Integer> e : strMap.entrySet()) {
			str = e.getKey();
			break;
		}
		return str;
	}
}
