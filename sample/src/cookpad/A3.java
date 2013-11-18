package cookpad;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class A3 {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		StringBuffer sb = new StringBuffer();

		while (in.hasNext()) {
			sb.append(in.nextLine());
		}
		// パーサオブジェクトを作成します。
		HTMLParser parser = new HTMLParser(sb.toString());

		// タグを順番に処理します。
		while (parser.hasNext()) {
			Tag tag = parser.next();
			System.out.println(tag);
		}
	}

	private static class HTMLParser extends A3 {
		/**
		 * タグの正規表現
		 */
		private Pattern tagPattern = Pattern
				.compile("(<([^ >]+)([^>]*)>)([^<]*)");
		private Matcher matcher;
		private Tag tag;

		/**
		 * コンストラクタ
		 * 
		 * @param src
		 *            HTMLソース
		 */
		public HTMLParser(String src) {
			matcher = tagPattern.matcher(src);
		}

		/**
		 * 次のHTMLタグがあるかどうかを検査します。
		 * 
		 * @return 存在する場合はtrue
		 */
		public boolean hasNext() {
			boolean found = matcher.find();
			if (found) {
				tag = new Tag(matcher.group(1), matcher.group(2),
						matcher.group(3), matcher.group(4));
			}
			return found;
		}

		/**
		 * 次のHTMLタグを返します。
		 * 
		 * @return タグをあらわすオブジェクト
		 */
		public Tag next() {
			return tag;
		}
	}

	class Tag {
		private String tagName;
		private String tagText;

		public Tag(String tagStr, String tagName, String tagAttribute,
				String tagText) {
			this.tagName = tagName;
			this.tagText = tagText;
		}

		@Override
		public String toString() {
			return "タグ:[" + tagName + "], テキスト: [" + tagText + "]";
		}
	}
}
