package map.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import map.controller.Controller;
import map.model.MapGenObserver;
import map.model.Province;
import map.util.SortByName;

public class EditDialog extends JDialog implements MapGenObserver {
	private DialogStatus status;
	private JComboBox provinces;
	private JCheckBox inHECheck;
	private JTextField name, state, owner,resource;
	private JSpinner xCenter, yCenter, xStart, xEnd, yStart, yEnd, devBI, devI, devC, devO, devS;

	public EditDialog(JFrame ventana, Controller ctrl) {
		super(ventana, "Province edit", true);
		this.setPreferredSize(new Dimension(200, 500));
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		status = DialogStatus.WAITING;
		initGUI(ctrl);
		this.pack();
		ctrl.addObserver(this);
		this.setVisible(true);

	}

	public int getIndex() {
		return provinces.getSelectedIndex();
	}

	private JPanel createSidePanel(List<JComponent> components) {
		JPanel p = new JPanel(new GridLayout(1, components.size()));
		for (JComponent component : components)
			p.add(component);
		return p;
	}
	private void setFields() {
		// TODO Auto-generated method stub
		Province prov = (Province)provinces.getSelectedItem();
		name.setText(prov.getName());
		state.setText(prov.getState());
		owner.setText(prov.getOwner());
		resource.setText(prov.getLocalResource());
		xCenter.setValue((Integer)prov.getxCenter());
		yCenter.setValue((Integer)prov.getyCenter());
		xStart.setValue((Integer)prov.getxStart());
		yStart.setValue((Integer)prov.getyStart());
		xEnd.setValue((Integer)prov.getxEnd());
		yEnd.setValue((Integer)prov.getyEnd());
		devBI.setValue((Integer)prov.getDevelopmentBI());
		devC.setValue((Integer)prov.getDevelopmentC());
		devI.setValue((Integer)prov.getDevelopmentI());
		devO.setValue((Integer)prov.getDevelopmentO());
		devS.setValue((Integer)prov.getDevelopmentS());
		inHECheck.setSelected(prov.isInHE());
	}
	private void initGUI(Controller ctrl) {
		// TODO Auto-generated method stub
		JPanel mainPanel = new JPanel(new GridLayout(13, 1));
		this.add(mainPanel);
		provinces = new JComboBox();
		provinces.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setFields();
				ctrl.setSelectedProvince(provinces.getSelectedIndex());
			}

			
			
		});
		mainPanel.add(provinces);
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
		resource = new JTextField();
		componentsToAdd.add(new JLabel("Resource"));
		componentsToAdd.add(resource);
		resource.setToolTipText("The resource gathered in the province");
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
		this.inHECheck = new JCheckBox("In HE?");
		componentsToAdd.add(inHECheck);
		mainPanel.add(createSidePanel(componentsToAdd));
		componentsToAdd.clear();
		JButton ok = new JButton("Ok");

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (provinces.getSelectedItem() != null) {
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
		componentsToAdd.add(ok);
		componentsToAdd.add(cancel);
		mainPanel.add(createSidePanel(componentsToAdd));
	}
	public Province getProvince() {
		Province prov =new Province(name.getText(),state.getText(),owner.getText(),resource.getText(),
				(Integer)devBI.getValue(),(Integer)devC.getValue(),(Integer)devI.getValue(),(Integer)devO.getValue(),(Integer)devS.getValue(),(Integer)xCenter.getValue(),(Integer)yCenter.getValue(),
				(Integer)xStart.getValue(),(Integer)xEnd.getValue(),(Integer)yStart.getValue(),(Integer)yEnd.getValue(),inHECheck.isSelected()); 
		String adjacency = ((Province)provinces.getSelectedItem()).getAdjacencyString();
		for(String adj:adjacency.trim().split(" ")) {
				prov.addAdjacency(adj);
		}
		return prov;
	}
	
	public DialogStatus getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	@Override
	public void onProvinceAdded(Map<String, Province> provinces, Province prov) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionAdded(String indexProv, String indexAdj, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionDelete(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNationAdded(String nation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProvinceEdited(Map<String, Province> provinces) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProvinceDeleted(Province prov, Map<String, Province> provinces) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(Map<String, Province> provinces, Set<String> nations) {
		// TODO Auto-generated method stub
		List<Province> temp = new ArrayList<Province>(provinces.values());
		Collections.sort(temp, new SortByName());
		for (Province prov : temp)
			this.provinces.addItem(prov);
	}

	@Override
	public void onReset(Map<String, Province> provinces, Set<String> nations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSelection(Province province) {
		// TODO Auto-generated method stub
		
	}

	

}
