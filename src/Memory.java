import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Memory {
	
	private boolean xy[][];
	public Thread t0;
	private ArrayList<boolean[][]> charArrays;
	private ArrayList<String> charNameArray;
	private int maxX;//Max true X value
	private boolean stringLoadStatus=false;
	public Memory()
	{
		xy=new boolean[16][16];
		setAll(false);
		t0=new Thread();
		charArrays=new ArrayList<boolean[][]>();
		charNameArray=new ArrayList<String>();
		maxX=0;//Max True X value
	}
	
	public void setAll(boolean b) {
		boolean bool=b;
		int j,k;
		for(j=0;j<16;j++)
		{
			for(k=0;k<16;k++)
			{
				xy[j][k]=bool;
			}
		}
		if(bool)//update Maximum true x value
		{
			maxX=15;
		}
		else
		{
			maxX=0;
		}
	}
	public boolean[][] getBinArray()
	{
		return xy;
	}

	public boolean getValueAtPosition (int x,int y)
	{
		return xy[x][y];		
	}
	public boolean updateStatus(int x,int y)
	{
		xy[x][y]=!xy[x][y];
		if(xy[x][y] && maxX<x)//update Maximum true X value
			maxX=x;
		return xy[x][y];
		
	}
	public void printStatus()
	{
		int j,k;
		System.out.println("[Layout]");
		for(j=0;j<16;j++)
		{
			for(k=0;k<16;k++)
			{
				System.out.println(" p("+k+","+j+")='"+xy[k][j]+"'");
			}
		}		
		System.out.println("");
	}
	public void printStatus(String name)
	{
		int j,k;
		System.out.println("[Layout "+name+"]");
		for(j=0;j<16;j++)
		{
			for(k=0;k<16;k++)
			{
				System.out.println(" p("+k+","+j+")="+xy[k][j]);
			}
		}		
		System.out.println("");
	}
	public void loadLayout(boolean[][] bin)
	{
		xy=bin;
		calcMaxX();
	}
	public void shiftArrayXWrap(int shift)
	{
		boolean offsetColumns[][]=new boolean[Math.abs(shift)][16];
		boolean oldData[][]=new boolean[16][16];
		int j,k;
		for(j=0;j<16;j++)
		{
			for(k=0;k<16;k++)
			{
				oldData[j][k]=xy[j][k];
			}
		}	
		for(j=0;j<Math.abs(shift);j++)
		{
			for(k=0;k<16;k++)
			{
				if(shift>0)
					offsetColumns[j][k]=getValueAtPosition(15-shift+j,k);
				else
				{
					offsetColumns[j][k]=getValueAtPosition(j,k);//shift is negative
				}
			}
		}
		if(shift>0)
		{
			for(j=0;j<shift;j++)
			{
				for(k=0;k<16;k++)
				{
					xy[j][k]=offsetColumns[j][k];
				}
			}
			for(j=shift;j<16;j++)
			{
				for(k=0;k<16;k++)
				{
					xy[j][k]=oldData[j-shift][k];
				}
			}
		}
		else
		{
			int l=shift,m=0;
			for(j=16+shift;j<16;j++)
			{
				for(k=0;k<16;k++)
				{
					xy[j][k]=offsetColumns[j-16-shift][k];
				}
			}
			for(j=0;j<16+shift;j++)
			{
				for(k=0;k<16;k++)
				{
					xy[j][k]=oldData[j-shift][k];
				}
			}
		}
		
	}
	public void shiftArrayYWrap(int shift)
	{
		boolean offsetColumns[][]=new boolean[16][Math.abs(shift)];
		boolean oldData[][]=new boolean[16][16];
		int j,k;
		for(j=0;j<16;j++)
		{
			for(k=0;k<16;k++)
			{
				oldData[k][j]=xy[k][j];
			}
		}	
		for(j=0;j<Math.abs(shift);j++)
		{
			for(k=0;k<16;k++)
			{
				if(shift>0)
					offsetColumns[k][j]=getValueAtPosition(k,15-shift+j);
				else
				{
					offsetColumns[k][j]=getValueAtPosition(k,j);//shift is negative
				}
			}
		}
		if(shift>0)
		{
			for(j=0;j<shift;j++)
			{
				for(k=0;k<16;k++)
				{
					xy[k][j]=offsetColumns[k][j];
				}
			}
			for(j=shift;j<16;j++)
			{
				for(k=0;k<16;k++)
				{
					xy[k][j]=oldData[k][j-shift];
				}
			}
		}
		else
		{
			for(j=16+shift;j<16;j++)
			{
				for(k=0;k<16;k++)
				{
					xy[k][j]=offsetColumns[k][j-16-shift];
				}
			}
			for(j=0;j<16+shift;j++)
			{
				for(k=0;k<16;k++)
				{
					xy[k][j]=oldData[k][j-shift];
				}
			}
		}
		
	}
	private void setColumn(int x, boolean value) 
	{
		int y;
		for(y=0;y<15;y++)
		{
			xy[x][y]=value;
		}
	}

	public void updateValue(int x,int y,boolean value)
	{
		xy[x][y]=value;
	}

	public void loadCharArrays()
	{
		int j,separatorIndex;
		String name;
		StringUtil sUtil=new StringUtil();
		AccessFileSystem fs=new AccessFileSystem();
		ArrayList<Path> filePaths=fs.getFileListInPath("/charLookups/");
		for(j=0;j<filePaths.size();j++)
		{
			name=filePaths.get(j).getFileName().toString();
			separatorIndex=name.indexOf('/');
			while(separatorIndex!=-1)
			{
				name=name.substring(separatorIndex);
				separatorIndex=name.indexOf('/');
			}
			charNameArray.add(name);
			System.out.println(name);
		}
		charArrays=fs.getCharArrays();
	}
	
	public ArrayList getNamesArray()
	{
		return charNameArray;
	}
	
	public void loadString(String stringToSave)
	{
		stringLoadStatus=false;
		setAll(false);
		int j,index;
		String working, previousWorking="";
		for(j=0;j<stringToSave.length();j++)
		{
			working=stringToSave.substring(j,j+1);
			index=getCharArrayIndexByName(working);
			/*if(j==0)
			{
				loadLayout(charArrays.get(index));
			}
			else
			{*/
				if(working.matches("[0-9]+"))
				{
					if(previousWorking.matches("[0-9]+"))
						addWithOffset(charArrays.get(index),maxX+2,0);
					else
						addWithOffset(charArrays.get(index),maxX+1,0);
				}
				else
				{
					addWithOffset(charArrays.get(index),maxX+1,0);
				}
			//}
			previousWorking=working;
		}
		stringLoadStatus=true;
	}
	private void addWithOffset(boolean[][] toAdd,int xOffset,int yOffset)
	{
		int x,y;
		for(x=xOffset;x<16;x++)
		{
			for(y=yOffset;y<16;y++)
			{
				xy[x][y]=toAdd[x-xOffset][y-yOffset];
				if(xy[x][y] && x>maxX)
				{
					maxX=x;
				}
			}
		}
	}
	public boolean[][] getCharArrayByName(String name)
	{
		int index=0;
		while(!charNameArray.get(index).equals(name))
		{
			index++;
		}
		return charArrays.get(index);
	}
	public int getCharArrayIndexByName(String name)
	{
		int index=0;
		while(!charNameArray.get(index).equals(name) && index<charNameArray.size()-1)
		{
			index++;
		}
		return index;
	}
	private void calcMaxX()
	{
		int x,y;
		for(x=0;x<16;x++)
		{
			for(y=0;y<16;y++)
			{
				if(xy[x][y] && maxX<x)
					maxX=x;
			}
		}
	}
	public boolean getStringLoadStatus()
	{
		return stringLoadStatus;
	}
	
	public void setStringLoadStatus(boolean b)
	{
		stringLoadStatus=b;
	}

}
