package oneapm.synthetic.agent.test.config ;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created by liujian on 2015/6/19 10:58.
 */
public class XMLUtil {
    private static final Logger logger = LogManager.getLogger(XMLUtil.class.getName());
  

    /**
     * ��xml�ļ���ȡ����
     *
     * @param absPath  ����·��
     * @param fileName �ļ���
     * @param cls class
     * @return T
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBeanFromFile(String absPath, String fileName, Class<T> cls) throws Exception {
        String filePath = absPath + fileName;
        InputStream ins;
        try {
            ins = new FileInputStream(new File(filePath));
        } catch (Exception e) {
            throw new Exception("read File {" + filePath + "} error!", e);
        }
       
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj;
        try {
            obj = (T) xstream.fromXML(ins);
        } catch (Exception e) {
            throw new Exception("analysis XML{" + filePath + "}error!", e);
        }
        ins.close();
        return obj;
    }
    
    public static <T> void toFileFromBean(String  filename,T obj, Class<T> cls) {
    	logger.info("convert "+ cls.getName() + "to " + filename);
    	XStream xstream = new XStream() ;
    	xstream.processAnnotations(cls);
    	String xmlString = xstream.toXML(obj) ;
    	writeFile(filename, xmlString);
    }
    
    private static void writeFile(String filename,String xmlString){
    	BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(filename));
			out.write(xmlString);
		} catch (IOException e) {
			logger.error("IOException Occured" + e.getMessage());
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				logger.error("IOException Occured" + e.getMessage());
			}
		}
    }
    
}

