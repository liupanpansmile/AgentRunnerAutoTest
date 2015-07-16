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
	 * 	每个checkFrequency，计算resultPath下文件个数，如果在指定的时间内，
	 * 	result个数没有增加，则表明job执行完毕，否则job仍在执行
	 * 
	 * @param void
	 *           
	 * @return void
	 */
	@Override
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
