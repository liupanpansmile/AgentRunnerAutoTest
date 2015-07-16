package oneapm.synthetic.agent.test.unittest;

import oneapm.synthetic.agent.test.result.GenerateResultReport;

public class GenerateResultReportTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		GenerateResultReport result = GenerateResultReport.instance() ;
		String srcDir = "D:\\work\\firefox-agent\\agent_runner_0629_02\\result" ;
		String destDir = "D:\\123456" ;
		result.copyFiles(srcDir, destDir);
	}

}
