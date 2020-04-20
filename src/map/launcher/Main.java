package map.launcher;

import javax.swing.UIManager;

import map.controller.Controller;
import map.model.MapGenerator;
import map.model.Province;
import map.view.MainWindow;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MapGenerator g = new MapGenerator();
		Controller c = new Controller(g);
		MainWindow m = new MainWindow(c);
	}

}
