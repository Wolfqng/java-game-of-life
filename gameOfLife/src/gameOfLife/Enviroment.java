package gameOfLife;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Enviroment extends JPanel {
	private static final long serialVersionUID = 1L;
	public static JFrame f = new JFrame("Game of Life");
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public final static int blockSize = 10;
	public static boolean map[][];
	
	public void paintComponent(Graphics g) {
		for(int i = 0; i < WIDTH / blockSize; i++) {
			for(int j = 0; j < HEIGHT / blockSize; j++) {
				g.setColor(Color.BLACK);
				if(!map[i][j])
					g.drawRect(i * blockSize, j * blockSize, blockSize, blockSize);
				else
					g.fillRect(i * blockSize, j * blockSize, blockSize, blockSize);
			}
		}
		
	}
	
	public static void main(String[] args) {
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	f.setBackground(new Color(20, 20, 20));
    	f.setSize(WIDTH + 16, HEIGHT + 39);
        f.setVisible(true);
        f.add(new Enviroment());
        f.setBackground(Color.WHITE);
        
        map = new boolean[WIDTH / blockSize][HEIGHT / blockSize];
        initializeMap();
        f.repaint();
        Object monitor = new Object();
        synchronized(monitor) {
            while(true) {
                try{Thread.sleep(100);}catch(InterruptedException ex){Thread.currentThread().interrupt();}
                updateMap();
                f.repaint();
            }
        }
        
    }
	
	public static void initializeMap() {
		int count = 0;
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if((int)(Math.random() * 1000) > 700 && count < 2000) {
					map[i][j] = true;
					count++;
				}
				else map[i][j] = false;
			}
		}
	}
	
	public static void updateMap() {
		boolean newMap[][] = new boolean[map.length][map[0].length];
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				//Neighbor stuff
				boolean state = map[i][j];
				int neighbors = getNeighborCount(i, j);
				if(!state && neighbors == 3) newMap[i][j] = true;
				else if(state && (neighbors < 2 || neighbors > 3)) newMap[i][j] = false; 
				else newMap[i][j] = map[i][j];
			}
		}
		
		map = newMap;
	}
	
	public static int getNeighborCount(int x, int y) {
		int count = 0;
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if((x + i < 0 || x + i >= map.length || y + j < 0 || y + j >= map[0].length) || (x + i == x && y + j == y)) continue;
				if(map[x + i][y + j] == true) count++;
			}
		}
		
		return count;
	}
	
}
