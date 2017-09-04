public	class	User	extends	SugarRecord<User> {
	String	name;
	String	salted_passwd;

	public User() {
	}

	public User(String name, String raw_passwd)
	{
		this.name = name;
		this.salted_passwd = salted(raw_passwd);
	}

	public	static	String	salted(String str1)
	{
		StringBuffer buf;
		String	salt = "salt";
		for (int i=0; i<str1.length(); i++)
			buf.append(str1.charAt(i) ^ salt.charAt(i & 3))
		return buf.toString();
	}
}
