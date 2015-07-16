package oneapm.synthetic.agent.test.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessUtil {

	private static final Logger logger = LogManager.getLogger(ProcessUtil.class.getName());
	//创建进程
	public static void launchProcess(String processAddress,String cmdLine,String workDirectory){
		
		logger.info("generate script end...");
		// split cmd by space
		String[] cmds = cmdLine.split(" ") ;
		List<String> cmdList = new ArrayList<String>() ;
		cmdList.add(processAddress) ;
		cmdList.addAll(Arrays.asList(cmds)) ;
		
		ProcessBuilder pb = new ProcessBuilder(cmdList);
		Process p = null ;
		try {
			 pb.directory(new File(workDirectory)) ;
			 p = pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.warn("launchProcess IOException " + e);
		}
		try {
			p.waitFor() ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.warn("launchProcess InterruptedException " + e);
		}
		logger.info("generate script end...");
	}
	
	
	
	 public static void killProcessByPID(Long processId) {
	        List<String> cmds = new ArrayList<String>();
	        cmds.add("TASKKILL");
	        cmds.add("-F");
	        cmds.add("/PID");
	        cmds.add(processId.toString());
	        cmds.add("-T");
	        ExecCmdUtil.executeCommand(cmds);
	    }
}
