import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * LibSearchGUI is a complete interface for the LibrarySearch class
 * It handles the user input and transfers it to the LibrarySearch class
 * @author juliandileonardo
 *
 */

public class LibSearchGUI extends JFrame implements ActionListener{
	public static final int WIDTH = 800;//Width of windows
	public static final int HEIGHT = 600;//Height of window
	public JPanel mainPanel;//Main panel, to use instead of constantly making new JFrames
	public JTextField callnoT, authorsT, titleT, publisherT,organizationT, yearT, startYearT, endYearT;//The textboxes which hold the user input
	public JTextArea theText;//TextArea which has system output
	public int flag = 0;//used to determine add book,add journal, or search
	
	/**
	 * The welcome screen that the user is prompted with on Run
	 */
	public LibSearchGUI()
	{
		super();
		setSize(WIDTH,HEIGHT);
		setTitle("Library Search");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image img = Toolkit.getDefaultToolkit().getImage("src/b.png");
	    setIconImage(img);//Sets icon image
		
		setLayout(new BorderLayout());
		JMenuBar bar = new JMenuBar();//Sets the options in the Menu Bar
		JMenu commands = new JMenu("Commands");
		
		JMenuItem add = new JMenuItem("Add");
		add.addActionListener(this);
		commands.add(add);
		JMenuItem search = new JMenuItem("Search");
		search.addActionListener(this);
		commands.add(search);
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		commands.add(quit);
		
		bar.add(commands);
		add(bar,BorderLayout.NORTH);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5,1));//Initial Welcome Screen Display
		
		JLabel welcomeMessage = new JLabel("Welcome to Library Search");
		welcomeMessage.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(welcomeMessage);
		
		JPanel explanation = new JPanel();
		explanation.setLayout(new FlowLayout());
		JLabel explanationMessage1 = new JLabel("Choose a command from the \"Commands\" menu above for ");
		JLabel explanationMessage2 = new JLabel("adding a reference, searching references, or quitting the ");
		JLabel explanationMessage3 = new JLabel("program. ");
		explanation.add(explanationMessage1);
		explanation.add(explanationMessage2);
		explanation.add(explanationMessage3);
		JLabel credits = new JLabel("By: Julian Di Leonardo");
		credits.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(credits);
		mainPanel.add(explanation);
		
		JPanel uog = new JPanel();//UOG icon and Url button
		uog.setLayout(new FlowLayout());
		ImageIcon icon = new ImageIcon("src/uog.png");
        JButton btn = new JButton(icon);
        btn.addActionListener(new ClickEvent());
        uog.add(btn);
        mainPanel.add(uog);
		add(mainPanel, BorderLayout.CENTER);
	}
	/**
	 * The Add Screen, for adding Books and Journals. Also Displays any messages
	 */
	public void addGUI(){
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
			JPanel field = new JPanel();
			JLabel heading = new JLabel("Adding a Reference");//Title of the Frame
			mainPanel.add(heading,BorderLayout.NORTH);

				JPanel columns = new JPanel();
				//columns.setLayout(new GridLayout(7,1));
				columns.setLayout(new FlowLayout(FlowLayout.LEFT));//Used Flow in a flow, to handle invisibility issues
				columns.setPreferredSize(new Dimension(400, 400));
				
					JPanel line1 = new JPanel();
					line1.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel type = new JLabel("Type:          ");
					line1.add(type);
					String[] referenceStrings = {"Book","Journal"};//Combo Box options
					JComboBox referenceList = new JComboBox(referenceStrings);
					if(flag == 0){//Depending on what we are looking at(book or journal)
						referenceList.setSelectedIndex(0);
					}
					if(flag == 1){
						referenceList.setSelectedIndex(1);
					}
					referenceList.addActionListener(new comboBoxAction());
					line1.add(referenceList);
					columns.add(line1);//1
					
					
					JPanel line2 = new JPanel();
					line2.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel callno = new JLabel("Call No:         ");
					line2.add(callno);
					callnoT = new JTextField(15);//Text field
					line2.add(callnoT);
					columns.add(line2);//2
					
					JPanel line3 = new JPanel();
					line3.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel authors = new JLabel("Authors:        ");
					line3.add(authors);
					authorsT = new JTextField(15);//Text field
					line3.add(authorsT);
					if (flag == 1) {// if were a journal
						line3.setVisible(false);
						}
					columns.add(line3);
					
					JPanel line4 = new JPanel();
					line4.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel title = new JLabel("Title:             ");
					line4.add(title);
					titleT = new JTextField(15);//Text field
					line4.add(titleT);
					columns.add(line4);
					
					
					JPanel line9 = new JPanel();
					line9.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel organization = new JLabel("Organization:");
					line9.add(organization);
					organizationT = new JTextField(15);//Text field	
					line9.add(organizationT);
					if(flag == 0){//if its a book 
						line9.setVisible(false);		
					}
					columns.add(line9);
					
					JPanel line5 = new JPanel();
					line5.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel publisher = new JLabel("Publisher:      ");
					line5.add(publisher);
					publisherT = new JTextField(15);//Text field	
					line5.add(publisherT);
					if (flag == 1) {// if its a journal
						line5.setVisible(false);	
						}
					columns.add(line5);
					
					JPanel line6 = new JPanel();
					line6.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel year = new JLabel("Year:              ");
					line6.add(year);
					yearT = new JTextField(15);//Get Text
					line6.add(yearT);
					columns.add(line6);
					
				
				field.add(columns, BorderLayout.EAST);
	mainPanel.add(field,BorderLayout.WEST);
				
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new GridLayout(2,1));
				
				JPanel buttonA = new JPanel();
				buttonA.setLayout(new FlowLayout());
				
				JButton resetButton = new JButton("Reset");//reset buttons clears all the fields
				resetButton.setBackground(Color.RED);
				resetButton.addActionListener(this);
				buttonA.add(resetButton);
				buttonPanel.add(buttonA);
				
				JPanel buttonB = new JPanel();
				buttonB.setLayout(new FlowLayout());
				JButton addButton = new JButton("Create");//Create button adds to ReferenceDB
				addButton.setBackground(Color.GREEN);
				addButton.addActionListener(this);
				buttonB.add(addButton);
				buttonPanel.add(buttonB);
				
	mainPanel.add(buttonPanel,BorderLayout.EAST);

				JPanel messageOutput = new JPanel();
				messageOutput.setLayout(new BorderLayout());
				JLabel message = new JLabel("Messages:");//Displays messages returned from LibrarySearch.class
				theText = new JTextArea(10,25);
				theText.setEditable(false);
				
				theText.setLineWrap(true);
				
				JScrollPane scrolledText= new JScrollPane(theText);//Make textbox scrollable
				scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				
				messageOutput.add(message,BorderLayout.NORTH);
				messageOutput.add(scrolledText,BorderLayout.SOUTH);
				mainPanel.add(messageOutput,BorderLayout.SOUTH);
				

				add(mainPanel);

	}
	/**
	 * Takes in user's search querys, send them to LibrarySearch. Handles what is searched, and prints results.
	 * Review comments in addGUI() to understand similar aspects
	 */
	public void searchGUI(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
			JPanel field = new JPanel();
			JLabel heading = new JLabel("Searching References");
			mainPanel.add(heading,BorderLayout.NORTH);

				JPanel columns = new JPanel();
				columns.setLayout(new GridLayout(7,1));
					
					JPanel line2 = new JPanel();
					line2.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel callno = new JLabel("Call No:   ");
					line2.add(callno);
					callnoT = new JTextField(15);//Get Text
					line2.add(callnoT);
					columns.add(line2);//2
					
					JPanel line4 = new JPanel();
					line4.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel title = new JLabel("Title:       ");
					line4.add(title);
					titleT = new JTextField(15);//Get Text
					line4.add(titleT);
					columns.add(line4);

					JPanel line9 = new JPanel();
					line9.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel startYear = new JLabel("Start Year:");
					line9.add(startYear);
					startYearT = new JTextField(15);//Get Text	
					line9.add(startYearT);
					columns.add(line9);
					
					JPanel line5 = new JPanel();
					line5.setLayout(new FlowLayout(FlowLayout.LEFT));
					JLabel endYear = new JLabel("End Year:");
					line5.add(endYear);
					endYearT = new JTextField(15);//Get Text	
					line5.add(endYearT);
					columns.add(line5);
					
				field.add(columns, BorderLayout.EAST);
	mainPanel.add(field,BorderLayout.WEST);
				
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new GridLayout(2,1));
				
				JPanel buttonA = new JPanel();
				buttonA.setLayout(new FlowLayout());
				
				JButton resetButton = new JButton("Clear");
				resetButton.setBackground(Color.RED);
				resetButton.addActionListener(this);
				buttonA.add(resetButton);
				buttonPanel.add(buttonA);
				
				JPanel buttonB = new JPanel();
				buttonB.setLayout(new FlowLayout());
				JButton addButton = new JButton("Query");
				addButton.setBackground(Color.GREEN);
				addButton.addActionListener(this);
				buttonB.add(addButton);
				buttonPanel.add(buttonB);
				
	mainPanel.add(buttonPanel,BorderLayout.EAST);

				JPanel messageOutput = new JPanel();
				messageOutput.setLayout(new BorderLayout());
				JLabel message = new JLabel("Search Results");
				theText = new JTextArea(10,25);
				theText.setEditable(false);
				
				theText.setLineWrap(true);
				
				JScrollPane scrolledText= new JScrollPane(theText);
				scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				
				messageOutput.add(message,BorderLayout.NORTH);
				messageOutput.add(scrolledText,BorderLayout.SOUTH);
				mainPanel.add(messageOutput,BorderLayout.SOUTH);
				

				add(mainPanel);
	}
	/**
	 * Handles all user actions from buttons or Menubox
	 */
	public void actionPerformed(ActionEvent e){
		String menuString = e.getActionCommand();
		if(menuString.equals("Add")){//If the user selects Add from the Menu box
			mainPanel.setVisible(false);
			addGUI();
		}
			
		else if(menuString.equals("Search")){//If the User selects search from the menu box
			mainPanel.setVisible(false);
			searchGUI();
		}
			
		else if(menuString.equals("Quit")){//If the User quits the program
			System.exit(0);
		}
		else if(menuString.equals("Reset")){//The user will clear all the fields
			callnoT.setText("");
			authorsT.setText("");
			titleT.setText("");
			publisherT.setText("");
			yearT.setText("");
			organizationT.setText("");
			theText.setText("");
		}
		else if(menuString.equals("Create")){//User will add entry and will get back a message from librarySearch
			String call = callnoT.getText();
			String title = titleT.getText();
			String year = yearT.getText();
			String author = authorsT.getText();
			String publisher = publisherT.getText();
			String organization = organizationT.getText();
			
			theText.setText(LibrarySearch.main(flag, call, title, year,author,publisher,organization));//Text returned

		}
		else if(menuString.equals("Clear")){//Wipes all the fields in search
			callnoT.setText("");
			titleT.setText("");
			startYearT.setText("");
			endYearT.setText("");
			theText.setText("");
		}
		else if(menuString.equals("Query")){//Runs a search
			String call = callnoT.getText();
			String title = titleT.getText();
			String year = startYearT.getText() + "-" + endYearT.getText();
			String author = null;
			String publisher = null;
			String organization = null;
			theText.setText(LibrarySearch.main(2, call, title, year,author,publisher,organization));

		}
	}
	/**
	 * Handles selection of ComboBox between Book and Journal
	 * @author juliandileonardo
	 *
	 */
	private class comboBoxAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e)//Tells us if we are adding a  book/journal, and sets certain fields visible
		{
			JComboBox choice = (JComboBox)e.getSource();
			if(choice.getSelectedItem().equals("Book")){
				mainPanel.setVisible(false);
				flag = 0;
				addGUI();
			}
			else if(choice.getSelectedItem().equals("Journal"))
			{
				mainPanel.setVisible(false);
				flag = 1;
				addGUI();
			}
		}
	}
	/**
	 * Handles the User clicking the UOG Icon for the BrowserLauncher
	 * @author juliandileonardo
	 *
	 */
	private class ClickEvent implements ActionListener{
		public void actionPerformed(ActionEvent e){//Launches the UOG website, catches any exceptions
			
				BrowserLauncher launcher = null;
				try {
					launcher = new BrowserLauncher();
				} catch (BrowserLaunchingInitializingException e1) {
					e1.printStackTrace();
				} catch (UnsupportedOperatingSystemException e1) {
					e1.printStackTrace();
				}
				launcher.openURLinBrowser("http://www.uoguelph.ca");

		}
	}

	
	/**
	 * Starts the initial GUI
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LibSearchGUI w = new LibSearchGUI();
			w.setVisible(true);
	}

}
