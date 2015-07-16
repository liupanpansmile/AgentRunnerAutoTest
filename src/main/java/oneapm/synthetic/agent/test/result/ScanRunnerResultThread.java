package oneapm.synthetic.agent.test.result;

import java.io.File;

public class ScanRunnerResultThread implements Runnable {

	private String resultPath  ;
	private int checkFrequency ; // millisecond
	
	public ScanRunnerResultThread(String resultPath,int checkFrequency) {

		this.resultPath = resultPath ;
		this.checkFrequency = checkFrequency ;
	}
	
	/**
	 * 	ÿ��checkFrequency������resultPath���ļ������������ָ����ʱ���ڣ�
	 * 	result����û�����ӣ������jobִ����ϣ�����job����ִ��
	 * 
	 * @param void
	 *           
	 * @return void
	 */
	public void run() {
		
		int oldFileNum = countResultFileNumner(resultPath) ;
		int newFileNum  ;
		while(true){
			try {
				Thread.sleep(checkFrequency/2);
				//this.wait(checkFrequency/2);
				if((newFileNum = countResultFileNumner(resultPath)) > oldFileNum){
					oldFileNum = newFileNum ;
					continue ;
				}
				break ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	/**
	 * 	count file number
	 * 
	 * @param void
	 *           
	 * @return int
	 */
	private int countResultFileNumner(String resultPath){
		File file = new File(resultPath) ;
		return file.listFiles().length ;
	} 
	

}
