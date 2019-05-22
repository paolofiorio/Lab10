package it.polito.tdp.porto.model;

import java.util.List;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	private PortoDAO dao;

	private List<Author> autori;
	
	public List<Author> getAutori() {
		if(this.autori==null) {
			dao= new PortoDAO();
			this.autori= dao.listAutori();
		}
		return this.autori;
	}

}
