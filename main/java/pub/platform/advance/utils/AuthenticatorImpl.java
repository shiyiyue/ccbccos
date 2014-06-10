package pub.platform.advance.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * �ʼ�������֤
 * @author �����
 *
 */
public class AuthenticatorImpl extends Authenticator {

    private String username = null;
    private String userpass = null;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserpass(String userpass) {
    	this.userpass = userpass;
    }

    public AuthenticatorImpl(String username, String userpass) {
        super();
        setUsername(username);
        setUserpass(userpass);

    }

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.username, this.userpass);
    }
}
