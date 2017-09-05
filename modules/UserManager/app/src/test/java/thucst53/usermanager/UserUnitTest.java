package thucst53.usermanager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserUnitTest {
    @Test
    public void user_unittest() throws Exception {
        User.deleteAll(User.class);

        User.register("__test__user__1__", "__test__passwd__1__");

        boolean flag = false;

        try {
            User.register("__test__user__1__", "__test__passwd__1__");
        }
        catch (UserRegisterException e) {
            assertEquals(e.getMessage().compareTo("Duplicate Username."), 0);
            flag = true;
        }
        finally {
            assertEquals(flag, true);
        }

        User.register("__test__user__2__", "__test__passwd__2__");

        flag = false;
        try {
            User.register("__test__user__E__", "sh");
        }
        catch (UserRegisterException e) {
            assertEquals(e.getMessage().compareTo("Password too weak."), 0);
            flag = true;
        }
        finally {
            assertEquals(flag, true);
        }

        flag = false;
        try {
            User.login("__unknown__user__1__", "__test__passwd__1__");
        }
        catch (UserLoginException e) {
            assertEquals(e.getMessage().compareTo("No such Username."), 0);
            flag = true;
        }
        finally {
            assertEquals(flag, true);
        }

        User u = User.login("__test__user__1__", "__test__passwd__1__");
        assertEquals(u.getName().compareTo("__test__user__1__"), 0);

        flag = false;
        try {
            User.login("__test__user__2__", "__test__passwd__1__");
        }
        catch (UserLoginException e) {
            assertEquals(e.getMessage().compareTo("Password mismatch."), 0);
            flag = true;
        }
        finally {
            assertEquals(flag, true);
        }

        flag = false;
        try {
            u.changePassword("__wrong__old__passwd__", "__test__new__passwd__", "__test__new__passwd__");
        }
        catch (UserChangePasswordException e) {
            assertEquals(e.getMessage().compareTo("Old password mismatch."), 0);
            flag = true;
        }
        finally {
            assertEquals(flag, true);
        }

        flag = false;
        try {
            u.changePassword("__test__passwd__1__", "__test__new__passwd__", "__test__NEW__passwd__");
        }
        catch (UserChangePasswordException e) {
            assertEquals(e.getMessage().compareTo("Confirm password mismatch."), 0);
            flag = true;
        }
        finally {
            assertEquals(flag, true);
        }

        flag = false;
        try {
            u.changePassword("__test__passwd__1__", "sh", "sh");
        }
        catch (UserChangePasswordException e) {
            assertEquals(e.getMessage().compareTo("Password too weak."), 0);
            flag = true;
        }
        finally {
            assertEquals(flag, true);
        }

        u.changePassword("__test__passwd__1__", "__test__new__passwd__", "__test__new__passwd__");

        flag = false;
        try {
            User.login("__test__user__1__", "__test__passwd__1__");
        }
        catch (UserLoginException e) {
            assertEquals(e.getMessage().compareTo("Password mismatch."), 0);
            flag = true;
        }
        finally {
            assertEquals(flag, true);
        }

        u = User.login("__test__user__1__", "__test__new__passwd__");
        assertEquals(u.getName().compareTo("__test__user__1__"), 0);

        User.deleteAll(User.class);
    }
}