package cookpad;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

public class A2 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String str = "";

		while (in.hasNext()) {
			String buf = in.nextLine();
			str += buf;
		}

		CookPad cookpad = null;
		try {
			cookpad = new CookPad(new URL(str));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println("タイトル：" + cookpad.getTitle());
		System.out.println("材料：");
		for (String ingredient : cookpad.getIngredientList()) {
			System.out.println(ingredient);
		}
		System.out.println("手順：");
		int stepCnt = 1;
		for (String step : cookpad.getSteps()) {
			System.out.println(stepCnt + ". " + step);
			stepCnt++;
		}
//		System.out.println(cookpad.get());
	}
}

class CookPad
{
	private String charset = "UTF-8";
	private JTextArea htmlArea;

	public CookPad(URL url) {
		htmlArea = new JTextArea();
		// Webページを読み込む
		try {
			// 接続
			URLConnection uc = url.openConnection();
			// HTMLを読み込む
			BufferedInputStream bis = new BufferedInputStream(
					uc.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(bis,
					charset));
			htmlArea.setText("");// 初期化
			String line;
			while ((line = br.readLine()) != null) {
				htmlArea.append(line + "\n");
			}
		} catch (MalformedURLException ex) {
			htmlArea.setText("URLが不正です。");
			ex.printStackTrace();
		} catch (UnknownHostException ex) {
			htmlArea.setText("サイトが見つかりません。");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String get() {
		return htmlArea.getText();
	}
	public String getTitle() {
		String target = get();
		String regex = "<title>(.+)</title>";
		return extractMatchString(regex, target);
	}
	public List<String> getIngredientList() {
		String target = get();
		String regex = "<div[^>]+ingredient_name[^>]*>.*>([^<][^<]+)<.*</div>";
		return extractMatchList(regex, target);
	}
	public List<String> getSteps() {
		String target = get();
		String regex = "<p.*step_text_.*>(.+)</p>";
		return extractMatchList(regex, target);
	}
	private String extractMatchString(String regex, String target) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(target);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			throw new IllegalStateException("No match found.");
		}
	}
	private List<String> extractMatchList(String regex, String target) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(target);
		List<String> strList = new ArrayList<String>();
		if (matcher.find()) {
			strList.add(matcher.group(1));
		} else {
			throw new IllegalStateException("No match found.");
		}
		while (matcher.find()) {
			strList.add(matcher.group(1));
		}
		return strList;
	}
}