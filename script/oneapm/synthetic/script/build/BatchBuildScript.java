package oneapm.synthetic.script.build;

import oneapm.synthetic.script.util.FileUtil;
import net.sf.json.JSONObject;


public class BatchBuildScript {

	private String templateFile ;
	private String destPath ;
	private int count ;
	
	
	public BatchBuildScript(){
		
	}
	
	public BatchBuildScript(String templateFile, String destPath, int count) {
		super();
		this.templateFile = templateFile;
		this.destPath = destPath;
		this.count = count;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public String getDestPath() {
		return destPath;
	}

	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public void batchBuildScript() {
		
		String jsonContext = FileUtil.readFile(templateFile) ;
		JSONObject jsonObject = JSONObject.fromObject(jsonContext);
		
		for(int i =0 ;i < count ;++i){
			if(jsonObject.containsKey("id") )
			{
				jsonObject.put("id", i) ;	
			}
			String filename =destPath + i +".osl" ;
			FileUtil.writeFile(filename, jsonObject.toString());			
		}

	}

	
	
	
}
