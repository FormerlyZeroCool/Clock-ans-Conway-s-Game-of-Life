import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class AnalogueClockLogic 
{
private boolean[][] xy;
private boolean invertColor,value;
private Date currentTime;
private SimpleDateFormat sDF;
private Memory binData;
public AnalogueClockLogic(Memory data)
{
	binData=data;
	sDF=new SimpleDateFormat("HH:mm:ss");
	currentTime=new Date();
	invertColor=true;
	if(invertColor=false)
		value=false;
		else
			value=true;
}
public String getDate()
{
	return sDF.format(currentTime);
}
public void buildAnalogueTimeOutput()
{
	currentTime=new Date();
	binData.setAll(!value);
	int centerMarker;
	int interval=1;
	String time[]=sDF.format(currentTime).split("[:]+");
	int hours,minutes,seconds;
	hours= Integer.parseInt(time[0]);
	minutes= Integer.parseInt(time[1]);
	seconds= Integer.parseInt(time[2]);
	if(hours>12)
		hours-=12;
	int arrayStartX=0,arrayStartY=0,arrayEndX=15,arrayEndY=15;
	centerMarker=0;
	while(arrayStartX<=centerMarker)//Updates Seconds
	{
		updateNextPoint(arrayStartX,arrayStartY,arrayEndX,arrayEndY,seconds,60,1);
		arrayStartX+=interval;
		arrayStartY+=interval;
		arrayEndX-=interval;
		arrayEndY-=interval;
	}
	arrayStartX=0;
	arrayStartY=0;
	arrayEndX=15;
	arrayEndY=15;
	centerMarker=6;
	while(arrayStartX<=centerMarker)//Updates Minute hand
	{
		updateNextPoint(arrayStartX,arrayStartY,arrayEndX,arrayEndY,minutes,60,0);
		arrayStartX+=interval;
		arrayStartY+=interval;
		arrayEndX-=interval;
		arrayEndY-=interval;
	}
	arrayStartX=2;
	arrayStartY=2;
	arrayEndX=13;
	arrayEndY=13;
	while(arrayStartX<=centerMarker)//Updates hour hand
	{
		updateNextPoint(arrayStartX,arrayStartY,arrayEndX,arrayEndY,hours,12,0);
		arrayStartX+=interval;
		arrayStartY+=interval;
		arrayEndX-=interval;
		arrayEndY-=interval;
	}
	binData.updateValue(15,7,value);
	binData.updateValue(15,8,value);
	binData.updateValue(0,7,value);
	binData.updateValue(0,8,value);
	binData.updateValue(7,15,value);
	binData.updateValue(8,15,value);
	binData.updateValue(7,0,value);
	binData.updateValue(8,0,value);
	binData.updateValue(7,8,value);
	binData.updateValue(8,7,value);
	binData.updateValue(7,7,value);
	binData.updateValue(8,8,value);
	//return binData;
}
private void updateNextPoint(int arrayStartX,int arrayStartY,int arrayEndX,int arrayEndY,int diff,int revolution,int pxSize)
{
	int cubePerim,sideLength,perimPosition,quadrant,xToUpdate=0,yToUpdate=0;
cubePerim= calcCubePerim (arrayStartX, arrayEndX);
sideLength= cubePerim /4;
perimPosition=(int) (Math.floor((diff* cubePerim )/(1.0*revolution)+sideLength/2));
if(perimPosition<cubePerim)
{
quadrant=(perimPosition)/sideLength;
}
else
{
quadrant=(perimPosition-cubePerim)/sideLength;
}
if(quadrant==0)
{
	yToUpdate=arrayStartY;
	xToUpdate=(perimPosition)%cubePerim+arrayStartX;
}
else if(quadrant==1)
{
xToUpdate=arrayEndX;
yToUpdate=(perimPosition%sideLength)+arrayStartY;
}
else if(quadrant==2)
{
	yToUpdate=arrayEndY;
	xToUpdate=(sideLength+arrayStartX)-(perimPosition%sideLength);
}
else if(quadrant==3)
{
	yToUpdate=(sideLength+arrayStartY)-(perimPosition%sideLength);
	xToUpdate=arrayStartX;
}
if(pxSize==1)
	updateSurrounding(xToUpdate,yToUpdate);
binData.updateValue(xToUpdate,yToUpdate,value);
}
private void updateSurrounding(int xToUpdate, int yToUpdate) {
	if(xToUpdate<=7)
	{
		binData.updateValue(xToUpdate+1,yToUpdate,value);
		if(yToUpdate<=7)
			binData.updateValue(xToUpdate+1,yToUpdate+1,value);
		else
			binData.updateValue(xToUpdate+1,yToUpdate-1,value);
	}
	else
	{
		binData.updateValue(xToUpdate-1,yToUpdate,value);
		if(yToUpdate<=7)
			binData.updateValue(xToUpdate-1,yToUpdate+1,value);
		else
			binData.updateValue(xToUpdate-1,yToUpdate-1,value);
	}
	if(yToUpdate<=7)
	{
		binData.updateValue(xToUpdate,yToUpdate+1,value);
	}
	else
	{
		binData.updateValue(xToUpdate,yToUpdate-1,value);
	}
	
}
public int  calcCubePerim(int start, int end)
{
	return (end-start)*4;
}

}
