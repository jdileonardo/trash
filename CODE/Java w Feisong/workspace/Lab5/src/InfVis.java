// InfVis.java
//
// This program downloads a file, counts the number of occurrences of 
// the words 'red, 'blue', and 'green', and makes and displays a colour 
// based on these values.

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.beans.*;

public class InfVis extends JFrame implements ActionListener, PropertyChangeListener {

	private JMenu fileMenu;
	private JMenuItem loadItem;
	private JMenuBar bar;

	private JProgressBar progressBar;
	
	private Task task;

	private String text;
	private String title;
	private boolean ready;
	private	ArrayList<String>otherPages = new ArrayList<String>();
	private Color a,b,c,d;
	private int count;
	private int rs;
	private int gs;
	private int bs;
	private int tot;

	class Task extends SwingWorker<Void, Void> {

	@Override
	public Void doInBackground() {
            
		setProgress(0);
		count = 0;
		for(int i = 0; i < 4; i ++)
		{
		loadText();
		count ++;
		}
		setProgress(100);
            	return null;
        }

        @Override
        public void done() {
		progressBar.setIndeterminate(false);
            	Toolkit.getDefaultToolkit().beep();
            	setCursor(null); //turn off the wait cursor
        }
    }

	public InfVis() {
		super("Information Visualization");				//provides text for the title bar
		
		JPanel jp = new JPanel();

		//Add components here
		fileMenu = new JMenu("File");
		loadItem = new JMenuItem("Load Text");
		loadItem.addActionListener(this);
		fileMenu.add(loadItem);

		bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(fileMenu);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(false);

		jp.add(progressBar);
		setContentPane(jp);

		setSize(250,250);
		setVisible(true);

		// other initialization
		ready = false;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
    	}

        public void actionPerformed(ActionEvent evt) {

		if(evt.getSource() == loadItem) {
			
			progressBar.setIndeterminate(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		        task = new Task();
		        task.addPropertyChangeListener(this);
		        task.execute();
		}
	}

	private void loadText() {
		
		URL u;
		InputStream is = null;
		BufferedReader dis;
		String s;
		
		// reset text
		text= "";
		tot=rs=gs=bs=0;

		try {
			if(count == 0){
				otherPages.add(("http://en.wikipedia.org/wiki/Special:Random"));
			}
			//u = new URL("http://www.gutenberg.org/files/2554/2554.txt");
			//u = new URL("http://en.wikipedia.org/wiki/Special:Random");
			//u =new URL ("http://en.wikipedia.org/wiki/Large_Flycatcher");
			u = new URL(otherPages.get(count));
			System.out.println("Downloading page...");
			
			is = u.openStream();
			
			dis = new BufferedReader(new InputStreamReader(is));
			
			while((s=dis.readLine()) != null) {
				text += s;

			}

			System.out.println("Done.");

			computeColour();
			if(count == 0){
			title = getTitle(text);
			getURL(text);
			}
			
			ready=true;
			repaint();
		}
		catch(MalformedURLException mue) {
			
			System.out.println("Internet problem...");
		}
		catch(IOException ioe) {
			
			System.out.println("Internet problem...");
		}
		finally {
			
			try {
				is.close();
			}
			catch (IOException ioe) {
				// meh
			}
		}
	}

	private void computeColour() {

		System.out.println("Computing colour...");

		String[] words = text.split(" ");

		for(int i=0; i<words.length; i++) {

			if(words[i].equalsIgnoreCase("the")) {
				rs++;
				tot++;
			}
			else if(words[i].equalsIgnoreCase("of")) {
				gs++;
				tot++;
			}
			else if(words[i].equalsIgnoreCase("to")) {
				bs++;
				tot++;
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
 int flag = 0;
		if(ready) {

			int xs = getBounds().width;
			int ys = getBounds().height;

			float R = (float)rs / (float)tot;
			float G = (float)bs / (float)tot;
			float B = (float)gs / (float)tot;			
			if(count == 0){
			a = new Color(R,G,B);
			
			}
			if(count == 1){
				b = new Color(R,G,B);

			}
			if(count == 2){
				c = new Color(R,G,B);
			}
			if (count >= 3){
			d = new Color(R,G,B);
			flag = 1;
			}
			if(flag == 1){
				g.setColor(a);
				
				g.fillOval((xs/2)-(xs/6), xs/3, xs/3, xs/3);
				g.setColor(Color.BLACK);
				g.drawString(title, (xs/2)-(xs/6), xs/3);
				
				g.setColor(b);
			
				g.fillOval((xs/5)-(xs/10), xs-(xs/5), xs/5, xs/5);
				g.setColor(Color.BLACK);
				g.drawLine((xs/2)-(xs/6), (xs/2), (xs/5), xs-(xs/5));			
				g.drawString((otherPages.get(1)).substring(29), (xs/5)-(xs/10), xs-(xs/5));

				
				g.setColor(c);
				
				g.fillOval((xs/2)-(xs/10), xs-(xs/5), xs/5, xs/5);
				g.setColor(Color.BLACK);
				g.drawLine(xs/2, (xs/3)*2, xs/2, xs-(xs/5));
				g.drawString((otherPages.get(2)).substring(29), (xs/2)-(xs/10), xs-(xs/5));

				g.setColor(d);
				
				g.fillOval((xs/5)*3+(xs/10), xs-(xs/5), xs/5, xs/5);
				g.setColor(Color.BLACK);
				g.drawLine((xs/2)+(xs/6), (xs/2), (xs/5)*4, xs-(xs/5));
				g.drawString((otherPages.get(3)).substring(29), (xs/5)*3+(xs/10), xs-(xs/5));

			}
			
			
				// begginging x, beggining y, width ,height
			
			
			//g.setColor(Color.WHITE);
			//g.drawLine(0, 0, xs, ys);

		}
}

	public String getTitle(String text) {
		String[] words = text.split("<");
		for(int i=0; i<words.length; i++) {
			
			if(words[i].length()>=5) {
				if(words[i].substring(0,5).equalsIgnoreCase("title")) {
					return words[i].substring(6,words[i].length()-35);
					
				}
			}
		}
		
		return null;
	}
	public void getURL(String text){
		
		String identify = " <a href=\"/wiki/";
		String[] words = text.split("\" title=\"");
		for(int i = 0; i < words.length ; i++)
		{
			if(words[i].contains(identify)){
				String name = words[i].substring(words[i].lastIndexOf(identify)+10);
				otherPages.add("http://en.wikipedia.org"+name);
			}
		}
		System.out.println(otherPages);
	}



	public static void main(String args[]) {
		InfVis iv = new InfVis();				
		iv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
