package pub.platform.advance.utils;

import java.util.*;
import java.io.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

import pub.platform.form.config.*;

public class MailSendHelper {

	public String host;
	public String sendMailAddress;
	public String passwd;

	public String subject;
	public String message;
	public List receiverList;
	public List appendlist;

	/**
	 * ��ʼ������
	 * @param subject �ʼ�����
	 * @param message �ʼ��ı�����
	 * @param receiverList �������ʼ���ַ�б�
	 * @param appendlist �����ļ����б�
	 */
	public MailSendHelper(String subject, String message, List receiverList, List appendlist){
		this.subject = subject;
		this.message = message;
		this.receiverList = receiverList;
		this.appendlist = appendlist;
	}

	public void send()throws Exception{
		if(this.host == null){
			this.host = EnumerationType.getEnumName("MAIL", "mail.smtp.host");
		}
		if(this.sendMailAddress == null){
			this.sendMailAddress = EnumerationType.getEnumName("MAIL", "mail.sender");
		}
		if(this.passwd == null){
			this.passwd = EnumerationType.getEnumName("MAIL", "mail.password");
		}

        // ������֤ʵ��
        AuthenticatorImpl auth
                = new AuthenticatorImpl(this.sendMailAddress, this.passwd);

        // ��������
        Properties props = System.getProperties();
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.auth", "true");

        // �����Ự
        Session session = Session.getInstance(props, auth);
        session.setDebug(false);

        // ������Ϣ
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(this.sendMailAddress));

        // ���ý��ܷ�
        int count = receiverList.size();
        InternetAddress[] address = new InternetAddress[count];
        for (int i = 0; i < count; i++) {
            address[i] = new InternetAddress((String)receiverList.get(i));
        }
        msg.setRecipients(Message.RecipientType.TO, address);

        // ��������
        msg.setSubject(this.subject);

        // ���ñ���
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setText(this.message);

        // ���������Ϣ��
        Multipart mp = new MimeMultipart();
        // ��ӱ���
        mp.addBodyPart(mbp);

        // ������и���
        if(appendlist != null){
        	for (int i = 0; i < appendlist.size(); i++) {
        		// ����������Ϣ��
        		MimeBodyPart append = new MimeBodyPart();
        		String fileName = (String)this.appendlist.get(i);
        		File file = new File(fileName);
        		FileDataSource fds = new FileDataSource(file);
        		append.setDataHandler(new DataHandler(fds));
        		append.setFileName(MimeUtility.encodeWord(fileName));
        		mp.addBodyPart(append);
        	}
        }
        // ������Ϣ����
        msg.setContent(mp);

        // ����ʱ��
        msg.setSentDate(new Date());

        // �����ʼ�
        Transport.send(msg);

    }

}
