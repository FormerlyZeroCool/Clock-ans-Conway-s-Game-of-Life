import java.io.IOException;
import java.util.ArrayList;
public class StringUtil {

	public StringUtil()
	{
	}
	
	public boolean[][] convertJsonToBin(String s)
	{
		int x,y;
		boolean value;
		String stringValue;
		Memory binData=new Memory();
		s=s.substring(s.indexOf("p"), s.length());
		while(s.indexOf('p')!=-1)
		{//get x and y coordinates and save corresponding value to bin array in binData
			x=Integer.parseInt(s.substring(s.indexOf('p')+2, s.indexOf(',')));
			y=Integer.parseInt(s.substring(s.indexOf(',')+1, s.indexOf(')')));
			stringValue=s.substring(s.indexOf('=')+2, s.indexOf("\n")-1);
			value=Boolean.parseBoolean(stringValue);
			//System.out.println(x+","+y+"="+value+" "+s.substring(s.indexOf('=')+2, s.indexOf('\n')-1));
			if(binData.getValueAtPosition(x, y)!=value)//update bin array in obj
				binData.updateStatus(x, y);
			s=s.substring(s.indexOf('p')+1, s.length());
			s=s.substring(s.indexOf('\n')+1, s.length());
		}
		return binData.getBinArray();
	}
	public boolean[][] createStringArray (String s,int yPos)
	{
		s="8:01";
		int startPosX=0,lSChar=0,rSChar=0,j,halfMark=(s.length()+1)/2-1;
		s.substring(0, (s.length()+1)/2-1);
		Memory binData=new Memory();
		String sArray[]=new String[s.length()];
		int data[][]=new int[s.length()][3];//c 1 is datatype c 2 is xshift of each char c 3 is yshift
		for(j=0;j<s.length();j++)//dt1=num dt2=single column ;'.,:
		{
			sArray[j]=s.substring(j, j);
			if(s.substring(j, j).matches("[0-9]+"))
				data[j][0]=1;
			else if(s.substring(j, j).matches("[:;,.!]+"))
				data[j][0]=2;
			else 
				data[j][0]=-1;
			
		}
		startPosX=8;
		for(j=halfMark;j>=0;j--)
		{
			if (data[j][0]==1)
				startPosX-=3;
			else if(data[j][0]==2)
				startPosX-=1;
			//set Middle Left char start position if is num set for 3 column char else 1 column
			data[j][1]=startPosX;
			data[j][2]=yPos;
		}
		startPosX=7;
		for(j=halfMark+1;j<=s.length();j++)
		{
			if (data[j][0]==1)
				startPosX+=3;
			else if(data[j][0]==2)
				startPosX+=1;
			//set Middle Left char start position if is num set for 3 column char else 1 column
			data[j][1]=startPosX;
			data[j][2]=yPos;
		}
		return binData.getBinArray();
	}
	
		
}
	
