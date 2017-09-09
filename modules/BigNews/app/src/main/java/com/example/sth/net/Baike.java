import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//if the keyword can't be found in baike.baidu.com, it will return null
public class Baike {
	public static ArrayList<String> jumpToBaike(ArrayList<String> keywords) throws MalformedURLException, IOException {
		ArrayList<String> output = new ArrayList<String>();
		output.clear();
		URL url;
		String s;
		for (int i = 0; i < keywords.size(); ++i) {
			url = new URL("https://baike.baidu.com/item/" + keywords.get(i));
			BufferedReader in = new BufferedReader(new
					InputStreamReader(url.openStream()));
			s = in.readLine();
			s = in.readLine();
			if (s.contains("OK"))
				output.add("https://baike.baidu.com/item/" + keywords.get(i));
			else
				output.add(null);
		}
		return output;
	}
}
