package logic;

import gui.Main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

import communication.HttpServer;

public class Loggy extends JFrame {

	static final Dimension SIZE = new Dimension(500, 340);
	static JTextArea console;
	static JScrollBar scroll;

	public Loggy() {

		setLayout(new BorderLayout());

		console = new JTextArea();
		console.setEditable(false);
		console.setAutoscrolls(true);
		add(console, BorderLayout.CENTER);

		scroll = new JScrollBar(JScrollBar.VERTICAL);
		add(scroll, BorderLayout.EAST);

		setMinimumSize(SIZE);
		setPreferredSize(SIZE);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void log(String s) {
		console.setText(console.getText() + "\n" + s);
	}

	public static void main(String[] args) {
		new Loggy();
		new Thread() {
			public void run() {
				Main.main(null);
			}
		}.start();
		new Thread() {
			public void run() {
				HttpServer.main(null);
			}
		}.start();
	}
}
