package map.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import map.model.Province;

public class ProvinceDialog extends JDialog{
	DialogStatus status;
	JTextField name,state,owner;
	JSpinner xCenter,yCenter,xStart,xEnd,yStart,yEnd,devBI,devI,devC,devO,devS;
	public ProvinceDialog(JFrame ventana,int rectXStart,int rectXEnd,int rectYStart,int rectYEnd,int x,int y) {
		super(ventana,"Province adder",true);
		this.setPreferredSize(new Dimension(200,500));
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		status = DialogStatus.WAITING;
		initGUI();
		xCenter.setValue((Integer)x);
		yCenter.setValue((Integer)y);
		xStart.setValue((Integer)rectXStart);
		xEnd.setValue((Integer)rectXEnd);
		yStart.setValue((Integer)rectYEnd);
		yEnd.setValue((Integer)rectYStart);
		
		this.pack();
	}
	public DialogStatus getStatus() {
		return status;
	}
	private JPanel createSidePanel(List<JComponent> components) {
		JPanel p = new JPanel(new GridLayout(1,components.size()));
		for(JComponent component:components)
			p.add(component);
		return p;
	}
	public Province getProvince() {
		return new Province(name.getText(),state.getText(),owner.getText(),
				(Integer)devBI.getValue(),(Integer)devC.getValue(),(Integer)devI.getValue(),(Integer)devO.getValue(),(Integer)devS.getValue(),(Integer)xCenter.getValue(),(Integer)yCenter.getValue(),
				(Integer)xStart.getValue(),(Integer)xEnd.getValue(),(Integer)yStart.getValue(),(Integer)yEnd.getValue());
	}
	private void initGUI() {
		// TODO Auto-generated method stub
		JPanel mainPanel = new JPanel(new GridLayout(10,1));
		this.add(mainPanel);
		
		name = new JTextField();
		name.setToolTipText("The name of the province");
		List<JComponent> componentsToAdd = new ArrayList<JComponent>();
		componentsToAdd.add(new JLabel("Name"));
		componentsToAdd.add(name);
		mainPanel.add(createSidePanel(componentsToAdd));
		componentsToAdd.clear();
		state = new JTextField();
		componentsToAdd.add(new JLabel("State"));
		componentsToAdd.add(state);
		state.setToolTipText("The state of the province");
		mainPanel.add(createSidePanel(componentsToAdd));
		componentsToAdd.clear();
		owner = new JTextField();
		componentsToAdd.add(new JLabel("Owner"));
		componentsToAdd.add(owner);
		owner.setToolTipText("The owner of the province");
		mainPanel.add(createSidePanel(componentsToAdd));
		componentsToAdd.clear();
		mainPanel.add(new JLabel("Center"));
		SpinnerNumberModel x = new SpinnerNumberModel(1, 0, 10000, 1);
		xCenter = new JSpinner(x);
		xCenter.setToolTipText("The x coordinate of the center");
		SpinnerNumberModel y = new SpinnerNumberModel(1, 0, 10000, 1);
		yCenter = new JSpinner(y);
		yCenter.setToolTipText("The y coordinate of the center");
		componentsToAdd.add(xCenter);
		componentsToAdd.add(yCenter);
		mainPanel.add(createSidePanel(componentsToAdd));
		componentsToAdd.clear();
		mainPanel.add(new JLabel("Bounds"));
		SpinnerNumberModel x1 = new SpinnerNumberModel(1, 0, 10000, 1);
		xStart = new JSpinner(x1);
		xStart.setToolTipText("Bound x start");
		SpinnerNumberModel x2 = new SpinnerNumberModel(1, 0, 10000, 1);
		xEnd = new JSpinner(x2);
		xEnd.setToolTipText("Bound x end");
		SpinnerNumberModel y1 = new SpinnerNumberModel(1, 0, 10000, 1);
		yStart = new JSpinner(y1);
		yStart.setToolTipText("Bound y start");
		SpinnerNumberModel y2 = new SpinnerNumberModel(1, 0, 10000, 1);
		yEnd = new JSpinner(y2);
		yEnd.setToolTipText("Bound y end");
		componentsToAdd.add(xStart);
		componentsToAdd.add(xEnd);
		componentsToAdd.add(yStart);
		componentsToAdd.add(yEnd);
		mainPanel.add(createSidePanel(componentsToAdd));
		componentsToAdd.clear();
		mainPanel.add(new JLabel("Development"));
		SpinnerNumberModel dBI = new SpinnerNumberModel(1, 0, 100, 1);
		devBI = new JSpinner(dBI);
		devBI.setToolTipText("Base imponible");
		SpinnerNumberModel dC = new SpinnerNumberModel(1, 0, 100, 1);
		devC = new JSpinner(dC);
		devC.setToolTipText("Comercio");
		SpinnerNumberModel dI = new SpinnerNumberModel(1, 0, 100, 1);
		devI = new JSpinner(dI);
		devI.setToolTipText("Industria");
		SpinnerNumberModel dO = new SpinnerNumberModel(1, 0, 100, 1);
		devO = new JSpinner(dO);
		devO.setToolTipText("Mano de obra");
		SpinnerNumberModel dS = new SpinnerNumberModel(1, 0, 100, 1);
		devS = new JSpinner(dS);
		devS.setToolTipText("Soldadesca");
		componentsToAdd.add(devBI);
		componentsToAdd.add(devC);
		componentsToAdd.add(devI);
		componentsToAdd.add(devO);
		componentsToAdd.add(devS);
		mainPanel.add(createSidePanel(componentsToAdd));
		componentsToAdd.clear();
		JButton ok = new JButton("Ok");
		
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				status = DialogStatus.ACCEPTED;
				close();
			}
			
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				status = DialogStatus.CANCEL;
				close();
			}
			
		});
		componentsToAdd.add(ok);
		componentsToAdd.add(cancel);
		mainPanel.add(createSidePanel(componentsToAdd));
	}
	public void close() {
		this.dispose();
	}
	public void open() {
		status = DialogStatus.WAITING;
		this.setVisible(true);
	}
}
