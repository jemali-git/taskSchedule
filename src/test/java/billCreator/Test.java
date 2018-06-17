package billCreator;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;

public class Test {

	public Test() {

		System.out.println(Test.class.getResource("/").getFile());
		// new File(a);
	}

	public static void main(String[] args) {

		String aString = "(a+b-c)/(d)";
		Map<String, Integer> map = new HashMap<>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		map.put("d", 4);
		Pattern pattern = Pattern.compile("[a-zA-Z_]+");
		Matcher matcher = pattern.matcher(aString);
		StringBuilder stringBuilder = new StringBuilder();
		int start = 0;
		while (matcher.find()) {
			stringBuilder.append(aString.substring(start, matcher.end()).replace(matcher.group(),
					map.get(matcher.group()).toString()));
			start = matcher.end();
		}
		stringBuilder.append(aString.substring(start, aString.length()));
		System.out.println(stringBuilder);

		// new Test();

		// JODconverter
		// Desktop desktop = Desktop.getDesktop();
		// desktop.open(file);
		// Scanner scanner = new Scanner(
		// new FileReader("C:\\Users\\nadhem\\Desktop\\New folder (2)\\modèle_Cahier des
		// charges_PFE - Copy.rtf"));
		// StringBuilder stringBuilder = new StringBuilder();
		// while (scanner.hasNext()) {
		// // System.out.println(scanner.nextLine());
		// stringBuilder.append(scanner.nextLine());
		// }
		// System.out.println(stringBuilder);

		// ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		// ScriptEngine scriptEngine =
		// scriptEngineManager.getEngineByName("JavaScript");
		// try {
		// System.out.println(scriptEngine.eval(" a=5;"));
		// } catch (ScriptException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		System.out.println("done");
	}
}
