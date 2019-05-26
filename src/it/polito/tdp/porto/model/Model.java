package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
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
	public List<Paper> sequenzaArticoli(Author a1, Author a2) {

		List<Paper> result = new ArrayList<>() ; 
		PortoDAO dao = new PortoDAO();

				
		// trovo un cammino minimo tra a1 ed a2
		ShortestPathAlgorithm<Author, DefaultEdge> dijkstra = 
				new DijkstraShortestPath<Author, DefaultEdge>(this.grafo);
		
		GraphPath<Author,DefaultEdge> gp = dijkstra.getPath(a1, a2);

		List<DefaultEdge> edges = gp.getEdgeList();
		
		// ciascun edge corrisponderà ad un paper!
		
		for(DefaultEdge e: edges) {
			// autori che corrispondono all'edge
			Author as = grafo.getEdgeSource(e) ;
			Author at = grafo.getEdgeTarget(e) ;
			// trovo l'articolo
			Paper p = dao.articoloComune(as, at) ;
			if(p == null)
				throw new InternalError("Paper not found...") ;
			result.add(p) ;
		}
		
		return result ;

}

}
