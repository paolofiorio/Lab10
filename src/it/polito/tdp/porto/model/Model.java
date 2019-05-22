package it.polito.tdp.porto.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	private PortoDAO dao;
	private List<Author> autori;
	
	private SimpleGraph<Author, DefaultEdge> grafo;
	
	public Model() {
		
	}	
	public List<Author> getAutori() {
		if(this.autori==null) {
			dao= new PortoDAO();
			this.autori= dao.listAutori();
		}
		return this.autori;
	}
	
	public void creaGrafo() {
		dao= new PortoDAO();
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.grafo, autori);
		
		for(Author a : grafo.vertexSet()) {
			List<Author> coautori = dao.getCoAutori(a) ;
			for(Author b: coautori) {
				if(this.grafo.containsVertex(a) && this.grafo.containsVertex(b))
					this.grafo.addEdge(a, b);	
			}
			System.out.println("Vertici: " + grafo.vertexSet().size());
			System.out.println("Archi: " + grafo.edgeSet().size());
			
			
		}
	}
	public List<Author> trovaCoAutori(Author a) {
		
		if(this.grafo==null)
			creaGrafo() ;
		
		List<Author> coautori = Graphs.neighborListOf(this.grafo, a) ;
		return coautori ;
}
	

}
