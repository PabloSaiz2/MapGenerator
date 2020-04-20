package map.view;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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
		editProvince.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EditDialog dialog = new EditDialog(framedWindowEdit,_ctrl);
				if(dialog.getStatus().equals(DialogStatus.ACCEPTED)) {
					_ctrl.setProvince(dialog.getIndex(),dialog.getProvince());
				}
			}
			
		});
		this.add(editProvince);
		JButton provinceView = new JButton("See provinces");
		provinceView.setToolTipText("See the provinces added");
		provinceView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ProvinceViewDialog(framedWindowView,_ctrl);
			}
			
		});
		this.add(provinceView);
		JButton adjacency = new JButton("Connect provinces");
		adjacency.setToolTipText("Shows dialog to connect the provinces");
		adjacency.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AdjacencyDialog dialogConnect = new AdjacencyDialog(framedWindowConnect,_ctrl);
				if(dialogConnect.getStatus().equals(DialogStatus.ACCEPTED)) {
					_ctrl.addAdjacency(dialogConnect.getProv(), dialogConnect.getAdj());
					_ctrl.addAdjacency(dialogConnect.getAdj(), dialogConnect.getProv());
				}
			}
			
		});
		this.add(adjacency);
		JButton delete = new JButton("Delete provinces");
		delete.setToolTipText("Delete provinces");
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DeleteDialog dialog = new DeleteDialog(framedWindowDelete,_ctrl);
				if(dialog.getStatus().equals(DialogStatus.ACCEPTED)) {
					_ctrl.removeProvince(dialog.getIndex());
				}
			}
			
		});
		this.add(delete);
		JButton clear = new JButton("Clear map");
		clear.setToolTipText("Clears the map");
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_ctrl.reset();
			}
			
		});
		this.add(clear);
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
	public void onProvinceAdded(List<Province> provinces,Province prov) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionAdded(int indexProv, int indexAdj, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNationAdded(String nation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(List<Province> provinces, Set<String> nations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(List<Province> provinces, Set<String> nations) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProvinceDeleted(Province prov,List<Province> provinces) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionDelete(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProvinceEdited(List<Province> provinces) {
		// TODO Auto-generated method stub
		
	}

}
