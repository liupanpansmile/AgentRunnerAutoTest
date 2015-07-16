package oneapm.synthetic.agent.test.config ;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configuration {

    private static final Logger logger = LogManager.getLogger(Configuration.class.getName());
    private Config config;


	static class ConfigurationHolder {    
	    static Configuration instance = new Configuration();    
	  }   
	
	private Configuration(){
        readConfig();
	}
	
	public static Configuration instance(){
		return ConfigurationHolder.instance;
	}
	
    public void readConfig() {
        logger.info("---readConfig---");
        try {
        	
            config = XMLUtil.toBeanFromFile("", "config.xml", Config.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Config getConfig() {
        return config;
    }

    private void Print(String filename){
    	
    	FileInputStream inputStream = null ;
    	String text ;
    	try {
			inputStream = new FileInputStream(filename) ;
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)) ;
			try {
				while( (text = reader.readLine()) != null){
					System.out.println(text);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
  
}

