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
		leerHabitats();
		leerPokedex();
		leerGimnasios();
		leerAltoMando();
		while (!salir) {
			System.out.println("POKEMON EN JAVA!!!!!!!!!");
			System.out.println("");
			System.out.println("1) Continuar.");
			System.out.println("2) Nueva Partida");
			System.out.println("3) Salir.");
		
		try {
			int opcion = Integer.valueOf(lector.nextLine());

			switch (opcion) {
			case 1:
				System.out.println("WIP...");
				break;
			case 2:
				System.out.println("--NUEVO JUGADOR--");
				System.out.println("Ingrese su apodo: ");
				String nombreIngresado = lector.nextLine();
				Jugador nuevoJugador = new Jugador(nombreIngresado, "none");
				System.out.println("Bienvenido " + nuevoJugador.getNombre() + "!!");
				menuJuego(nuevoJugador, lector);
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

		// System.out.println(listaPokedex.size());
		}
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
					//retarGimnasio();
					break;
				case 5:
					//desafiarAltoMando();
					break;
				case 6:
					//curarPokemon();
					break;
				case 7:
					//guardarContinuar();
					break;
				case 8:
					//guardarSalir();
					salirMenu = true;
					break;
				default:
					System.out.println("Opcion no encontrada");
				}
			} catch (Exception e) {
				System.out.println("ERROR: Ingrese un número válido.");
			}
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
	
	public static Zona buscarZona(String nombreZona) {
		for (Zona z : listaZonas) {
			if (z.getNombre().equalsIgnoreCase(nombreZona)) {
				return z;
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
				Zona zonaPokemon = buscarZona(partes[1]);
				if (zonaPokemon != null) {
					zonaPokemon.getPokemonsHabitat().add(nuevoPoke);
				}
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

	// metodos menu juego
	public static void revisarEquipo(Jugador protagonista) {
		System.out.println("--- EQUIPO ACTUAL ---");
		ArrayList<Pokemon> miEquipo = protagonista.getEquipo();

		if (miEquipo.isEmpty()) {
			System.out.println("Tu equipo esta vacío. ¡Ve a capturar algunos pokémon!");
			return;
		}

		for (int i = 0; i < miEquipo.size(); i++) {
			Pokemon p = miEquipo.get(i);
			int suma = p.getSumaStats();
			System.out.println((i + 1) + ")" + p.getNombre() + "|" + p.getTipo() + "|Stats totales: " + suma);
		}
	}

	public static void capturarPokemon(Jugador protagonista, Scanner lector) {
		System.out.println("---Que zona deseas explorar?---");
		for (int i=0; i < listaZonas.size();i++) {
			Zona zona = listaZonas.get(i);
			System.out.println((i+1)+")"+zona.getNombre());
		}
		System.out.println("0) Volver al menu.");
		try {
			System.out.println("Ingrese Zona: ");
			int opcion = Integer.valueOf(lector.nextLine());
			if (opcion == 0) {
				return;
			}
			else if (opcion>0 && opcion <= listaZonas.size()) {
				Zona zonaElegida = listaZonas.get(opcion-1);
				System.out.println("Explorando "+zonaElegida.getNombre()+"...");
				ArrayList<Pokemon> salvajes = zonaElegida.getPokemonsHabitat();
				
				if (salvajes.isEmpty()) {
					System.out.println("No parece haber ningun Pokemon ahora...");
					return;
				}
				
				double probabilidadAleatoria = Math.random() * 100;
				Pokemon pokemonEncontrado = null;
				double probAcumulada = 0;
				
				for (Pokemon p : salvajes) {
					probAcumulada += p.getPorcentajeAparicion();
					if (probabilidadAleatoria <= probAcumulada) {
						pokemonEncontrado = p;
						break;
					}
				}
				if (pokemonEncontrado == null) {
					pokemonEncontrado = salvajes.get(0);
				}
				
				System.out.println("Un " + pokemonEncontrado.getNombre() + " salvaje ha aparecido");
				System.out.println("1) Lanzar Pokeball");
				System.out.println("2) Huir");
				
				int accion = Integer.valueOf(lector.nextLine());
				if (accion == 1) {
					System.out.println("¡Has atrapado a " + pokemonEncontrado.getNombre() + "!");
					Pokemon nuevoAtrapado = new Pokemon(pokemonEncontrado.getNombre(), pokemonEncontrado.getHabitat(), 
							pokemonEncontrado.getPorcentajeAparicion(), pokemonEncontrado.getVida(), pokemonEncontrado.getAtaque(), 
							pokemonEncontrado.getDefensa(), pokemonEncontrado.getAtaqueEspecial(), pokemonEncontrado.getDefensaEspecial(), 
							pokemonEncontrado.getVelocidad(), pokemonEncontrado.getTipo());
					protagonista.getMisPokemons().add(nuevoAtrapado);
				} else {
					System.out.println("Escapaste sin problemas");
				}
			} else {
				System.out.println("Esa zona no existe.");
			}
		} catch (Exception e) {
			System.out.println("ERROR: Ingrese un número válido.");
		}
	}
	
	public static void accederPC(Jugador protagonista, Scanner lector) {
		ArrayList<Pokemon> todos = protagonista.getMisPokemons();
		
		if (todos.size() <= 6) {
			System.out.println("No tienes suficientes Pokemon para usar el PC. (Tienes " + todos.size()+ "/6 en tu equipo)");
			return;
		}
		
		boolean enPC = true;
		while (enPC) {
			System.out.println("\n=== SISTEMA DE ALMACENAMIENTO POKEMON ===");
			System.out.println("--- TU EQUIPO ACTUAL ---");
			for (int i=0; i<6; i++) {
				System.out.println(i + ") " + todos.get(i).getNombre());
			}
			System.out.println("\n¿Qué deseas hacer?");
			System.out.println("1) Intercambiar un Pokémon");
			System.out.println("0) Salir del PC");
			
			try {
				int opcion = Integer.valueOf(lector.nextLine());
				if (opcion == 0) {
					enPC = false;
				} else if (opcion == 1) {
					System.out.print("Ingresa el numero del Pokemon de TU EQUIPO que quieres guardar (0-5): ");
					int indexEquipo = Integer.valueOf(lector.nextLine());
					
					System.out.print("Ingresa el numero del Pokemon del PC que quieres sacar: ");
					int indexCaja = Integer.valueOf(lector.nextLine());
					
					if (indexEquipo>=0 && indexEquipo < 6 && indexCaja >= 6 && indexCaja < todos.size()) {
						Pokemon temporal = todos.get(indexEquipo);
						todos.set(indexEquipo, todos.get(indexCaja));
						todos.set(indexCaja, temporal);
						
						System.out.println("¡Cambio realizado con exito!");
					} else {
						System.out.println("Indices incorrectos. Intenta de nuevo.");
					}
				}
			} catch (Exception e) {
				System.out.println("Entrada invalida.");
			}
		}
	}
}