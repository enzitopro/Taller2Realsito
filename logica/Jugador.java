package logica;

import java.util.ArrayList;

public class Jugador {
	private String nombre;
	private String medallas;
	private ArrayList<Pokemon> misPokemons;

	public Jugador(String nombre, String medallas) {
		this.nombre = nombre;
		this.medallas = medallas;
		this.misPokemons = new ArrayList<>();
	}

	public ArrayList<Pokemon> getEquipo() {
		ArrayList<Pokemon> equipo = new ArrayList<>();
		for (int i = 0; i < misPokemons.size() && equipo.size() < 6; i++) {
			equipo.add(misPokemons.get(i));
		}

		return equipo;
	}

	public boolean pokemonYaObtenido(String nombrePokemon) {
		for (Pokemon p : misPokemons) {
			if (p.getNombre().equalsIgnoreCase(nombrePokemon))
				return true;
		}
		return false;
	}

	public final String getNombre() {
		return nombre;
	}

	public String getMedallas() {
		return medallas;
	}

	public void setMedallas(String medallas) {
		this.medallas = medallas;
	}

	public ArrayList<Pokemon> getMisPokemons() {
		return misPokemons;
	}
}