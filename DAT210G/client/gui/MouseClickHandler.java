package gui;

import javax.naming.ldap.Control;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Ronnie on 15.02.14.
 */
public class MouseClickHandler implements MouseListener {

	private Controller controller;

	public MouseClickHandler(Controller controller) {

		this.controller = controller;

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		OneImage temp = (OneImage) e.getSource();

		int tempId = temp.getImageId();

		System.out.println("Clicked image with id: " + tempId);

		controller.setSingleImageMode(tempId);

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
