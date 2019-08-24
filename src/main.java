
public class main 
{

	public static Memory binData=new Memory();
	public static void main(String[] args) 
	{
		Thread t0=new Thread();
		//sys.printStatus();
		//binData.getCharArrayByName("1");
		binData.loadCharArrays();
		Display disp=new Display(binData);
		disp.setVisible(true);
			disp.setScreen();
		while(true)
		{
			if(disp.getTimerStatus())
			{
				disp.applyShift();
				try {
					t0.sleep(disp.getTimeDelay());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else
			{
				try {
					t0.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

}
