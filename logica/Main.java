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
		// abrir los archivos al iniciar el programa
		Scanner lector = new Scanner(System.in);
		boolean salir = false;
		leerPokedex();
		leerHabitats();
		leerGimnasios();
		leerAltoMando();
		while (!salir) {
			System.out.println("POKEMON EN JAVA!!!!!!!!!");
			System.out.println("");
			System.out.println("1) Continuar.");
			System.out.println("2) Nueva Partida");
			System.out.println("3) Salir.");
		}
		try {
			int opcion = Integer.valueOf(lector.nextLine());

			switch (opcion) {
			case 1:
				break;
			case 2:
				System.out.println("--NUEVO JUGADOR--");
				System.out.println("Ingrese su apodo: ");
				String nombreIngresado = lector.nextLine();
				Jugador nuevoJugador = new Jugador(nombreIngresado, "none");
				System.out.println("Bienvenido " + nuevoJugador.getNombre() + "!!");

				break;
			case 3:
				System.out.println("Saliendo del juego...");
				salir = true;
				break;
			default:
				System.out.println("Opcion no encontrada.");
			}
		} catch (Exception e) {
			System.out.println("ERROR: Ingrese un número válido.");
		}

		System.out.println(listaPokedex.size());
	}
	
	public static void menuJuego(Jugador protagonista, Scanner lector) {
		boolean salirMenu = false;
		while (!salirMenu) {
			System.out.println("1) Revisar Equipo.");
			System.out.println("2) Salir a capturar");
			System.out.println("3) Acceso al PC (cambiar equipo)");
			System.out.println("4) Retar un gimnasio");
			System.out.println("5) Desafío al Alto Mando");
			System.out.println("6) Curar Pokémon");
			System.out.println("7) Guardar y continuar.");
			System.out.println("8) Guardar y salir.");
		}
		try {
			int opcion = Integer.valueOf(lector.nextLine());
			switch (opcion) {
			case 1:
				revisarEquipo(protagonista);
				break;
			case 2:
				capturarPokemon(protagonista, lector);
				break;
			case 3:
				accederPC(protagonista, lector);
				break;
			case 4:
				retarGimnasio();
				break;
			case 5:
				desafiarAltoMando();
				break;
			case 6:
				curarPokemon();
				break;
			case 7:
				guardarContinuar();
				break;
			case 8:
				guardarSalir();
				salirMenu = true;
				break;
			default:
				System.out.println("Opcion no encontrada");
			}
		} catch (Exception e) {
			System.out.println("ERROR: Ingrese un número válido.");
		}
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