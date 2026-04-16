package logica;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class Main {
	public static ArrayList<Pokemon> listaPokedex = new ArrayList<>();
	public static ArrayList<Zona> listaZonas = new ArrayList<>();
	public static ArrayList<Gimnasios> listaGimnasios = new ArrayList<>();
	public static ArrayList<AltoMando> listaAltosMandos = new ArrayList<>();

	public static void main(String[] args) {
		leerPokedex();
		System.out.println(listaPokedex.size());
	}

	public static Pokemon buscarEnPokedex(String nombrePokemon) {
		for (Pokemon poke : listaPokedex) {
			if (poke.getNombre().equalsIgnoreCase(nombrePokemon)) {
				return poke;
			}
		}
		return null;
	}

	public static void leerPokedex() {
		try {
			File pokedex = new File("Pokedex.txt");
			Scanner lector = new Scanner(pokedex);
			while (lector.hasNextLine()) {
				String lineaActual = lector.nextLine();
				String[] partes = lineaActual.split(";");
				double spawnChance = Double.parseDouble(partes[2]);
				int vidaPoke = Integer.parseInt(partes[3]);
				int ataquePoke = Integer.parseInt(partes[4]);
				int defensaPoke = Integer.parseInt(partes[5]);
				int ataqueEspPoke = Integer.parseInt(partes[6]);
				int defensaEspPoke = Integer.parseInt(partes[7]);
				int velocidadPoke = Integer.parseInt(partes[8]);
				Pokemon nuevoPoke = new Pokemon(partes[0], partes[1], spawnChance, vidaPoke, ataquePoke, defensaPoke,
						ataqueEspPoke, defensaEspPoke, velocidadPoke, partes[9]);
				listaPokedex.add(nuevoPoke);
			}
			lector.close();

		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo de PokeDex.");
		}
	}

	public static void leerHabitats() {
		try {
			File habitats = new File("Habitats.txt");
			Scanner lector = new Scanner(habitats);
			while (lector.hasNextLine()) {
				String zonaLeida = lector.nextLine();
				Zona nuevaZona = new Zona(zonaLeida);
				listaZonas.add(nuevaZona);
			}
			lector.close();
		} catch (FileNotFoundException e) {
			System.out.println("No se encontro el archivo de Habitats.");
		}
	}

	public static void leerGimnasios() {
		try {
			File gimnasio = new File("Gimnasios.txt");
			Scanner lector = new Scanner(gimnasio);
			while (lector.hasNextLine()) {
				String lineaActual = lector.nextLine();
				String[] partes = lineaActual.split(";");
				int numeroGimnasio = Integer.valueOf(partes[0]);
				String nombreLider = partes[1];
				String estadoLider = partes[2];
				int cantPokemons = Integer.valueOf(partes[3]);

				Gimnasios nuevoGim = new Gimnasios(numeroGimnasio, nombreLider, estadoLider);

				for (int i = 0; i < cantPokemons; i++) {
					String nombrePokemonLider = partes[4 + i];
					Pokemon pokeEncontrado = buscarEnPokedex(nombrePokemonLider);
					if (pokeEncontrado != null) {
						nuevoGim.getEquipoLider().add(pokeEncontrado);
					}
				}
				listaGimnasios.add(nuevoGim);
			}
			lector.close();

		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo de Gimnasios.");
		}
	}

	public static void leerAltoMando() {
		try {
			File altoMando = new File("Alto Mando.txt");
			Scanner lector = new Scanner(altoMando);
			while (lector.hasNextLine()) {
				String lineaActual = lector.nextLine();
				String[] partes = lineaActual.split(";");
				int numeroAltoMando = Integer.valueOf(partes[0]);
				String nombreAltoMando = partes[1];

				AltoMando nuevoAltoMando = new AltoMando(numeroAltoMando, nombreAltoMando);

				for (int i = 0; i < 6; i++) {
					String nombrePokemonAltoMando = partes[2 + i];
					Pokemon pokeEncontrado = buscarEnPokedex(nombrePokemonAltoMando);
					if (pokeEncontrado != null) {
						nuevoAltoMando.getEquipoAltoMando().add(pokeEncontrado);
					}
				}
				listaAltosMandos.add(nuevoAltoMando);
			}
			lector.close();
		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo de Alto Mando.");
		}
	}
}