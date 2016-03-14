package com.chrisali.peopledatabase.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.chrisali.peopledatabase.controller.Controller;
import com.chrisali.peopledatabase.view.formpanel.FormEvent;
import com.chrisali.peopledatabase.view.formpanel.FormListener;
import com.chrisali.peopledatabase.view.formpanel.FormPanel;
import com.chrisali.peopledatabase.view.messagelist.MessagePanel;
import com.chrisali.peopledatabase.view.persontable.PersonTableListener;
import com.chrisali.peopledatabase.view.persontable.TablePanel;
import com.chrisali.peopledatabase.view.preferences.PreferencesDialog;
import com.chrisali.peopledatabase.view.preferences.PrefsListener;
import com.chrisali.peopledatabase.view.toolbar.Toolbar;
import com.chrisali.peopledatabase.view.toolbar.ToolbarListener;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private TablePanel tablePanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private Controller controller;
	private PreferencesDialog prefsDialog;
	private Preferences prefs;
	private JSplitPane splitPane;
	private JTabbedPane tabPane;
	private MessagePanel messagePanel;
	
	public MainFrame() {
		super("People Database");
		
		setLayout(new BorderLayout());
		
		controller = new Controller();
		
		//--------------------- Table Panel -------------------------
		
		tablePanel = new TablePanel();
		tablePanel.setData(controller.getPeople());
		tablePanel.setPersonTableListener(new PersonTableListener() {
			@Override
			public void rowDeleted(int index) {
				controller.removePerson(index);
			}
		});
		
		//----------------------- Toolbar ---------------------------
		
		toolbar = new Toolbar();
		toolbar.setToolbarListener(new ToolbarListener() {
			@Override
			public void saveEventOccurred() {
				connectToDatabase();
				
				try {
					controller.saveToDatabase();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
				}
				
				tablePanel.refresh();
			}

			@Override
			public void refreshEventOccurred() {
				connectToDatabase();
				
				try {
					controller.loadFromDatabase();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
				}
				
				tablePanel.refresh();
			}
		});
		add(toolbar, BorderLayout.NORTH);
		
		//--------------------- Form Panel -------------------------
		
		formPanel = new FormPanel();
		formPanel.setFormListener(new FormListener() {
			@Override
			public void formEventOccured(FormEvent ev) {
				controller.addPerson(ev);
				tablePanel.refresh();
			}
		});
		
		//------------------- Messages Area --------------------------
		
		new JTextArea("Foo");
		
		//---------------- Tree Message Panel ------------------------
		
		messagePanel = new MessagePanel(this);
		
		//------ Tabbed Pane (Table Panel and Message Panel) ---------
		
		tabPane = new JTabbedPane();
		tabPane.add("Person Database", tablePanel);
		tabPane.add("Messages", messagePanel);
		tabPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabPane.getSelectedIndex() == 1)
					messagePanel.refresh();
			}
		});
		
		//-------- Split Pane (Form Panel and Tabbed Pane) ----------
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabPane);
		splitPane.setOneTouchExpandable(true);
		add(splitPane, BorderLayout.CENTER);
		
		//----------------- Preferences Dialog ----------------------
		
		prefs = Preferences.userRoot().node("db");
		
		prefsDialog = new PreferencesDialog(this);
		prefsDialog.setPrefsListener(new PrefsListener() {
			@Override
			public void preferencesSet(String user, String password, int port) {
				prefs.put("user", user);
				prefs.put("password", password);
				prefs.putInt("port", port);
				
				try {controller.configure(port, user, password);} 
				catch (Exception e) {JOptionPane.showMessageDialog(MainFrame.this, "Unable to reconnect to database!", "Reconnection Problem", JOptionPane.ERROR_MESSAGE);}
			}
		});
		
		String user = prefs.get("user", "");
		String password = prefs.get("password", "");
		int port = prefs.getInt("port", 3306);
		
		prefsDialog.setDefaults(user, password, port);
		
		try {controller.configure(port, user, password);}
		catch (Exception e) {JOptionPane.showMessageDialog(MainFrame.this, "Unable to connect to database!", "Connection Problem", JOptionPane.ERROR_MESSAGE);}
		
		//================== Window Settings  ==========================
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.disconnect();
				System.gc();
				System.exit(0);
			}
		});
		
		setJMenuBar(createMenuBar());
		setSize(1200,500);
		setMinimumSize(new Dimension(400, 500));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private JMenuBar createMenuBar() {
				
		//------------------- File Chooser -------------------------------
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());
		
		//------------------- Import Item -------------------------------
		
		JMenuItem importItem = new JMenuItem("Import Data...");
		importItem.setMnemonic(KeyEvent.VK_I);
		importItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
				
		importItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException er) {
						JOptionPane.showMessageDialog(MainFrame.this, 
												"Could not load data from file", "Error", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		//------------------- Export Item -------------------------------
		
		JMenuItem exportItem = new JMenuItem("Export Data...");
		exportItem.setMnemonic(KeyEvent.VK_E);
		exportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
				
		exportItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException er) {
						JOptionPane.showMessageDialog(MainFrame.this, 
												"Could not save data to file", "Error", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		//----------------------- Exit Item -------------------------------
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you wish to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
				
				if (choice == JOptionPane.YES_OPTION) {
					WindowListener[] listeners = getWindowListeners();
					
					for (WindowListener listener : listeners)
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
				}
				
			}
		});
		
		//+++++++++++++++++++++++++ File Menu ++++++++++++++++++++++++++++++++++++++++++
		
		JMenu fileMenu = new JMenu("File");
		
		fileMenu.add(importItem);
		fileMenu.add(exportItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//------------------- Preferences Item ---------------------------------
		
		JMenuItem prefsItem = new JMenuItem("Preferences...");
		
		prefsItem.setMnemonic(KeyEvent.VK_P);
		prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		
		prefsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prefsDialog.setVisible(true);
			}
		});
		
		//------------------- Show Form Item ---------------------------------
		
		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
		showFormItem.setSelected(true);
		
		showFormItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean checked = ((JCheckBoxMenuItem)e.getSource()).isSelected();
				
				formPanel.setVisible(checked);
				
				if (checked)
					splitPane.setDividerLocation((int)formPanel.getMinimumSize().getWidth());
			}
		});
		
		//------------------- Show Toolbar Item ---------------------------------
				
		JCheckBoxMenuItem showToolBarItem = new JCheckBoxMenuItem("Toolbar");
		showToolBarItem.setSelected(true);
		
		showToolBarItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toolbar.setVisible(((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
		});
		
		//+++++++++++++++++++++++++ Show Menu ++++++++++++++++++++++++++++++++++++++++++
		
		JMenu showMenu = new JMenu("Show");		
		showMenu.add(showFormItem);
		showMenu.add(showToolBarItem);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//+++++++++++++++++++++++++ Window Menu ++++++++++++++++++++++++++++++++++++++++
		
		JMenu windowMenu = new JMenu("Window");
		windowMenu.add(prefsItem);
		windowMenu.add(showMenu);	
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//===========================================================================
		//                              Menu Bar
		//===========================================================================
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		return menuBar;
	}
	
	private void connectToDatabase() {
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Unable to connect to database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
		}
	}
}
