package map.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import map.controller.Controller;
import map.model.MapGenObserver;
import map.model.Province;

public class ControlPanel extends JPanel implements MapGenObserver {

	private Controller _ctrl;
	private JFrame framedWindowAdder = (JFrame) SwingUtilities.windowForComponent(this);
	private JFrame framedWindowView = (JFrame) SwingUtilities.windowForComponent(this);
	private JFrame framedWindowConnect = (JFrame) SwingUtilities.windowForComponent(this);
	private JFrame framedWindowDelete = (JFrame) SwingUtilities.windowForComponent(this);
	private JFrame framedWindowEdit = (JFrame) SwingUtilities.windowForComponent(this);
	private JFrame framedWindowState = (JFrame) SwingUtilities.windowForComponent(this);
	private JFrame framedWindowLoad = (JFrame) SwingUtilities.windowForComponent(this);
	private ProvinceDialog dialogProvince;
	private JFileChooser fileChooser;
	public ControlPanel(Controller ctrl,MapComponent map) {
		initGUI(map);
		_ctrl = ctrl;
		ctrl.addObserver(this);
	}
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	private void initGUI(MapComponent map) {
		// TODO Auto-generated method stub
		//this.setLayout(new GridLayout(2,14));
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")+"/resources"));
		JButton print = new JButton();
		print.setToolTipText("Print the provinces");
		print.setIcon(new ImageIcon(loadImage("run.png")));
		this.add(print);
		print.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				_ctrl.printProvinces();
			}
			
		});
		JButton province = new JButton();
		province.setToolTipText("Add province");
		province.setText("Add province");
		province.setMaximumSize(new Dimension(100,25));
		this.add(province);
		province.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dialogProvince = new ProvinceDialog(framedWindowAdder,map.getxRectStart(),map.getxRectEnd(),map.getyRectStart(),map.getyRectEnd(),map.getXProv(),map.getYProv());
				dialogProvince.open();
				if(dialogProvince.getStatus().equals(DialogStatus.ACCEPTED)) {
					_ctrl.addProvince(dialogProvince.getProvince());
				}
			}
			
		});
		
		JButton editProvince = new JButton("Edit province");
		editProvince.setToolTipText("Edit a province");
		editProvince.setMaximumSize(new Dimension(100,25));
		editProvince.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EditDialog dialog = new EditDialog(framedWindowEdit,_ctrl);
				if(dialog.getStatus().equals(DialogStatus.ACCEPTED)) {
					_ctrl.setProvince(dialog.getIndex(),dialog.getProvince());
					_ctrl.setSelectedProvince("");
				}
				else if(dialog.getStatus().equals(DialogStatus.CANCEL)) {
					_ctrl.setSelectedProvince("");
				}
			}
			
		});
		this.add(editProvince);
		JButton editState = new JButton("Give state");
		editState.setToolTipText("Change owner of a state");
		editState.setMaximumSize(new Dimension(100,25));
		editState.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StateChangeDialog dialog = new StateChangeDialog(framedWindowState);
				if(dialog.getStatus().equals(DialogStatus.ACCEPTED)) {
					_ctrl.changeOwner(dialog.getState(),dialog.GetOwner());
				}
			}
			
		});
		this.add(editState);
		JButton provinceView = new JButton("See provinces");
		provinceView.setToolTipText("See the provinces added");
		provinceView.setMaximumSize(new Dimension(100,25));
		provinceView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ProvinceViewDialog(framedWindowView,_ctrl);
			}
			
		});
		this.add(provinceView);
		/*JButton adjacency = new JButton("Connect provinces");
		adjacency.setToolTipText("Shows dialog to connect the provinces");
		adjacency.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AdjacencyDialog dialogConnect = new AdjacencyDialog(framedWindowConnect,_ctrl);
				if(dialogConnect.getStatus().equals(DialogStatus.ACCEPTED)) {
					_ctrl.addAdjacency(dialogConnect.getProv(), dialogConnect.getAdj());
				}
			}
			
		});
		this.add(adjacency);*/
		JButton delete = new JButton("Delete provinces");
		delete.setToolTipText("Delete provinces");
		delete.setMaximumSize(new Dimension(100,25));
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DeleteDialog dialog = new DeleteDialog(framedWindowDelete,_ctrl);
				if(dialog.getStatus().equals(DialogStatus.ACCEPTED)) {
					_ctrl.removeProvince(dialog.getName());
				}
			}
			
		});
		this.add(delete);
		/*
		JButton clear = new JButton("Clear map");
		clear.setToolTipText("Clears the map");
		clear.setMaximumSize(new Dimension(100,25));
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_ctrl.reset();
			}
			
		});*/
		JButton toogleNames = new JButton("Toogle bounds & names");
		toogleNames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				map.toogleShow();
			}
			
		});
		this.add(toogleNames);
		JButton connect = new JButton("Connect");
		connect.setMaximumSize(new Dimension(100,25));
		connect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				_ctrl.connectProvinces(map.getxClick(),map.getyClick(),map.getxClick2(),map.getyClick2());
			}
			
		});
		connect.setEnabled(false);
		this.add(connect);
		JButton toogleConnect = new JButton("Toogle connect");
		toogleConnect.setMaximumSize(new Dimension(100,25));
		toogleConnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				map.toogleConnecting();
				connect.setEnabled(!connect.isEnabled());
				province.setEnabled(!province.isEnabled());
			}
			
		});
		this.add(toogleConnect);
		JButton loadData = new JButton("Load Data");
		loadData.setToolTipText("Load Data");
		loadData.setMaximumSize(new Dimension(100,25));
		loadData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoadDialog dialog = new LoadDialog(framedWindowLoad,_ctrl.getCloserProvinceTo(map.getxClick(),map.getyClick()));
				if(dialog.getStatus().equals(DialogStatus.ACCEPTED)) {
					_ctrl.loadDataToProvince(dialog.getProvToChange(),dialog.getDataName());
				}
			}
			
		});
		this.add(loadData);
		//this.add(clear);
		JButton file = new JButton();
		file.setIcon(new ImageIcon(loadImage("open.png")));
		file.setToolTipText("Open from file");
		this.add(file);
		file.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					_ctrl.reset();
					_ctrl.loadFromFile(fileChooser.getSelectedFile());
				}
			}
			
		});
		/*
		JButton zoomIn = new JButton("Zoom in");
		zoomIn.setToolTipText("Zooms in");
		this.add(zoomIn);
		zoomIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				map.zoomIn();
			}
			
		});
		JButton zoomOut = new JButton("Zoom out");
		zoomOut.setToolTipText("Zooms out");
		this.add(zoomOut);
		zoomOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				map.zoomOut();
			}
			
		});*/
		JPanel arrowsPanel = new JPanel(new GridLayout(3,1));
		JPanel leftRightPanel = new JPanel(new GridLayout(1,2));
		JButton up = new JButton("Up");
		up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				map.up();
			}
			
		});
		JButton down = new JButton("Down");
		down.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				map.down();
			}
			
		});
		JButton right = new JButton("Right");
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				map.right();
			}
			
		});
		JButton left = new JButton("Left");
		left.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				map.left();
			}
			
		});
		arrowsPanel.add(up);
		leftRightPanel.add(left);
		leftRightPanel.add(right);
		arrowsPanel.add(leftRightPanel);
		arrowsPanel.add(down);
		this.add(arrowsPanel);
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
