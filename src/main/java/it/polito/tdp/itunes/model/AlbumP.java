package it.polito.tdp.itunes.model;

import java.util.Objects;

public class AlbumP{
	private Album album;
	private int peso;
	@Override
	public String toString() {
		return "album = " + album + ", peso = " + peso ;
	}
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public AlbumP(Album album, int peso) {
		super();
		this.album = album;
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(album, peso);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlbumP other = (AlbumP) obj;
		return Objects.equals(album, other.album) && peso == other.peso;
	}
	
	
}
