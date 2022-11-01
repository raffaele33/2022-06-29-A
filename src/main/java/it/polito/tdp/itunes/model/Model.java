package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;


import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	private ItunesDAO dao;
	private Graph<Album,DefaultWeightedEdge> grafo;
	private Map<Integer,Album> map;
	private List<Album> vertici;
	public Model() {
		dao=new ItunesDAO();
		map=new HashMap<Integer,Album>();
		dao.getAllAlbums(map);
	}
	
	public void CreaGrafo(int n) {
		grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		List<AlbumP> alb= new ArrayList<AlbumP>(this.dao.getVertici(n, map));
		vertici=new ArrayList<Album>();
		for(AlbumP a : alb) {
			vertici.add(a.getAlbum());
		}
		Graphs.addAllVertices(this.grafo, vertici);
		
		for(AlbumP a: alb) {
			for(AlbumP a1: alb) {
				if(!a.equals(a1) && a.getPeso()-a1.getPeso()!=0) {
					if(a.getPeso()>a1.getPeso()) {
						Graphs.addEdge(grafo, a1.getAlbum(), a.getAlbum(), a.getPeso()-a1.getPeso());
					}
				}
			}
		}
		
	}
	
  public List<AlbumP> getAdiacenze(Album album){
	  List<AlbumP> result=new ArrayList<>();
	  List<Album> successori= new ArrayList<>(Graphs.successorListOf(this.grafo, album));
	  int ent;
	  int usc;
	  for(Album a : successori) {
		  ent=0;
		  usc=0;
		  for(DefaultWeightedEdge d : this.grafo.incomingEdgesOf(a)) {
			  ent+=this.grafo.getEdgeWeight(d);
		  }
		  for(DefaultWeightedEdge d : this.grafo.outgoingEdgesOf(a)) {
			  usc+=this.grafo.getEdgeWeight(d);
		  }
		  result.add(new AlbumP(a,ent-usc));
	  }
		Collections.sort(result, new Comparator<AlbumP>() {

			@Override
			public int compare(AlbumP o1,AlbumP o2) {
				return   -(o1.getPeso()-o2.getPeso());
			}
			
		});
	  return result;
  }

	public int getVert(){
		return this.grafo.vertexSet().size();
	}
	
	
	public int getArchi(){
		return this.grafo.edgeSet().size();
	}
	public List<Album> getAlbum(){
		
		Collections.sort(this.vertici, new Comparator<Album>() {

			@Override
			public int compare(Album o1,Album o2) {
				return   o1.getTitle().compareTo(o2.getTitle());
			}
			
		});
		return this.vertici;
	}
}
