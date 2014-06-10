//Source file: e:\\java\\zt\\platform\\form\\component\\DbBoolean.java

package pub.platform.form.component;

import pub.platform.form.config.ElementBean;

/**
 *  Boolean���͵ĳ��������ʵ������RadioButton�����ȡֵ�� 0-�� 1-��
 *
 *@author     ���滻
 *@created    2003��10��10��
 *@version    1.0
 */
public class DbBoolean extends AbstractFormComponent {

    /**
     *  Constructor for the DbBoolean object
     *
     *@param  element  Description of the Parameter
     */
    public DbBoolean(ElementBean element) {
        super(element);
    }
    /**
     *  �γ�
     *  <td class="page_form_title_td">
     *
     *  </td>
     *
     *  <td class="page_form_td">
     *    <input type="radio" name="" value="1" ����>��</input> <input type="radio"
     *    name="" value="0" ����>��</input>
     *  </td>
     *  ���ַ���
     *
     *@return     String
     *@roseuid    3F73AAD902D6
     */
    public String toHTML() {
        String value = getValues()[0];
        String truestr = " ";
        String falsestr = " ";
        if ( value.equals("0") ) {
            falsestr = " checked ";
        } else {
            truestr = " checked ";
        }
        String componentStr = "<input type=\"radio\" name=\"" + element.getName()
                + "\" value=\"1\" class=\""+CSS_PAGE_FORM_RADIO+"\""+truestr+otherStr()+">��</input>"+element.getMiddleStr()+"<input type=\"radio\" name=\"" +
                element.getName()
                + "\" value=\"0\" class=\""+CSS_PAGE_FORM_RADIO+"\""+falsestr+otherStr()+">��</input>";
        return getHeader()+componentStr+GetFooter();
    }

}
