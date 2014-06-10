/**
 * Copyright 2003 ZhongTian, Inc. All rights reserved.
 *
 * qingdao tec PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * $Id: NoAvailableResourceException.java,v 1.1 2006/05/17 09:19:31 wiserd Exp $
 * File:NoAvailableResourceException.java
 * Date Author Changes
 * March 5 2003 wangdeliang Created
 */

package pub.platform.db;

/**
 * ��ϵͳ�޿�����Դʱ�׳��������.
 *
 * @author <a href="mailto:wuyeyuan@tom.com">wuyeyuan</a>
 * @version $Revision: 1.1 $ $Date: 2006/05/17 09:19:31 $
 */

public class NoAvailableResourceException extends Exception {
    public NoAvailableResourceException() {
        super();
    }
    public NoAvailableResourceException(String msg) {
        super(msg);
    }
}