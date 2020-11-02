package map.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import map.controller.Controller;
import map.model.Province;

public class LoadDialog extends JDialog{
	private JTextField nameData;
	private String provToChange;
	DialogStatus status;
	public LoadDialog(JFrame ventana, Province prov) {
		super(ventana, "Province load", true);
		this.setLayout(new GridLayout(4,1));
		this.setPreferredSize(new Dimension(200, 200));
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		status = DialogStatus.WAITING;
		provToChange = prov.getName();
		System.out.println("Gol de señor");
		nameData = new JTextField();
		this.add(new JLabel(provToChange));
		this.add(nameData);
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!nameData.getText().trim().equals("")) {
					status = DialogStatus.ACCEPTED;
					dispose();
				}
			}

		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				status = DialogStatus.CANCEL;
				dispose();
			}

		});
		this.add(ok);
		this.add(cancel);
		this.pack();
		this.setVisible(true);

	}
	public String getProvToChange() {
		return provToChange;
	}
	public String getDataName() {
		return nameData.getText().trim();
	}
	public Object getStatus() {
		// TODO Auto-generated method stub
		return status;
	}
}
