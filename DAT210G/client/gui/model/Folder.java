package gui.model;

public class Folder {
	
	private String name;	
	private int id;
	private int lft;
	private int rgt;
	
	
	public Folder(String name, int id, int lft, int rgt) {
		this.name = name;
		this.id = id;
		this.lft = lft;
		this.rgt = rgt;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getId() {
		return id;
	}


<<<<<<< HEAD
=======
	public void setId(int id) {
		this.id = id;
	}


	public int getLft() {
		return lft;
	}


	public void setLft(int lft) {
		this.lft = lft;
	}


	public int getRgt() {
		return rgt;
	}


	public void setRgt(int rgt) {
		this.rgt = rgt;
	}
	
	
>>>>>>> parent of fb343bd... Ny test

}
