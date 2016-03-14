package com.chrisali.peopledatabase.view.servertreecell;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

import com.chrisali.peopledatabase.view.servertreecell.ServerInfo;

public class ServerTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {

	private static final long serialVersionUID = 7548654707160692958L;

	private ServerTreeCellRenderer renderer;
	private JCheckBox checkBox;
	private ServerInfo serverInfo;
	
	public ServerTreeCellEditor() {
		renderer = new ServerTreeCellRenderer();
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf,
			int row) {
		Component component = renderer.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, true);
		
		if (leaf) {
			checkBox = (JCheckBox)component;
			serverInfo = (ServerInfo)((DefaultMutableTreeNode)value).getUserObject();
			
			ItemListener itemListener = new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					fireEditingStopped();
					checkBox.removeItemListener(this);
				}
			};
			
			checkBox.addItemListener(itemListener);
		}
		return component;
	}
	
	@Override
	public Object getCellEditorValue() {
		serverInfo.setChecked(checkBox.isSelected());
		return serverInfo;
	}

	@Override
	public boolean isCellEditable(EventObject event) {
		if (! (event instanceof MouseEvent)) return false;
		MouseEvent mouseEvent = (MouseEvent)event;

		TreePath path = ((JTree)event.getSource()).getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
		if (path == null) return false;
		
		Object lastComponent = path.getLastPathComponent();
		if (lastComponent == null) return false;
		return ((DefaultMutableTreeNode)lastComponent).isLeaf();
	}
}
