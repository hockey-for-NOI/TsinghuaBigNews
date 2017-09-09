/*
num  name
1    科技
2    教育
3    军事
4    国内
5    社会
6    文化
7    汽车
8    国际
9    体育
10   财经
11   健康
12   娱乐
 */

import java.util.HashMap;
import java.util.Map;

public class Category {
	private static final int categoryNumber = 12;
	private static final String name[] = {"", "科技", "教育", "军事", "国内", "社会", "文化", "汽车", "国际", "体育", "财经", "健康", "娱乐"};
	private static final Map<String, Integer> num = new HashMap<String, Integer>();
	static {
		for (int i = 1; i <= categoryNumber; ++i)
			num.put(name[i], i);
	}
	
	
	//1 <= num <= 12
	public static String getName(int num) {
		return name[num];
	}
	
	public static int getNum(String name) {
		return num.get(name);
	}
}
