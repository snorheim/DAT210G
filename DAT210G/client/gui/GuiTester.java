package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GuiTester {
	
	public GuiTester() {
		GUI gui = new GUI();
		
		gui.addSearchBtnListener(new SearchBtnListener());
		gui.addImportBtnListener(new ImportBtnListener());
	}
	
	class SearchBtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {	
			System.out.println("search-button works");			
		}
		
	}
	
	class ImportBtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {	
			System.out.println("import-button works");			
		}
		
	}

	public static void main(String[] args) {
		
		new GuiTester();

	}

}
