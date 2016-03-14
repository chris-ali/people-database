package com.chrisali.peopledatabase.view.messagelist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.chrisali.peopledatabase.model.messagedatabase.Message;
import com.chrisali.peopledatabase.model.messagedatabase.MessageServer;
import com.chrisali.peopledatabase.view.progressbar.ProgressDialog;
import com.chrisali.peopledatabase.view.progressbar.ProgressDialogListener;
import com.chrisali.peopledatabase.view.servertreecell.ServerInfo;
import com.chrisali.peopledatabase.view.servertreecell.ServerTreeCellEditor;
import com.chrisali.peopledatabase.view.servertreecell.ServerTreeCellRenderer;

public class MessagePanel extends JPanel implements ProgressDialogListener {

	private static final long serialVersionUID = 1L;
	
	private JTree serverTree;
	private ServerTreeCellRenderer treeCellRenderer;
	private ServerTreeCellEditor treeCellEditor;
	private ProgressDialog progressDialog;
	private SwingWorker<List<Message>, Integer> worker;
	private Set<Integer> selectedServers;
	private MessageServer messageServer;
	private TextPanel textPanel;
	private JList<Message> messageList;
	private DefaultListModel<Message> listModel;
	private JSplitPane upperPane;
	private JSplitPane lowerPane;
	
	public MessagePanel(JFrame parent) {
		
		setLayout(new BorderLayout());
		
		//------------------------ Message "Server" -----------------------------------
		
		messageServer = new MessageServer();
		selectedServers = new TreeSet<Integer>();
		selectedServers.add(0);
		selectedServers.add(1);
		selectedServers.add(2);
		selectedServers.add(5);
		messageServer.setSelectedServers(selectedServers);
		
		//------------------------ Progress Dialog -----------------------------------
		
		progressDialog = new ProgressDialog(parent, "Messages Downloading");
		progressDialog.setProgressDialogListener(this);
				
		//----------------- Tree Cell Renderer and Editor ----------------------------
		
		treeCellRenderer = new ServerTreeCellRenderer();
		treeCellEditor = new ServerTreeCellEditor();
		treeCellEditor.addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				ServerInfo serverInfo = ((ServerInfo)treeCellEditor.getCellEditorValue());
				int serverId = serverInfo.getId();

				if (serverInfo.isChecked())
					selectedServers.add(serverId);
				else
					selectedServers.remove(serverId);
				
				messageServer.setSelectedServers(selectedServers);
				
				retrieveMessages();
			}
			
			@Override
			public void editingCanceled(ChangeEvent e) {}
		});
		
		//----------------------- Server Tree ----------------------------------

		serverTree = new JTree(createTree());
		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		serverTree.setEditable(true);
		serverTree.setCellRenderer(treeCellRenderer);
		serverTree.setCellEditor(treeCellEditor);
		
		//----------------------- Text Panel  ----------------------------------
		
		textPanel = new TextPanel();
		textPanel.setMinimumSize(new Dimension(400, 150));
		
		//---------------------- Messages List ---------------------------------
		
		listModel = new DefaultListModel<Message>();
		messageList = new JList<Message>(listModel);
		messageList.setMinimumSize(new Dimension(400, 150));
		messageList.setCellRenderer(new MessageListRenderer());
		messageList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				textPanel.setText(messageList.getSelectedValue().getContents());
			}
		});
						
		//------------------------ Lower Pane ----------------------------------
		
		lowerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(messageList), textPanel);
		lowerPane.setResizeWeight(0.5);
		
		//------------------------ Upper Pane ----------------------------------
		
		upperPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(serverTree), lowerPane);
		upperPane.setResizeWeight(0.5);
		add(upperPane, BorderLayout.CENTER);
	}
	
	private void retrieveMessages() {
		
		progressDialog.setMaximum(messageServer.getMessageCount());
		progressDialog.setVisible(true);
		
		worker = new SwingWorker<List<Message>, Integer>() {
			@Override
			protected void done() {
				progressDialog.setVisible(false);
				
				if(isCancelled()) return;
				
				try {
					List<Message> retrievedMessages = get();
					listModel.removeAllElements();
					
					for (Message message : retrievedMessages)
						listModel.addElement(message);
					
					messageList.setSelectedIndex(0);
					
				} catch (InterruptedException | ExecutionException e) {
					System.err.println("Interrupted!");
				}
			}

			@Override
			protected void process(List<Integer> counts) {
				int retrieved = counts.get(counts.size()-1);
				progressDialog.setValue(retrieved);
			}
			
			@Override
			protected List<Message> doInBackground() throws Exception {
				List<Message> retrievedMessages = new ArrayList<Message>();
				int count = 0;
				
				for (Message message : messageServer) {
					if(isCancelled()) break;
					
					retrievedMessages.add(message);
					
					count++;	
					publish(count);
				}
				
				return retrievedMessages;
			}
		};
		
		worker.execute();
	}
	
	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");
		
		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
		top.add(branch1);
		DefaultMutableTreeNode node11 = new DefaultMutableTreeNode(new ServerInfo("New York", 0, selectedServers.contains(0)));
		branch1.add(node11);
		DefaultMutableTreeNode node12 = new DefaultMutableTreeNode(new ServerInfo("Austin", 1, selectedServers.contains(1)));
		branch1.add(node12);
		DefaultMutableTreeNode node13 = new DefaultMutableTreeNode(new ServerInfo("Los Angeles", 2, selectedServers.contains(2)));
		branch1.add(node13);
		
		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
		top.add(branch2);
		DefaultMutableTreeNode node21 = new DefaultMutableTreeNode(new ServerInfo("London", 3, selectedServers.contains(3)));
		branch2.add(node21);
		DefaultMutableTreeNode node22 = new DefaultMutableTreeNode(new ServerInfo("Edinburgh", 4, selectedServers.contains(4)));
		branch2.add(node22);
		DefaultMutableTreeNode node23 = new DefaultMutableTreeNode(new ServerInfo("Dublin", 5, selectedServers.contains(5)));
		branch2.add(node23);
		
		return top;
	}
	
	public void refresh() {
		retrieveMessages();
	}

	@Override
	public void ProgressDialogCancelled() {
		worker.cancel(true);
	}
}
