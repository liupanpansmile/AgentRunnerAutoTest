/**
 * 
 */
package oneapm.synthetic.agent.test.result;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import oneapm.synthetic.agent.runner.report.Report;
import oneapm.synthetic.agent.test.util.MyEntry;

import org.apache.logging.log4j.Logger;

/**
 * @author Lpp 文件处理
 *
 */
public class GenerateResultReport {

	static Logger logger = org.apache.logging.log4j.LogManager
			.getLogger(GenerateResultReport.class.getName());

	static final int ERROR_RESULT_FORMAT = -1;

	static class Holder {
		static GenerateResultReport instance = new GenerateResultReport();
	}

	public static GenerateResultReport instance() {
		return Holder.instance;
	}

	private GenerateResultReport() {
	}

	/**
	 * 目的：读取resultPath下的result ,生成测试报告，并存放在resultReportPath中
	 * 
	 * @param filepath
	 * 
	 * @return Map <id,filename>
	 */
	public void gererateStatisticsReport(String resultPath,
			String resultReportPath, String timeStamp, Report report) {
		Map<Integer, String> resultMap = readFileAndRtnResult(resultPath);

		String resultDir = resultReportPath + File.separator + "result " + timeStamp ;
		createDir(resultDir);
		copyFiles(resultPath, resultDir+File.separator+"result");
		deleteResultFile(resultPath);
		vertifyResult(resultMap, resultDir+File.separator+"report.txt", report);
	}
	
	public void gererateStatisticsReport(String resultPath,
			String resultReportPath, String timeStamp, Report report,boolean isGenerateTimeStamp){
		if(isGenerateTimeStamp){
			
			Map<Integer, String> resultMap = readFileAndRtnResult(resultPath);
			String resultDir = resultReportPath + File.separator + "result " + timeStamp ;
			createDir(resultDir);
			String subFileDir = resultDir + File.separator + "result "+ getCurrent()  ;
			createDir(subFileDir);
			copyFiles(resultPath, subFileDir+ File.separator+ "result");
			deleteResultFile(resultPath);
			vertifyResult(resultMap, subFileDir+File.separator+"report.txt", report);
		}else{
			gererateStatisticsReport(resultPath,resultReportPath,timeStamp,report)  ;
		}
	}

	
	private String getCurrent(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//设置日期格式
		return df.format(new Date()) ;
	}
	
	/**
	 * 目的：读filePath下的所有result文件，统计<id,count>的map
	 * 
	 * @param filepath
	 * 
	 * @return Map <id,filename>
	 */
	private Map<Integer, String> readFileAndRtnResult(String filepath) {

		File file = new File(filepath);
		if (!file.exists() || !file.isDirectory()) {
			logger.error("failed to find local job folder: " + filepath);
			return null;
		}

		File[] filelist = file.listFiles();
		Map<Integer, String> map = new HashMap<Integer, String>(); // key-->id
																	// value-->filename

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
	 * 方法：依次读取result文件夹下的xxx.txt,保存id到hashMap中，遍历hashMap中key中value的值
	 * 
	 * @param map
	 *            resultReportPath
	 * 
	 * @return void
	 */
	private void vertifyResult(Map<Integer, String> map,
			String resultReportPath, Report report) {
		if (map == null) {
			return;
		}

		int totalResultCount = 0; // the total result count
		int multiResultCount = 0; // the same job and the different result
		int errorFormatResultCount = 0; // may contain xml、empty format
		int correctResultCount = 0;

		Map<Integer, String> multiResultMap = new HashMap<Integer, String>();
		StringBuilder errorResultFile = new StringBuilder("");

		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			if (entry.getKey() == ERROR_RESULT_FORMAT) {
				errorFormatResultCount += entry.getValue().split(" ").length;
				totalResultCount += errorFormatResultCount;
				errorResultFile.append(entry.getValue() + "  ");
				
			} else {
				int count = entry.getValue().split(" ").length;
				if (count == 1) { // correct result
					++correctResultCount;
					++totalResultCount;

				} else { // the same job and the different result
					multiResultCount += count;
					totalResultCount += count;
					multiResultMap.put(entry.getKey(), entry.getValue());
				}
			}
		}

		report.setCorrectResultCount(correctResultCount);
		report.setResultCount(totalResultCount);
		report.setXmlResultCount(errorFormatResultCount);
		report.setMultiResultCount(multiResultCount);

		report.setMultiIDFilesMap(multiResultMap);
		report.setXmlFilename(errorResultFile.toString());

		writeToFile(resultReportPath, report.toString());

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
				if (line == null) {
					entry = new MyEntry<Integer, String>(ERROR_RESULT_FORMAT,
							file.getName());

				} else if (line.contains("<") || line.contains(">")) { // in
																		// some
																		// circumstances,xml
																		// format
																		// may
																		// occur
					entry = new MyEntry<Integer, String>(ERROR_RESULT_FORMAT,
							file.getName());

				} else if (line.startsWith("{\"id\":")) {
					int startIndex = line.indexOf(':');
					int endIndex = line.indexOf(',');
					String idValue = line.substring(startIndex + 1, endIndex);
					entry = new MyEntry<Integer, String>(
							Integer.parseInt(idValue), file.getName());

				} else {
					entry = new MyEntry<Integer, String>(ERROR_RESULT_FORMAT,
							file.getName());

				}
			} catch (FileNotFoundException e) {
				logger.error("The exception is occurred when file:" + file
						+ " is not exist", e);

			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
						bufferedReader = null;

					} catch (IOException e) {
						logger.error(
								"The exception is occurred when closing bufferedReader",
								e);

					}
				}
			}
		}
		return entry;
	}

	/**
	 * 删除指定目录下的所有文件
	 */
	private void deleteResultFile(String resultPath) {
		File file = new File(resultPath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File subFile : files) {
				subFile.delete();

			}
		}
	}

	//如果文件夹不存在则创建    
	private void createDir(String filename){
		File dir =new File(filename);
		if  (!dir .exists()  && !dir.isDirectory())      
		{       
		    dir.mkdir() ;  
		}
	}
	public void copyFiles(String srcDir,String destDir ){
		
		createDir(destDir);
		File[] files = new File(srcDir).listFiles();  
	    for (File file : files) {  
	         if (file.isFile()) {  
	        	 String pathname = destDir + File.separator + file.getName();  
	             File dest = new File(pathname);  
	             // File destPar = dest.getParentFile();  
	             // destPar.mkdirs();  
	             if (!dest.exists()) {  
	                 try {
						dest.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
	             }  
	             copyFile(file, dest);
	        }  

        }
	}
	            
	
	private void copyFile(File src, File dest){  
        FileInputStream input = null;  
        FileOutputStream outstrem = null;  
        try {  
             input = new FileInputStream(src);  
             outstrem = new FileOutputStream(dest);  
             outstrem.getChannel().transferFrom(input.getChannel(), 0,input.available());    
        } catch (Exception e) {  
            logger.warn(e.getMessage()); 
        } finally {  
            try {
				outstrem.flush();
				outstrem.close();  
	            input.close();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
            
        }  
    }  

private void writeToFile(String filename, String text) {
		System.out.println("text" + text);
		FileOutputStream outputStream = null;
		BufferedWriter writer = null;
		try {
			outputStream = new FileOutputStream(new File(filename), true);
			writer = new BufferedWriter(new OutputStreamWriter(outputStream));
			try {
				writer.write(text);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
				writer.close();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
