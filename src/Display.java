
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import javafx.scene.control.RadioButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Panel;
import java.awt.Label;
import java.awt.Button;
import java.awt.TextField;
import javax.swing.JCheckBox;
import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tglArrayIndex,updateCount=0;
	private ArrayList<ToggleButton> tglArray;
	private JPanel contentPane;
	private JTextField tbX;
	private JTextField tbY;
	private JLabel lblTitle,lblInstructions,lblUpdateCount;
	private JButton btnUpdate;
	private Memory binData;
	private JTextField tbSaveFilePath;
	private JButton btnSaveData;
	private Panel panelTitle;
	private Button btnLoadArray;
	private TextField tbLoadFilePath;
	private String test,test1;
	private JPanel pnlControlTrans;
	private JButton btnShiftX;
	private JTextField tbShiftX;
	private JButton btnVerticalShiftY;
	private JTextField tbShiftY;
	private JButton btnResetUpdateCount;
	private JCheckBox chckTimerState;
	private JTextField tbTimeDelay;
	private JCheckBox chckUpdateLocalDisplay,chckClockRunning;
	private JCheckBox chckConwayLogic;
	private Button btnClearGrid;
	private JRadioButton btnClockHHmm,btnClockmmss,btnClockssSS,btnClockAnalogue;
	
	private Thread clockUpdaterThread;
	private Canvas canvas;
	private JPanel panelGrouping;
	private JPanel panelGroupControls;


	public Display(Memory data) {
		
		binData=data;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panelTitle = new Panel();
		contentPane.add(panelTitle);
		chckUpdateLocalDisplay = new JCheckBox("Update Computer Display");
		chckUpdateLocalDisplay.setSelected(true);
		panelTitle.add(chckUpdateLocalDisplay);
		
		lblTitle = new JLabel("New Layout");
		
		panelGrouping = new JPanel();
		contentPane.add(panelGrouping);
		panelGrouping.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel pnlMain = new JPanel();
		panelGrouping.add(pnlMain);
		pnlMain.setLayout(new GridLayout(16, 16, 0, 0));
		
		panelGroupControls = new JPanel();
		panelGrouping.add(panelGroupControls);
		panelGroupControls.setLayout(new GridLayout(6, 1, 0, 0));
		
		JPanel pnlControl = new JPanel();
		panelGroupControls.add(pnlControl);
		pnlControl.setLayout(new GridLayout(4, 2, 0, 0));
		
		tbX = new JTextField();
		tbX.setText("");
		pnlControl.add(tbX);
		tbX.setColumns(1);
		
		tbY = new JTextField();
		tbY.setText("");
		tbY.setColumns(1);
		pnlControl.add(tbY);
		
		lblInstructions = new JLabel("Enter X then Y value Above");
		pnlControl.add(lblInstructions);
		
		btnUpdate = new JButton("Update Grid");
		btnUpdate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				refresh();
			}
		});
		pnlControl.add(btnUpdate);
		
		btnSaveData = new JButton("Save data file to path:");
		pnlControl.add(btnSaveData);
		
		tbSaveFilePath = new JTextField();
		pnlControl.add(tbSaveFilePath);
		tbSaveFilePath.setColumns(10);
		tbSaveFilePath.setText("\\Desktop\\filename.txt");
		
		
		btnLoadArray = new Button("Load from File");
		pnlControl.add(btnLoadArray);
		btnLoadArray.addActionListener(this);
		tbLoadFilePath = new TextField();
		pnlControl.add(tbLoadFilePath);
		
		pnlControlTrans = new JPanel();
		panelGroupControls.add(pnlControlTrans);
		pnlControlTrans.setLayout(new GridLayout(4, 2, 0, 0));
		
		
		btnShiftX = new JButton("Shift Horizontal");
		pnlControlTrans.add(btnShiftX);
		btnShiftX.addActionListener(this);
		
		tbShiftX = new JTextField();
		pnlControlTrans.add(tbShiftX);
		tbShiftX.setColumns(10);
		
		btnVerticalShiftY = new JButton("Vertical Shift");
		pnlControlTrans.add(btnVerticalShiftY);
		btnVerticalShiftY.addActionListener(this);
		tbShiftY = new JTextField();
		pnlControlTrans.add(tbShiftY);
		tbShiftY.setColumns(10);
		
		chckTimerState = new JCheckBox("Execute Translations on Timer(ms):");
		pnlControlTrans.add(chckTimerState);
		
		tbTimeDelay = new JTextField();
		pnlControlTrans.add(tbTimeDelay);
		tbTimeDelay.setColumns(10);
		
				chckConwayLogic=new JCheckBox("Turn on Conway Logic to Update");
				pnlControlTrans.add(chckConwayLogic);
				
				btnClearGrid = new Button("Clear grid");
				pnlControlTrans.add(btnClearGrid);
				btnClearGrid.addActionListener(this);
				chckConwayLogic.addActionListener(this);
		btnUpdate.addActionListener(this);
		tglArray=new ArrayList();

		tglArrayIndex=0;
		
		while(tglArrayIndex<256)
		{
			tglArray.add(new ToggleButton("("+tglArrayIndex%16+","+tglArrayIndex/16+")"));
			pnlMain.add((ToggleButton) tglArray.get(tglArrayIndex));
			((ToggleButton) tglArray.get(tglArrayIndex)).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					for(tglArrayIndex=0;tglArrayIndex<256;tglArrayIndex++)
					{
						if (e.getSource().equals((ToggleButton) tglArray.get(tglArrayIndex)))
						{
							binData.updateStatus(tglArrayIndex%16,tglArrayIndex/16);
							binData.printStatus();
						}
					}
					setScreen();
				}
			});
			tglArrayIndex++;
		}
		tglArrayIndex=255;
		
		lblUpdateCount= new JLabel("0");
		panelTitle.add(lblUpdateCount);
		
		btnResetUpdateCount=new JButton("Reset Count");
		panelTitle.add(btnResetUpdateCount);
		btnResetUpdateCount.addActionListener(this);

		chckClockRunning=new JCheckBox("Turn on Clock");
		chckClockRunning.setSelected(true);
		panelTitle.add(chckClockRunning);
		ButtonGroup timeSettingGroup=new ButtonGroup();
		btnClockHHmm=new JRadioButton("HH:mm time format");
		btnClockmmss=new JRadioButton("mm:ss time format");
		btnClockssSS=new JRadioButton("Seconds and hundredths of a second");
		btnClockAnalogue=new JRadioButton("Display Analogue Clock");
		timeSettingGroup.add(btnClockHHmm);
		timeSettingGroup.add(btnClockmmss);
		timeSettingGroup.add(btnClockssSS);
		timeSettingGroup.add(btnClockAnalogue);
		panelTitle.add(btnClockHHmm);
		panelTitle.add(btnClockmmss);
		panelTitle.add(btnClockssSS);
		panelTitle.add(btnClockAnalogue);
		btnClockAnalogue.setSelected(true);
		clockUpdaterThread=new Thread(){ @Override
	    public void run() 
		{
			final int sleepBuffer=10;
			int sleepTime;
			int counter;
			boolean mmss,hhmm,ssss,analogue;
			while(true)
			{   
				if(!chckClockRunning.isSelected())
					try {
						clockUpdaterThread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				while(chckClockRunning.isSelected())
		        {
		        	Date date = new Date();
					if(!btnClockAnalogue.isSelected())
					{

			        	DateFormat dateFormatmmss = new SimpleDateFormat("mm:ss");
			        	DateFormat dateFormatHHmm = new SimpleDateFormat("HH:mm");
			        	DateFormat dateFormatssSS = new SimpleDateFormat("ss.SS");
			        	String time;
			        	if(btnClockHHmm.isSelected())
			        	{
			        		time=dateFormatHHmm.format(date);
			        		sleepTime=1000;
			        	}
			        	else if (btnClockssSS.isSelected())
			        	{
			        		time=dateFormatssSS.format(date);
			        		sleepTime=50;
			        		time=time.replace('.', ':');
			        	}
			        		
			        	else
			        	{
				        	time=dateFormatmmss.format(date);
				        	sleepTime=100;
			        	}
			        	//System.out.println(time);
			        	binData.loadString(time);
			        	binData.shiftArrayYWrap(4);
					}
					else 
					{
						AnalogueClockLogic analogueArray=new AnalogueClockLogic(binData);
						sleepTime=100;
						analogueArray.buildAnalogueTimeOutput();
					}
		        	if(!chckTimerState.isSelected())
		        	{
		        		setScreen();
		        		addToCounter();
		        	}
		        	mmss=btnClockmmss.isSelected();
		        	hhmm=btnClockHHmm.isSelected();
		        	ssss=btnClockssSS.isSelected();
		        	analogue=btnClockAnalogue.isSelected();
		        	try {
		        		for(counter=sleepBuffer;counter<=sleepTime;counter+=sleepBuffer)
		        			{
		        				if(mmss==btnClockmmss.isSelected() && hhmm==btnClockHHmm.isSelected() && ssss==btnClockssSS.isSelected() && analogue==btnClockAnalogue.isSelected())
		        				{
			        				clockUpdaterThread.sleep(sleepBuffer);
		        				}
		        				else
		        				{
		        					counter=sleepTime;
		        				}
		        			}
		        		
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			}
			
		}};
		clockUpdaterThread.start();
	}

	public void setScreen() {
		
		int j;
		if(chckUpdateLocalDisplay.isSelected())
		{
			boolean[][] binArray=binData.getBinArray();
			for(j=0;j<256;j++)
			{
				if(binArray[j%16][j/16])
					((ToggleButton) tglArray.get(j)).setSelected(true);
				else
					((ToggleButton) tglArray.get(j)).setSelected(false);
			
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnUpdate))
			refresh();
		else if (e.getSource().equals(btnSaveData))
			saveToFile();
		else if (e.getSource().equals(btnLoadArray))
		{
			AccessFileSystem fs=new AccessFileSystem();
			StringUtil sUtil=new StringUtil();
			String t1;
			try {
				//t1 = fs.readFile(tbLoadFilePath.getText());
			
			//setBinDataToArray(sUtil.convertJsonToBin(t1));
			setScreen();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (e.getSource().equals(btnShiftX))
		{
			binData.shiftArrayXWrap(Integer.parseInt(tbShiftX.getText()));
			setScreen();
		}
		
		else if(e.getSource().equals(btnVerticalShiftY))
		{
			binData.shiftArrayYWrap(Integer.parseInt(tbShiftY.getText()));
			setScreen();
		}
		else if(e.getSource().equals(btnResetUpdateCount))
		{
			updateCount=0;
			lblUpdateCount.setText("0");
		}
		else if (e.getSource().equals(btnClearGrid))
		{
			binData.setAll(false);
			setScreen();
		}
		
	}

	private void saveToFile() {
		String filePath=tbSaveFilePath.getText();
		String dataToFile="";
		int x,y;
		try (PrintWriter out = new PrintWriter(new FileOutputStream(filePath))) {
			for(y=0;y<16;y++)
			{
				for(x=0;x<16;x++)
				{
					dataToFile=dataToFile+tglArray.get((y*16)+(x));
				}
				dataToFile=dataToFile+"\n";
			}
		    out.println(dataToFile);
		    out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void refresh()
	{
		int x=Integer.parseInt(tbX.getText()),y=Integer.parseInt(tbY.getText());
		//if x or y has a value greater than the max possible update to 15 (max in 4 bit system) 
		//if value is lower than the min, update to min
		if(x<0)
			x=0;
		if(x>=16)
			x=15;
		if(y<0)
			y=0;
		if(y>=16)
			y=15;
		
		binData.updateStatus(x,y);
		setScreen();
	}

	private void setBinDataToArray(boolean[][] binArray) 
	{
		int j,k;
		for(j=0;j<16;j++)
		{
			for(k=0;k<16;k++)
			{
				if(binData.getValueAtPosition(j, k)!=binArray[j][k])
				{
					binData.updateStatus(j,k);
				}
			}
		}
		
	}

	public boolean getTimerStatus() 
	{
		if(chckTimerState.isSelected() && tbTimeDelay.getText().matches("[0-9]+"))
			return true;
		else
			return false;
	}
	public void applyShift()
	{
		int x,y;
		if(tbShiftX.getText().matches("-?[0-9]+"))
			x=Integer.parseInt(tbShiftX.getText());
		else
			x=0;
		if(tbShiftY.getText().matches("-?[0-9]+"))
			y=Integer.parseInt(tbShiftY.getText());
		else
			y=0;
		
		binData.shiftArrayXWrap(x);
		binData.shiftArrayYWrap(y);
		if(getConwayStatus())
			conwaysShift();
		setScreen();
		updateCount++;
		lblUpdateCount.setText(updateCount+"");
	}

	public int getTimeDelay() 
	{
		int timeDelay;
		if(tbTimeDelay.getText().matches("[0-9]+"))
			timeDelay=Integer.parseInt(tbTimeDelay.getText());
		else 
			timeDelay=0;
		
		return timeDelay;
	}
	public boolean getConwayStatus()
	{
		return chckConwayLogic.isSelected();
	}
	public void conwaysShift()
	{
		int x,y,livingAdjacent;
		boolean copy[][];
		copy=new boolean[16][16];
		for(x=0;x<16;x++)
		{
			for(y=0;y<16;y++)
			{
				livingAdjacent=0;
				if(x-1>=0 && y-1>=0)
				{
					if(binData.getValueAtPosition(x-1,y-1))//bottom left
						livingAdjacent++;
				}
				if(x-1>=0)
				{
					if(binData.getValueAtPosition(x-1,y))//left of center
						livingAdjacent++;
				}
				if(x-1>=0 && y+1<16)
				{
					if(binData.getValueAtPosition(x-1,y+1))//top left
						livingAdjacent++;
				}

				if(y+1<16)
				{
					if(binData.getValueAtPosition(x,y+1))//top center
						livingAdjacent++;
				}
				if(x+1<16 && y+1<16)
				{
					if(binData.getValueAtPosition(x+1,y+1))//top right
						livingAdjacent++;
				}
				if(x+1<16)
				{
					if(binData.getValueAtPosition(x+1,y))//right of center
						livingAdjacent++;
				}
				if(x+1<16 && y-1>=0)
				{
					if(binData.getValueAtPosition(x+1,y-1))//bottom right
						livingAdjacent++;
				}
				if(y-1>=0)
				{
					if(binData.getValueAtPosition(x,y-1))//bottom center
						livingAdjacent++;
				}
				
				if(livingAdjacent>3)
					copy[x][y]=false;
				else if(livingAdjacent==3)
					copy[x][y]=true;
				else if(livingAdjacent<=1)
					copy[x][y]=false;
				else
					copy[x][y]=binData.getValueAtPosition(x, y);
			}
		}
		binData.loadLayout(copy);
		setScreen();
	}
	private void addToCounter() {

    	updateCount++;
    	lblUpdateCount.setText(updateCount+"");
		
	}
}
