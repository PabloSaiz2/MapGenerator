package map.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import map.controller.Controller;

public class StateChangeDialog extends JDialog {
	private DialogStatus status;
	private JTextField state, owner;
	public StateChangeDialog(JFrame ventana) {
		super(ventana, "State edit", true);
		this.setPreferredSize(new Dimension(500, 200));
		status = DialogStatus.WAITING;
		initGUI();
		this.pack();
		this.setVisible(true);

	}
	private void initGUI() {
		// TODO Auto-generated method stub
		state = new JTextField();
		owner = new JTextField();
		this.setLayout(new GridLayout(2,1));
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(1,4));
		JLabel stateLabel = new JLabel("State:");
		JLabel ownerLabel = new JLabel("Owner:");
		dataPanel.add(stateLabel);
		dataPanel.add(state);
		dataPanel.add(ownerLabel);
		dataPanel.add(owner);
		this.add(dataPanel);
		JButton ok = new JButton("Accept");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					status = DialogStatus.ACCEPTED;
					dispose();
			}

		});
		this.add(ok);
	}
	public DialogStatus getStatus() {
		// TODO Auto-generated method stub
		return status;
	}
	public String getState() {
		// TODO Auto-generated method stub
		return state.getText().trim();
	}
	public String GetOwner() {
		// TODO Auto-generated method stub
		return owner.getText().trim();
	}
}
