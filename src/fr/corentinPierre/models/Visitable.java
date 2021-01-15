package fr.corentinPierre.models;

public interface Visitable {
	public abstract int accept(Visitor visitor, int id);
}
