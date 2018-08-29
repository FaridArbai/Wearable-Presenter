package watchsliderserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame{
	private JLabel label;
	private static MainFrame instance;
	private static final int BORDER = 40;
	private static final int FRAME_HEIGHT = 640;
	private static final int FRAME_WIDTH = 480;
	private static final int LABEL_WIDTH = 400;
	private static final int LABEL_HEIGHT = 560;
	
	public static void init(){
		MainFrame.instance = new MainFrame();
	}
	
	public MainFrame(){
		super("Wearable Presenter Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage((new ImageIcon("./wearable-presenter.png")).getImage());
		
		
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setVisible(true);
		int pane_width = this.getContentPane().getWidth();
		int pane_height = this.getContentPane().getHeight();
		this.setVisible(false);
		
		System.out.println(pane_height);
		
		JPanel pane = new JPanel();
		pane.setOpaque(true);
		pane.setBackground(new Color(0xF0F0F0));
		pane.setLayout(null);
		
		this.label = new JLabel();
		/**
		this.label.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(0x000000)),
			BorderFactory.createEmptyBorder(BORDER/2, BORDER/4, BORDER/2, BORDER/4)));
		**/
		
		this.label.setBorder(BorderFactory.createEmptyBorder(BORDER/2, BORDER/4, BORDER/2, BORDER/4));
		
		this.label.setHorizontalTextPosition(JLabel.LEFT);
		this.label.setVerticalAlignment(JLabel.BOTTOM);
		this.label.setOpaque(true);
		this.label.setBackground(new Color(0xFDFDFD));
		
		this.label.setSize(pane_width-2*BORDER, pane_height-2*BORDER);
		this.label.setLocation(BORDER,BORDER);
		
		this.label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		
		
		JScrollPane scroller = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		/**
		scroller.add(label);
		pane.setSize(pane_width,pane_height);
		//pane.add(label);
		pane.add(scroller);
		**/
		
		this.setContentPane(scroller);
		this.setLocationByPlatform(true);
		this.setVisible(true);
	}
	
	public static JLabel getLabel(){
		return MainFrame.instance.label;
	}
	
	public static void log(String text){
		Calendar today = Calendar.getInstance();
		int day = today.get(Calendar.DAY_OF_MONTH);
		int month = today.get(Calendar.MONTH)+1;
		int year = today.get(Calendar.YEAR);
		int hour = today.get(Calendar.HOUR_OF_DAY);
		int minute = today.get(Calendar.MINUTE);
		int second = today.get(Calendar.SECOND);
		
		String time_str = String.format("%02d/%02d/%d %02d:%02d:%02d",
			day, month, year, hour, minute, second);
		
		
		String log_head = "[" + time_str + "] > ";
		instance.label.setText("<html>" + 
			instance.label.getText().replace("<html>","").replace("</html>","") + "<br/>" + 
			log_head + text + "</html>");
	}
	
	
}
