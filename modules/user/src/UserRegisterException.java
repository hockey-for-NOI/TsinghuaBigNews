public	class	UserRegisterException extends CheckedException
{
	public UserRegisterException()
	{
		super("Duplicate Username.");
	}

	public UserRegisterException(int dummy)
	{
		super("Password too weak.");
	}
}
