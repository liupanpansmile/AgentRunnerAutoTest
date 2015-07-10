package oneapm.synthetic.agent.test.config ;



import java.io.File;
import java.io.FileInputStream;
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
}

