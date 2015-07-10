/**
 * 
 */
package oneapm.synthetic.agent.test.result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import oneapm.synthetic.agent.test.util.MyEntry;

import org.apache.logging.log4j.Logger;



/**
 * @author Lpp 文件处理
 *
 */
public class GenerateResultReport {

	static Logger logger = org.apache.logging.log4j.LogManager.getLogger(GenerateResultReport.class.getName());
	
	static final int ERROR_RESULT_FORMAT = -1 ;

	static class Holder {
		static GenerateResultReport instance = new GenerateResultReport();
	}

	public static GenerateResultReport instance() {
		return Holder.instance;
	}

	private GenerateResultReport() {
	}

	/**
	 * 	目的：读取resultPath下的result ,生成测试报告，并存放在resultReportPath中
	 * 
	 * @param filepath
	 *           
	 * @return Map  <id,filename>
	 */
	public void gererateStatisticsReport(String resultPath,String resultReportPath){
		Map<Integer,String> resultMap = readFileAndRtnResult(resultPath) ;
		vertifyResult(resultMap, resultReportPath);
	}
	
	
	/**
	 * 	目的：读filePath下的所有result文件，统计<id,count>的map
	 * 
	 * @param filepath
	 *           
	 * @return Map  <id,filename>
	 */
	private Map<Integer, String> readFileAndRtnResult(String filepath) {

		File file = new File(filepath);
		if (!file.exists() || !file.isDirectory()) {
			logger.error("failed to find local job folder: " + filepath);
			return null;
		}
		
		File[] filelist = file.listFiles();
		Map<Integer, String> map = new HashMap<Integer, String>(); //key-->id  value-->filename

		for (int i = 0; i < filelist.length; i++) {
			File readFile = filelist[i];
			Map.Entry<Integer, String> entry = null;
			
			if (readFile.getName().toLowerCase().endsWith(".txt")) {
				entry = readFileContent(readFile);
				int key = entry.getKey();
				if (map.containsKey(key)) {
					map.put(key, map.get(key) + " " + entry.getValue());
					
				} else {
					map.put(key, entry.getValue());
					
				}
			}
		}
		return map;
	}

	

	/**
	 * 目的：测试是否有单个脚本被执行多次的情况
	 *  方法：依次读取result文件夹下的xxx.txt,保存id到hashMap中，遍历hashMap中key中value的值
	 * 
	 * @param map resultReportPath
	 *           
	 * @return void
	 */
	private  void vertifyResult(Map<Integer,String> map,String resultReportPath){
		if(map == null)
		{
			return ;
		}
		
		int totalResultCount = 0;				//the total result count
		int multiResultCount = 0 ;				//the same job and the different result 
		int errorFormatResultCount = 0 ;		//may contain xml、empty format 
		int correctResultCount = 0 ;			
		
		Map<Integer,String> multiResultMap = new HashMap<Integer,String>() ;
		StringBuilder errorResultFile = new StringBuilder() ;
		
		for(Map.Entry<Integer, String> entry : map.entrySet()){
			if(entry.getKey() == ERROR_RESULT_FORMAT){
				errorFormatResultCount += entry.getValue().split(" ").length ;
				totalResultCount += errorFormatResultCount ;
				errorResultFile.append(entry.getValue() + "  ") ;
				
			}else {
				int count = entry.getValue().split(" ").length ;
				if(count  == 1){		//correct result
					++correctResultCount ;
					++totalResultCount ;
					
				}else{					//the same job and the different result 
					multiResultCount += count ;
					System.out.println("multiResultCount = " + multiResultCount);
					totalResultCount += count ;
					multiResultMap.put(entry.getKey(),entry.getValue() ) ;
					
				}
			}
		}
		
		/*********************************************生成report***********************************************/
		FileWriter writer = null;
		DecimalFormat    df   = new DecimalFormat("######0.00");   

        try {
        	File file =  new File(resultReportPath) ;
        	if(!file.exists()){
        		file.createNewFile() ;
        	}
        	writer = new FileWriter(file);
        	writer.write("---------------------------------report as follows ------------------------------\n");
            writer.write("total result: " + totalResultCount + "\n");
            writer.write("XML Result: " + errorFormatResultCount + "  Rate: " + df.format(errorFormatResultCount/(totalResultCount*1.0)*100) + " %\n");
            writer.write("Multi Result: " + multiResultCount + " Rate: " +df.format( multiResultCount/(totalResultCount*1.0)*100) + "%\n");
            writer.write("Correct Result: " + correctResultCount + " Rate: " + df.format(correctResultCount/(totalResultCount*1.0)*100) + " %\n");
            
            if(multiResultCount != 0){
            	writer.write("\n\n------------------------------------------");
            	writer.write("\nMulti Result As Follows\n");
            	for(Map.Entry<Integer, String> entry:multiResultMap.entrySet()){
            		writer.write("id:" + entry.getKey() + " filename: " + entry.getValue()+"\n");
            	}
            }
    		
    		if(errorFormatResultCount != 0){
    			writer.write("\n\n------------------------------------------");
    			writer.write("\nXML result filename: " + errorResultFile.toString()+"\n");
    		}
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
            try {
            	if(writer != null){
            		writer.flush();
            		writer.close();
            	}
			} catch (IOException e) {
				e.printStackTrace();
			}
        } 
		
	} 
	
	
	/**
	 * read file and id value
	 * 
	 * @param filepath
	 *           
	 * @return map.Entry --> <id,filename>
	 */
	private static Map.Entry<Integer, String> readFileContent(File file) {
		BufferedReader bufferedReader = null;
		MyEntry entry = null;

		if (file.exists()) {
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
				String line = null;
				line = bufferedReader.readLine();
				if(line == null){
					entry = new MyEntry<Integer, String>(ERROR_RESULT_FORMAT, file.getName());
					
				}else if (line.contains("<") || line.contains(">")) { // in some circumstances,xml format may occur
					entry = new MyEntry<Integer, String>(ERROR_RESULT_FORMAT, file.getName());
					
				}else if (line.startsWith("{\"id\":")) {
					int startIndex = line.indexOf(':');
					int endIndex = line.indexOf(',');
					String idValue = line.substring(startIndex + 1, endIndex);
					entry = new MyEntry<Integer, String>(
					Integer.parseInt(idValue), file.getName());
					
				}else {
					entry = new MyEntry<Integer, String>(ERROR_RESULT_FORMAT, "");
					
				}
			} catch (FileNotFoundException e) {
				logger.error("The exception is occurred when file:" + file + " is not exist", e);
				
			} catch (IOException e) {
				e.printStackTrace();
				
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
						bufferedReader = null;
						
					} catch (IOException e) {
						logger.error("The exception is occurred when closing bufferedReader",e);
						
					}
				}
			}
		}
		return entry;
	}

}
