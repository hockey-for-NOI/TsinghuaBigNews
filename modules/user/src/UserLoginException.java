public	class	UseeLoginException extends CheckedException
{
	public UserLoginException()
	{
		super("No such Username.");
	}

	public UserLoginException(int dummy)
	{
		super("Password mismatch.");
	}
}
