package logica;

import java.util.ArrayList;

public class Zona {
	private String nombre;
	private ArrayList<Pokemon> pokemonsHabitat;

	public Zona(String nombre) {
		super();
		this.nombre = nombre;
		this.pokemonsHabitat = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public ArrayList<Pokemon> getPokemonsHabitat() {
		return pokemonsHabitat;
	}

}
