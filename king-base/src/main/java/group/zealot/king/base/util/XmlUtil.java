package group.zealot.king.base.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class XmlUtil {

    /**
     * 解析返回根节点root
     *
     * @param xml
     * @return Element
     * @throws JDOMException
     * @throws IOException
     */
    public static Element getRootElement(String xml) throws JDOMException, IOException {
        Reader in = new StringReader(xml);
        Document doc = (new SAXBuilder()).build(in);
        Element root = doc.getRootElement();
        return root;
    }
}
