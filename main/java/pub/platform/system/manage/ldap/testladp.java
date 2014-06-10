package pub.platform.system.manage.ldap;


import javax.naming.*;
import javax.naming.directory.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class testladp {
    static int COMMON_PORT = 389;
    static int SSL_PORT    = 636;
    /// �û����Զ����־
    static int ADS_UF_SCRIPT = 0X0001;                           /// ��¼�ű���־�����ͨ�� ADSI LDAP ���ж���д����ʱ���ñ�־ʧЧ�����ͨ�� ADSI WINNT���ñ�־Ϊֻ����
    static int ADS_UF_ACCOUNTDISABLE = 0X0002;                   /// �û��ʺŽ��ñ�־
    static int ADS_UF_HOMEDIR_REQUIRED = 0X0008;                 /// ���ļ��б�־
    static int ADS_UF_LOCKOUT = 0X0010;                          /// ���ڱ�־
    static int ADS_UF_PASSWD_NOTREQD = 0X0020;                   /// �û����벻�Ǳ����
    static int ADS_UF_PASSWD_CANT_CHANGE = 0X0040;               /// ���벻�ܸ��ı�־
    static int ADS_UF_ENCRYPTED_TEXT_PASSWORD_ALLOWED = 0X0080;  /// ʹ�ÿ���ļ��ܱ�������
    static int ADS_UF_TEMP_DUPLICATE_ACCOUNT = 0X0100;           /// �����ʺű�־
    static int ADS_UF_NORMAL_ACCOUNT = 0X0200;                   /// ��ͨ�û���Ĭ���ʺ�����
    static int ADS_UF_INTERDOMAIN_TRUST_ACCOUNT = 0X0800;        /// ����������ʺű�־
    static int ADS_UF_WORKSTATION_TRUST_ACCOUNT = 0x1000;        /// ����վ�����ʺű�־
    static int ADS_UF_SERVER_TRUST_ACCOUNT = 0X2000;             /// �����������ʺű�־
    static int ADS_UF_DONT_EXPIRE_PASSWD = 0X10000;              /// �����������ڱ�־
    static int ADS_UF_MNS_LOGON_ACCOUNT = 0X20000;               /// MNS �ʺű�־
    static int ADS_UF_SMARTCARD_REQUIRED = 0X40000;              /// ����ʽ��¼����ʹ�����ܿ�
    static int ADS_UF_TRUSTED_FOR_DELEGATION = 0X80000;          /// �����øñ�־ʱ�������ʺţ��û��������ʺţ���ͨ�� Kerberos ί������
    static int ADS_UF_NOT_DELEGATED = 0X100000;                  /// �����øñ�־ʱ����ʹ�����ʺ���ͨ�� Kerberos ί�����εģ������ʺŲ��ܱ�ί��
    static int ADS_UF_USE_DES_KEY_ONLY = 0X200000;               /// ���ʺ���Ҫ DES ��������
    static int ADS_UF_DONT_REQUIRE_PREAUTH = 0X4000000;          /// ��Ҫ���� Kerberos Ԥ�����֤
    static int ADS_UF_PASSWORD_EXPIRED = 0X800000;               /// �û�������ڱ�־
    static int ADS_UF_TRUSTED_TO_AUTHENTICATE_FOR_DELEGATION = 0X1000000;/// �û��ʺſ�ί�б�־

    public testladp() {
    }
    public static void main(String[] argv) {
        int flag =1;
        Hashtable env = new Hashtable();
        try {
            env.put(InitialContext.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(InitialContext.PROVIDER_URL, "ldap://192.168.4.28:389");
            env.put(InitialContext.SECURITY_AUTHENTICATION,"simple");

                env.put(InitialContext.SECURITY_CREDENTIALS, "oaserver");
                env.put(InitialContext.SECURITY_PRINCIPAL,
                        "administrator@weboa.com");

//            env.put(InitialContext.SECURITY_PROTOCOL,"ssl");
            //env.put(InitialContext.REFERRAL,"ignore");
            DirContext ctx = new InitialDirContext(env);

//            Attributes grpctx = ctx.getAttributes("cn=fff,ou=cims,dc=ztserver,dc=zhongtian,dc=biz");
//            System.out.println(grpctx);
//            Attribute at = grpctx.get("unicodePwd");
//            System.out.println(at);
//            byte [] encPassword = (byte[])at.get();
//            String encStrPassword = new String(encPassword);
//
//            System.out.println(encStrPassword);
//
//            PasswordHandler pHandler = PasswordHandler.getInstance();
//
//            System.out.println( pHandler.verify(encStrPassword, "therealpassword") );




            if ( 1==1 ){
                SearchControls sc = new SearchControls();
                sc.setSearchScope(sc.SUBTREE_SCOPE);

                NamingEnumeration ne = ctx.search("cn=users,dc=weboa,dc=com","cn=820005",sc);

                while ( ne.hasMore() ) {
                    System.out.println("================================");
                    SearchResult sr = (SearchResult)ne.next();
                    Attributes as = sr.getAttributes();

                    NamingEnumeration nes = as.getAll();
                    while ( nes.hasMore() ) {
                        Attribute a = (Attribute) nes.next();
                        if (a.getID().toLowerCase().equals("userpassword") ) {
                             System.out.println("eeeeeeeeeeeeeeeeeeeeee");

                            //System.out.println(PasswordHandler.getInstance().verify(new String((byte[])a.get(0)),"123456"));
                        }
                        System.out.println(a.getID()+"="+a.get(0));
                    }
                    System.out.println("*******************************");
                }

            }
            if ( flag == 1 ) {

//            ctx.destroySubcontext("ou=cims,dc=zhongtian,dc=com");
                 System.out.println("================================");
//
//            try {
//                Thread.sleep(10000);
//            } catch ( Exception e ) {
//
//            }


//            BasicAttributes grpa = new BasicAttributes();
//            BasicAttribute a1 = new BasicAttribute("objectClass");
//            a1.add("top");
//            a1.add("organizationalUnit");
//            grpa.put(a1);
//            grpa.put(new BasicAttribute("ou", ("cims")));
//
//            ctx.createSubcontext("ou=cims,dc=ztserver,dc=zhongtian,dc=biz",grpa);

//                BasicAttributes tmpa = new BasicAttributes();
                 //  BasicAttribute a = new BasicAttribute("objectClass");
//            a.add("top");
//            a.add("person");
//            a.add("organizationalPerson");
//                a.add("user");
//                tmpa.put(a);
//                tmpa.put(new BasicAttribute("cn", ("helloworld")));
//                tmpa.put(new BasicAttribute("sAMAccountName", "helloworld"));
//                tmpa.put(new BasicAttribute("sn", "helloworld"));
//                tmpa.put(new BasicAttribute("userPrincipalName",
//                                            "helloworld@ztserver.zhongtian.biz"));
//               // String encPwd = PasswordHandler.getInstance().generateDigest("123456",null,"SHA");
//                System.out.println(encPwd);
//                tmpa.put(new BasicAttribute("userPassword", encPwd));
//
//                tmpa.put(new BasicAttribute("displayName", "tttt"));
//                tmpa.put(new BasicAttribute("userAccountControl", "" + (ADS_UF_NORMAL_ACCOUNT|ADS_UF_DONT_EXPIRE_PASSWD|ADS_UF_ENCRYPTED_TEXT_PASSWORD_ALLOWED)));
//
//
//                ctx.createSubcontext(
//                    "cn=helloworld,ou=cims,dc=ztserver,dc=zhongtian,dc=biz", tmpa);
//
//                ModificationItem modificationItem[] = new ModificationItem[2];
//                modificationItem[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
//                                                           new BasicAttribute("displayName", new String("123456")));
//                modificationItem[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
//                                                           new BasicAttribute("homePhone", new String("3057175")));
//                ctx.modifyAttributes("cn=helloworld,ou=cims,dc=ztserver,dc=zhongtian,dc=biz", modificationItem);
//            }
//            else {
//                BasicAttributes atrs = new BasicAttributes();
//                String newVal = new String("\""+ "123456"+ "\"");
//                byte _bytes[] = newVal.getBytes("Unicode");
//                byte bytes[] = new byte[_bytes.length-2];
//                System.arraycopy(_bytes, 2, bytes, 0, _bytes.length-2);
//                BasicAttribute attribute = new BasicAttribute("unicodePwd");
//                attribute.add((byte[])bytes);
//                atrs.put(attribute);
//                ctx.modifyAttributes("cn=helloworld,ou=cims,dc=ztserver,dc=zhongtian,dc=biz", DirContext.REPLACE_ATTRIBUTE , atrs);
//            }
            }
            ctx.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void print(DirContext dc) {



    }

}
