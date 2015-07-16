package oneapm.synthetic.agent.runner.report;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;

import oneapm.synthetic.agent.test.result.GenerateResultReport;

import org.apache.logging.log4j.Logger;

public class Report {


	static Logger logger = org.apache.logging.log4j.LogManager.getLogger(Report.class.getName());
	
	private int poolSize ;
	private int jobCount ;
	private int resultCount =-1;
	private Instant startTime ;
	private Instant endTime ;
	private int xmlResultCount = 0 ;
	private int multiResultCount = 0 ;
	private int correctResultCount = 0 ;
	
	  
	
	private String xmlFilename = null ;
	private Map<Integer,String> multiIDFilesMap = null ; //<id,filenameList>
	
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
	public int getJobCount() {
		return jobCount;
	}
	public void setJobCount(int jobCount) {
		this.jobCount = jobCount;
	}
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	public Instant getStartTime() {
		return startTime;
	}
	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}
	public Instant getEndTime() {
		return endTime;
	}
	public void setEndTime(Instant endTime) {
		this.endTime = endTime;
	}
	
	
	public int getXmlResultCount() {
		return xmlResultCount;
	}
	public void setXmlResultCount(int xmlResultCount) {
		this.xmlResultCount = xmlResultCount;
	}
	public int getMultiResultCount() {
		return multiResultCount;
	}
	public void setMultiResultCount(int multiResultCount) {
		this.multiResultCount = multiResultCount;
	}
	
	public int getCorrectResultCount() {
		return correctResultCount;
	}
	public void setCorrectResultCount(int correctResultCount) {
		this.correctResultCount = correctResultCount;
	}
	
	public String getXmlFilename() {
		return xmlFilename;
	}
	public void setXmlFilename(String xmlFilename) {
		this.xmlFilename = xmlFilename;
	}
	
	public Map<Integer, String> getMultiIDFilesMap() {
		return multiIDFilesMap;
	}
	public void setMultiIDFilesMap(Map<Integer, String> multiIDFilesMap) {
		this.multiIDFilesMap = multiIDFilesMap;
	}
	public double getXMLResultRate(){
		try{
			return setScale((xmlResultCount*100.0)/resultCount,2) ;
		}catch(NullPointerException e){
			logger.warn("resultCount == " + resultCount + e.getMessage()) ;	
		}
		return 0 ;
	}
	
	public double getCorrectResultRate(){
		try{
			return setScale((correctResultCount*100.0)/resultCount,2) ;
		}catch(NullPointerException e){
			logger.warn("resultCount == " + resultCount + e.getMessage()) ;	
		}
		return 0 ;
	}
	
	public double getMultiResultRate(){
		try{
			return setScale((multiResultCount*100.0)/resultCount,2) ;
		}catch(NullPointerException e){
			logger.warn("resultCount == " + resultCount + e.getMessage()) ;	
		}
		return 0 ;
	}
	
	public long betweenSeconds(Instant startTime,Instant endTime){
		return Duration.between(endTime, startTime).abs().toMillis()/1000 ;
	}

	private double setScale(double d,int newScale){
		logger.info("setScale d:" + d) ;
		BigDecimal   bd   =   new   BigDecimal(d);   
		return bd.setScale(newScale,BigDecimal.ROUND_HALF_UP).doubleValue();  
	}
	private double timeDealPerJob(Instant startTime,Instant endTime,int jobCount){
		double timeDifference = betweenSeconds(startTime, endTime)*1.0 ;
		return setScale(timeDifference/jobCount, 2) ;
	}
	public String toString(){
		
		/*********************************************report***********************************************/
		StringBuilder sb = new StringBuilder() ;
		 

	    	sb.append("---------------------------------report as follows ------------------------------\n");
	    	sb.append("poolSize: " + poolSize+ "\n");
	    	sb.append("job: " + jobCount+ "\n");
	    	sb.append("total result: " + resultCount + "\n\n");
	    	sb.append("startTime:" + startTime.atOffset(ZoneOffset.ofHours(8)) + " endTime :" + endTime.atOffset(ZoneOffset.ofHours(8)) + "\n") ;
	    	sb.append("total execute time:" + betweenSeconds(startTime, endTime) + " s"+"\n");
	        sb.append("deal per job's time  :" + timeDealPerJob(startTime,endTime,jobCount) + "\n");
	        
	        sb.append("XML Result: " + xmlResultCount + "  Rate: " + getXMLResultRate() + " %\n");
	        sb.append("Multi Result: " + multiResultCount + " Rate: " + getMultiResultRate() + " %\n");
	        sb.append("Correct Result: " + correctResultCount + " Rate: " + getCorrectResultRate() + " %\n");
	        
	        if(multiIDFilesMap != null && multiIDFilesMap.size() != 0){
	        	
	        	sb.append("\n\n------------------------------------------");
	        	sb.append("\nMulti Result As Follows\n");
	        	
	        	for(Map.Entry<Integer, String> entry:multiIDFilesMap.entrySet()){
	        		sb.append("id:" + entry.getKey() + " filename: " + entry.getValue()+"\n");
	        	}
	        }
			
			if(xmlFilename != null && !xmlFilename.equals("")){
				sb.append("\n\n------------------------------------------");
				sb.append("\n xml filename: " +xmlFilename+"\n");
			}
	   
		
		return sb.toString() ;
	}
	
	
}
