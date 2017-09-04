public	class	User	extends	SugarRecord<User> {
	String	name;
	String	salted_passwd;

	public User() {
	}

	private User(String name, String raw_passwd)
	{
		this.name = name;
		this.salted_passwd = salted(raw_passwd);
	}

	private	static	String	salted(String str1)
	{
		StringBuffer buf;
		String	salt = "salt";
		for (int i=0; i<str1.length(); i++)
			buf.append(str1.charAt(i) ^ salt.charAt(i & 3))
		return buf.toString();
	}

	private static	boolean passwdChk(String str1)
	{
		if (str1.length() < 4) return false;
		return true;
	}

	public	static	User	register(String username, String raw_passwd) throws UserRegisterException
	{
		List<User> lu = Select.from(User.class).where(Condition.prop("name").eq(username)).list();
		if (!lu.isEmpty()) throw new UserRegisterException();

		if (!passwdChk(raw_passwd)) throw new UserRegisterException(0);

		User u = new User(username, salted(raw_passwd));
		u.save();
		return u;
	}

	public	static	User	login(String username, String raw_passwd) throws UserLoginException
	{
		List<User> lu = Select.from(User.class).where(Condition.prop("name").eq(username)).list();
		if (lu.isEmpty()) throw new UserLoginException();
		User	u = lu.get(0);
		if (u.salted_passwd.compareTo(salted(raw_passwd)) == 0) return u;
		throw new UserLoginException(0);
	}
}
