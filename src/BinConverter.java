
public class BinConverter {
	private boolean b4,b3,b2,b1;
	private int decVal;
	
	public BinConverter(boolean bool4,boolean bool3,boolean bool2,boolean bool1)
	{
		b4=bool4;
		b3=bool3;
		b2=bool2;
		b1=bool1;
	}
	public void setBoolValue(boolean bool4,boolean bool3,boolean bool2,boolean bool1)
	{
		b4=bool4;
		b3=bool3;
		b2=bool2;
		b1=bool1;
	}
	public String getBin()
	{
		String bin="";
		if(b4)
			bin="1";
		else
			bin="0";
		if(b3)
			bin+="1";
		else
			bin+="0";
		if(b2)
			bin+="1";
		else
			bin+="0";
		if(b1)
			bin+="1";
		else
			bin+="0";
		return bin;
		
	}
	public void setBin(int dec)
	{
		decVal=dec;
		if(decVal>=8)
		{
			b4=true;
			decVal-=8;
		}
		else
		{
			b4=false;
			decVal-=8;
		}
		if(decVal>=4)
		{
			b3=true;
			decVal-=4;
		}
		else
		{
			b3=false;
			decVal-=4;
		}
		if(decVal>=2)
		{
			b2=true;
			decVal-=2;
		}
		else
		{
			b2=false;
			decVal-=2;
		}
		
		if(decVal==1)
			b1=true;
		else
			b1=false;
		decVal-=8;
		
		
		
	}
	public int getDec()
	{
		decVal=0;
		if(b4)
		{
			decVal+=8;
			if(b3)
			{
				decVal+=4;
				if(b2)
				{
					decVal+=2;
					if(b1)
					{
						decVal+=1;
					}
				}
				else
				{
					decVal+=0;
					if(b1)
					{
						decVal+=1;
					}
				}
			}
		}
		else
		{
			decVal+=0;
			if(b3)
			{
				decVal+=4;
				if(b2)
				{
					decVal+=2;
					if(b1)
					{
						decVal+=1;
					}
				}
				else
				{
					decVal+=0;
					if(b1)
					{
						decVal+=1;
					}
				}
			}
			else
			{
				decVal+=0;
				if(b2)
				{
					decVal+=2;
					if(b1)
					{
						decVal+=1;
					}
				}
				else
				{
					decVal+=0;
					if(b1)
					{
						decVal+=1;
					}
				}
			}
		}
		return decVal;
		
	}

}
