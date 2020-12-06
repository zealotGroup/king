package group.zealot.king.base.util;

import com.sun.xml.internal.messaging.saaj.client.p2p.HttpSOAPConnectionFactory;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class SoapUtil {
    protected static Logger logger = LoggerFactory.getLogger(SoapUtil.class);
    private static HttpSOAPConnectionFactory httpSOAPConnectionFactory;

    static {
        try {
            httpSOAPConnectionFactory = (HttpSOAPConnectionFactory) HttpSOAPConnectionFactory.newInstance();
        } catch (SOAPException e) {
            logger.error("异常", e);
            System.exit(0);
        }
    }

    /**
     * 发送soap请求
     */
    public static String requestSOAP(SOAPMessage soapMessage, String url) throws IOException, SOAPException, DocumentException, TransformerException {
        //打印请求信息
        printSOAPMessage(soapMessage);
        //发送请求
        SOAPConnection soapConnection = null;
        try {
            soapConnection = getSoapConnection();
            SOAPMessage soapResponse = soapConnection.call(soapMessage, createURL(url));
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
     */
    private static SOAPConnection getSoapConnection() throws SOAPException {
        return httpSOAPConnectionFactory.createConnection();
    }

    /**
     * 打印请求信息
     */
    private static void printSOAPMessage(SOAPMessage soapMessage) throws IOException, DocumentException, TransformerException, SOAPException {
        //输出SOAP对象字符串格式
        String request = formartToString(soapMessage);
        //xml格式化
        String requestXml = formatXml(request);
        //打印xml
        logger.debug(requestXml);
    }

    /**
     * 把soap对象格式化为字符串
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

    private static URL createURL(String url) throws MalformedURLException {
        return new URL(new URL(url), "", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL url) throws IOException {
                URL target = new URL(url.toString());
                URLConnection connection = target.openConnection();
                // Connection settings
                connection.setConnectTimeout(EnvironmentUtil.get("timeout.soap.connect", 10000)); // 10 sec
                connection.setReadTimeout(EnvironmentUtil.get("timeout.soap.read", 60000)); // 1 min
                return (connection);
            }
        });
    }
}
