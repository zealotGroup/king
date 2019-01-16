package group.zealot.king.base.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class SoapUtil {
    protected static Logger logger = LoggerFactory.getLogger(SoapUtil.class);

    /**
     * 发送soap请求
     *
     * @param soapMessage
     * @param url
     * @return
     * @throws IOException
     * @throws SOAPException
     */
    public static String requestSOAP(SOAPMessage soapMessage, String url) throws IOException, SOAPException, DocumentException, TransformerException {
        //打印请求信息
        printSOAPMessage(soapMessage);
        //发送请求
        SOAPConnection soapConnection = null;
        try {
            soapConnection = getSoapConnection();
            SOAPMessage soapResponse = soapConnection.call(soapMessage, url);
            //输出SOAP对象字符串格式
            String response = formartToString(soapResponse);
            //xml格式化
            String responeXml = formatXml(response);
            //打印xml
            logger.info(responeXml);
            return responeXml;
        } finally {
            try {
                if (soapConnection != null) {
                    soapConnection.close();
                }
            } catch (SOAPException e) {
                logger.error("soapConnection.close()异常", e);
            }
        }
    }

    /**
     * 生成soap connection
     *
     * @return
     * @throws SOAPException
     */
    private static SOAPConnection getSoapConnection() throws SOAPException {
        return SOAPConnectionFactory.newInstance().createConnection();
    }

    /**
     * 打印请求信息
     *
     * @param soapMessage
     * @throws IOException
     */
    private static void printSOAPMessage(SOAPMessage soapMessage) throws IOException, DocumentException, TransformerException, SOAPException {
        //输出SOAP对象字符串格式
        String request = formartToString(soapMessage);
        //xml格式化
        String requestXml = formatXml(request);
        //打印xml
        logger.info(requestXml);
    }

    /**
     * 把soap对象格式化为字符串
     *
     * @param soapMessage
     * @return
     */
    public static String formartToString(SOAPMessage soapMessage) throws IOException, TransformerException, SOAPException {
        String str = null;
        StringWriter sw = new StringWriter();
        try {
            SOAPPart soapPart = soapMessage.getSOAPPart();
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(new DOMSource(soapPart.getEnvelope()), new StreamResult(sw));
            sw.flush();
            str = sw.toString();
        } finally {
            sw.close();
        }
        return str;
    }

    public static String formatXml(String result) throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        StringWriter out = null;
        XMLWriter writer = null;
        try {
            StringReader in = new StringReader(result);
            Document doc = reader.read(in);
            OutputFormat formater = OutputFormat.createPrettyPrint();
            formater.setEncoding("UTF-8");
            out = new StringWriter();
            writer = new XMLWriter(out, formater);
            writer.write(doc);
        } finally {
            if (out != null) {
                out.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
        return out.toString();
    }
}
