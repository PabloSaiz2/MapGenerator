package map.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import map.controller.Controller;
import map.model.MapGenObserver;
import map.model.Province;
import map.util.Pair;
import java.awt.color.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapComponent extends JComponent implements MapGenObserver {
	private Map<String,Province> provinces;
	private Set<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> connections;
	private Province selectedProvince;
	private static final int PROV_RADIUS = 5;
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _PROVINCE_COLOR = Color.GRAY;
	private static final Color _PROVINCE_LABEL_COLOR = Color.BLUE;
	private static final Color _PROVINCE_LABEL_COLOR_HE = Color.RED;
	boolean onMap;
	boolean showRectanglesAndNames;
	boolean connecting;
	boolean selectingFirst;
	private Image _map;
	private int x, y;
	private int xClick, yClick, xClick2, yClick2;
	private int rectW, rectH;
	private int xRectStart, xRectEnd, yRectStart, yRectEnd;
	private int zoomLevel;

	public MapComponent(Controller ctrl) {
		x = 0;
		y = 0;
		selectedProvince = null;
		zoomLevel = 1;
		showRectanglesAndNames = true;
		xClick = 0;
		xClick2 = 0;
		yClick2 = 0;
		connecting = false;
		selectingFirst = true;
		rectW = 0;
		rectH = 0;
		xRectStart = 0;
		xRectEnd = 0;
		yRectStart = 0;
		yRectEnd = 0;
		yClick = 0;
		onMap = false;
		this.setPreferredSize(new Dimension(1200, 550));
		provinces = new HashMap<String,Province>();
		connections = new HashSet<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>>();
		initGUI();
		ctrl.addObserver(this);
	}

	public void toogleShow() {
		showRectanglesAndNames = !showRectanglesAndNames;
		repaint();
	}

	public void toogleConnecting() {
		connecting = !connecting;
		repaint();
	}

	public int getxClick() {
		return xClick - x;
	}

	public int getyClick() {
		return yClick - y;
	}

	public int getxClick2() {
		return xClick2 - x;
	}

	public int getyClick2() {
		return yClick2 - y;
	}

	public int getxRectStart() {
		return xRectStart - x;
	}

	public int getxRectEnd() {
		return xRectEnd - x;
	}

	public int getyRectStart() {
		return yRectStart - y;
	}

	public int getyRectEnd() {
		return yRectEnd - y;
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		_map = loadImage("provinceMap.png");
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (!connecting) {
					xClick = arg0.getX();
					yClick = arg0.getY();
				} else {
					if (selectingFirst) {
						selectingFirst = false;
						xClick = arg0.getX();
						yClick = arg0.getY();
					} else {
						selectingFirst = true;
						xClick2 = arg0.getX();
						yClick2 = arg0.getY();
					}
				}
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				// onMap = true;
				// repaint();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				// onMap = false;
				// repaint();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				xRectStart = arg0.getX();
				yRectStart = arg0.getY();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				xRectEnd = arg0.getX();
				yRectEnd = arg0.getY();
				rectW = Math.abs(xRectEnd - xRectStart);
				rectH = Math.abs(yRectEnd - yRectStart);
				repaint();
			}

		});
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (provinces == null || provinces.size() == 0) {
			// g.setColor(Color.red);
			// g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
			drawMap(g);
		} else {
			drawMap(g);
		}
	}

	public void zoomIn() {
		++zoomLevel;
		x = (int) ((double) x * ((double) zoomLevel / ((double) zoomLevel - 1.0)));
		y = (int) ((double) y * ((double) zoomLevel / ((double) zoomLevel - 1.0)));
		repaint();
	}

	public void zoomOut() {
		if (zoomLevel > 1) {
			--zoomLevel;
			x = (int) ((double) x * ((double) zoomLevel / ((double) zoomLevel + 1.0)));
			y = (int) ((double) y * ((double) zoomLevel / ((double) zoomLevel + 1.0)));
		}
		repaint();
	}

	private void drawMap(Graphics g) {
		g.setFont(new Font("Dialog", Font.PLAIN, 10));
		g.drawImage(_map, x, y, _map.getWidth(this) * zoomLevel, _map.getHeight(this) * zoomLevel, this);
		if (!connecting) {
			drawCrossHair(g);
			drawRectangle(g);
		} else {
			drawConnectionPoints(g);
		}
		drawConnections(g);
		drawProvinces(g);
		drawRectanglesProvs(g);

	}

	private void drawConnectionPoints(Graphics g) {
		g.setColor(Color.ORANGE);
		if (selectingFirst)
			g.drawString("Choose the first point", 50, 50);
		else
			g.drawString("Choose the second point", 50, 50);

		g.setColor(Color.BLACK);
		g.drawLine(xClick2, yClick2 + 25, xClick2, yClick2 - 25);
		g.drawLine(xClick2 - 25, yClick2, xClick2 + 25, yClick2);
		g.drawLine(xClick, yClick + 25, xClick, yClick - 25);
		g.drawLine(xClick - 25, yClick, xClick + 25, yClick);
	}

	private void drawRectanglesProvs(Graphics g) {
		
			g.setColor(Color.GREEN);
			if (selectedProvince != null) {
				g.drawRect(selectedProvince.getxStart() + x, selectedProvince.getyEnd() + y, Math.abs(selectedProvince.getxEnd() - selectedProvince.getxStart()),
						Math.abs(selectedProvince.getyEnd() - selectedProvince.getyStart()));
			}
			// TODO Auto-generated method stub
		
	}

	private void drawRectangle(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.ORANGE);
		g.drawRect(xRectStart, yRectStart, rectW, rectH);
	}

	private void drawCrossHair(Graphics g) {
		// TODO Auto-generated method stub
		// if(onMap) {
		g.setColor(Color.BLACK);
		g.drawLine(xClick, 0, xClick, getHeight());
		g.drawLine(0, yClick, getWidth(), yClick);
		g.drawString("(" + ((Integer) (xClick - x)).toString() + "," + ((Integer) (yClick - y)).toString() + ")",
				xClick, yClick);
		// }
	}

	private void drawConnections(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);
		for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> connection : connections) {
			double factorH1 = (double) connection.getFirst().getSecond() / (double) _map.getHeight(this);
			double factorW1 = (double) connection.getFirst().getFirst() / (double) _map.getWidth(this);
			double factorH2 = (double) connection.getSecond().getSecond() / (double) _map.getHeight(this);
			double factorW2 = (double) connection.getSecond().getFirst() / (double) _map.getWidth(this);
			// g.drawLine((int)(getWidth()*factorW1),(int)(getHeight()*factorH1),(int)(getWidth()*factorW2),
			// (int)(getHeight()*factorH2));
			g.drawLine(connection.getFirst().getFirst() + x, connection.getFirst().getSecond() + y,
					connection.getSecond().getFirst() + x, connection.getSecond().getSecond() + y);
		}
	}

	private void drawProvinces(Graphics g) {
		// TODO Auto-generated method stub

		for (Province prov : provinces.values()) {
			g.setColor(_PROVINCE_COLOR);
			double factorH = (double) prov.getyCenter() / (double) _map.getHeight(this);
			double factorW = (double) prov.getxCenter() / (double) _map.getWidth(this);
			// g.fillOval((int)((getWidth()*factorW)-(PROV_RADIUS/2)),
			// (int)((getHeight()*factorH)-(PROV_RADIUS/2)), PROV_RADIUS, PROV_RADIUS);
			g.fillOval(prov.getxCenter() - (PROV_RADIUS / 2) + x, prov.getyCenter() - (PROV_RADIUS / 2) + y,
					PROV_RADIUS, PROV_RADIUS);
			// g.drawString(prov.getName(), (int)((getWidth()*factorW)-(PROV_RADIUS/2)),
			// (int)((getHeight()*factorH)-(PROV_RADIUS/2)));
			if (!prov.isInHE())
				g.setColor(_PROVINCE_LABEL_COLOR);
			else
				g.setColor(_PROVINCE_LABEL_COLOR_HE);
			if (showRectanglesAndNames||prov.equals(selectedProvince)) {
				g.drawString(prov.getName(), prov.getxCenter() - (PROV_RADIUS / 2) + x,
						prov.getyCenter() - (PROV_RADIUS / 2) + y);
			}
		}
	}

	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/maps/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	private void removeConnection(int x1, int y1, int x2, int y2) {
		connections.removeIf(
				connection -> connection.getFirst().getFirst() == x1 && connection.getFirst().getSecond() == y1
						&& connection.getSecond().getFirst() == x2 && connection.getSecond().getSecond() == y2);
		connections.removeIf(
				connection -> connection.getFirst().getFirst() == x2 && connection.getFirst().getSecond() == y2
						&& connection.getSecond().getFirst() == x1 && connection.getSecond().getSecond() == y1);
	}

	@Override
	public void onProvinceAdded(Map<String,Province> provinces, Province prov) {
		// TODO Auto-generated method stub
		this.provinces = provinces;
		repaint();

	}

	@Override
	public void onConnectionAdded(String indexProv, String indexAdj, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		Pair<Integer, Integer> firstPoint = new Pair<Integer, Integer>(x1, y1);
		Pair<Integer, Integer> secondPoint = new Pair<Integer, Integer>(x2, y2);

		Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> connection = new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>(
				firstPoint, secondPoint);
		connections.add(connection);
		repaint();
	}

	@Override
	public void onNationAdded(String nation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(Map<String,Province> provinces, Set<String> nations) {
		// TODO Auto-generated method stub
		this.provinces = provinces;
		connections.clear();
		x = 0;
		y = 0;
		repaint();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(Map<String,Province> provinces, Set<String> nations) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProvinceDeleted(Province prov, Map<String,Province> provinces) {
		// TODO Auto-generated method stub
		this.provinces = provinces;

		repaint();
	}

	@Override
	public void onConnectionDelete(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		removeConnection(x1, y1, x2, y2);
	}

	@Override
	public void onProvinceEdited(Map<String,Province> provinces) {
		// TODO Auto-generated method stub
		this.provinces = provinces;
		repaint();
	}

	public void up() {
		// TODO Auto-generated method stub
		y += 50;
		repaint();
	}

	public void down() {
		y -= 50;
		repaint();
	}

	public void left() {
		x += 50;
		repaint();
	}

	public void right() {
		x -= 50;
		repaint();
	}

	public int getYProv() {
		// TODO Auto-generated method stub
		return yClick - y;
	}

	public int getXProv() {
		// TODO Auto-generated method stub
		return xClick - x;
	}

	@Override
	public void onSelection(Province province) {
		// TODO Auto-generated method stub
		selectedProvince = province;
		repaint();
	}
}
