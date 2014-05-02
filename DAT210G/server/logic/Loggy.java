package logic;

import gui.Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import communication.HttpServer;

public class Loggy extends JFrame implements ActionListener {

	public static final int SERVER = 0, RQ_METHODS = 1, FILE_WATCHER = 2,
			IMG_HANDLER = 3, DB_READ = 4, DB_WRITE = 5, DB_UPDATE = 6,
			DB_DELETE = 7, EXIF_READ = 8, EXIF_WRITE = 9;

	private static final long serialVersionUID = 1L;

	static final Dimension SIZE = new Dimension(500, 500);
	static JTextArea console;
	static ArrayList<LogEntry> log;
	static JScrollPane scrollpane;

	private static Loggy instance;

	private JPanel filterPanel;
	private static JCheckBox filterserver;
	private static JCheckBox filterrq;
	private static JCheckBox filterfw;
	private static JCheckBox filterimgh;
	private static JCheckBox filterdbr;
	private static JCheckBox filterdbw;
	private static JCheckBox filterdbu;
	private static JCheckBox filterdbd;
	private static JCheckBox filterexifr;
	private static JCheckBox filterexifw;

	private static JButton clearLog;

	private Loggy() {

		log = new ArrayList<>();

		setLayout(new BorderLayout());

		console = new JTextArea();
		console.setEditable(false);
		console.setAutoscrolls(true);

		scrollpane = new JScrollPane(console);
		scrollpane.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					public void adjustmentValueChanged(AdjustmentEvent e) {
						e.getAdjustable().setValue(
								e.getAdjustable().getMaximum());
					}
				});
		add(scrollpane, BorderLayout.CENTER);

		filterPanel = new JPanel();
		filterPanel.setLayout(new GridLayout(11, 1));
		{
			filterserver = new JCheckBox("Server");
			filterserver.setSelected(true);
			filterserver.addActionListener(this);
			filterrq = new JCheckBox("Request Methods");
			filterrq.setSelected(true);
			filterrq.addActionListener(this);
			filterfw = new JCheckBox("Filewatcher");
			filterfw.setSelected(true);
			filterfw.addActionListener(this);
			filterimgh = new JCheckBox("Imagehandler");
			filterimgh.setSelected(true);
			filterimgh.addActionListener(this);
			filterdbr = new JCheckBox("DB Read");
			filterdbr.setSelected(true);
			filterdbr.addActionListener(this);
			filterdbw = new JCheckBox("DB Write");
			filterdbw.setSelected(true);
			filterdbw.addActionListener(this);
			filterdbu = new JCheckBox("DB Update");
			filterdbu.setSelected(true);
			filterdbu.addActionListener(this);
			filterdbd = new JCheckBox("DB Delete");
			filterdbd.setSelected(true);
			filterdbd.addActionListener(this);
			filterexifr = new JCheckBox("Exif Read");
			filterexifr.setSelected(true);
			filterexifr.addActionListener(this);
			filterexifw = new JCheckBox("Exif Write");
			filterexifw.setSelected(true);
			filterexifw.addActionListener(this);

			clearLog = new JButton("Clear log");
			clearLog.addActionListener(this);

			filterPanel.add(filterserver);
			filterPanel.add(filterrq);
			filterPanel.add(filterfw);
			filterPanel.add(filterimgh);
			filterPanel.add(filterdbr);
			filterPanel.add(filterdbw);
			filterPanel.add(filterdbu);
			filterPanel.add(filterdbd);
			filterPanel.add(filterexifr);
			filterPanel.add(filterexifw);
			filterPanel.add(clearLog);
		}
		add(filterPanel, BorderLayout.SOUTH);

		setMinimumSize(SIZE);
		setPreferredSize(SIZE);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private static void clearLog() {
		log.clear();
		oppdaterLogg();
	}

	private void addLogEntry(String message, int source) {
		log.add(new LogEntry(message, source));
		oppdaterLogg();
	}

	private static void oppdaterLogg() {
		StringBuilder sb = new StringBuilder();
		for (LogEntry le : log) {
			String sourceName = null;
			int source = le.getSource();
			switch (source) {
			case SERVER:
				if (!filterserver.isSelected())
					continue;
				sourceName = "SERVER";
				break;
			case RQ_METHODS:
				if (!filterrq.isSelected())
					continue;
				sourceName = "REQUEST";
				break;
			case FILE_WATCHER:
				if (!filterfw.isSelected())
					continue;
				sourceName = "FILEW";
				break;
			case IMG_HANDLER:
				if (!filterimgh.isSelected())
					continue;
				sourceName = "IMGHA";
				break;
			case DB_READ:
				if (!filterdbr.isSelected())
					continue;
				sourceName = "DB_R";
				break;
			case DB_WRITE:
				if (!filterdbw.isSelected())
					continue;
				sourceName = "DB_W";
				break;
			case DB_UPDATE:
				if (!filterdbu.isSelected())
					continue;
				sourceName = "DB_U";
				break;
			case DB_DELETE:
				if (!filterdbd.isSelected())
					continue;
				sourceName = "DB_D";
				break;
			case EXIF_READ:
				if (!filterexifr.isSelected())
					continue;
				sourceName = "EXIF_R";
				break;
			case EXIF_WRITE:
				if (!filterexifw.isSelected())
					continue;
				sourceName = "EXIF_W";
				break;
			}
			sb.append(sourceName + ":\t" + le.getMessage() + "\n");
		}
		console.setText(sb.toString());
	}

	public static void log(String message, int source) {
		if (console == null) {
			System.out.println(message);
		} else {
			getInstance().addLogEntry(message, source);
		}
	}

	public static void main(String[] args) {
		getInstance();
		Thread t2 = new Thread() {
			public void run() {
				HttpServer.main(null);
			}
		};
		t2.start();

		for (int i = 0; i < 300000; i++)
			System.out.println("VENTER " + i);

		Thread t1 = new Thread() {
			public void run() {
				Main.main(null);
			}
		};
		t1.start();

		while (true) {
			if (!t1.isAlive() || !t2.isAlive()) {
				if (!t1.isAlive())
					t1.stop();
				if (!t2.isAlive())
					t2.stop();
				System.exit(0);
			}
		}
	}

	private class LogEntry {
		String message;
		int source;

		public LogEntry(String message, int source) {
			this.message = message;
			this.source = source;
		}

		public String getMessage() {
			return message;
		}

		public int getSource() {
			return source;
		}
	}

	public static Loggy getInstance() {
		if (instance == null) {
			instance = new Loggy();
		}
		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == clearLog) {
			clearLog();
		}
		oppdaterLogg();
	}
}
