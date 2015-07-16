package oneapm.synthetic.agent.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecCmdUtil {

	 private static final Logger logger = LogManager.getLogger(ExecCmdUtil.class.getName());
	 public static void executeCommand(List<String> cmds) {
	        logger.debug("Execute Command:" + cmds);

	        ProcessBuilder p = new ProcessBuilder(cmds);
	        p.redirectErrorStream(true);
	        try {
	            Process ps = p.start();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(ps.getInputStream()));
	            StringBuffer out = new StringBuffer();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                logger.debug("[output] " + line);
	                out.append(line).append(System.getProperty("line.separator"));
	            }
	            logger.debug("Excute command result:" + out);

	            ps.destroy();
	        } catch (IOException e) {
	            logger.error(e.getMessage(), e);
	        }
	    }
}
