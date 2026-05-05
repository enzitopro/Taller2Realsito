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
					Jugador jugadorGuardado = cargarPartida();
					if (jugadorGuardado != null) {
						menuJuego(jugadorGuardado, lector);
					}
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

	// menu de juego cuando el usuario cargue partida o inicie una nueva
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
					retarGimnasio(protagonista, lector);
					break;
				case 5:
					desafiarAltoMando(protagonista, lector);
					break;
				case 6:
					curarPokemon(protagonista);
					break;
				case 7:
					guardarPartida(protagonista);
					break;
				case 8:
					guardarPartida(protagonista);
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

	// metodo para buscar pokemon en pokedex
	public static Pokemon buscarEnPokedex(String nombrePokemon) {
		for (Pokemon poke : listaPokedex) {
			if (poke.getNombre().equalsIgnoreCase(nombrePokemon)) {
				return poke;
			}
		}
		return null;
	}

	// metodo buscar zona en la lista de zonas
	public static Zona buscarZona(String nombreZona) {
		for (Zona z : listaZonas) {
			if (z.getNombre().equalsIgnoreCase(nombreZona)) {
				return z;
			}
		}
		return null;
	}

	// metodo leer archivo pokedex
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

	// metodo leer archivo habitats
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
		for (int i = 0; i < listaZonas.size(); i++) {
			Zona zona = listaZonas.get(i);
			System.out.println((i + 1) + ") " + zona.getNombre());
		}
		System.out.println("0) Volver al menu.");
		try {
			System.out.println("Ingrese Zona: ");
			int opcion = Integer.valueOf(lector.nextLine());
			if (opcion == 0) {
				return;
			} else if (opcion > 0 && opcion <= listaZonas.size()) {
				Zona zonaElegida = listaZonas.get(opcion - 1);
				System.out.println("Explorando " + zonaElegida.getNombre() + "...");
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
							pokemonEncontrado.getPorcentajeAparicion(), pokemonEncontrado.getVida(),
							pokemonEncontrado.getAtaque(), pokemonEncontrado.getDefensa(),
							pokemonEncontrado.getAtaqueEspecial(), pokemonEncontrado.getDefensaEspecial(),
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
			System.out.println(
					"No tienes suficientes Pokemon para usar el PC. (Tienes " + todos.size() + "/6 en tu equipo)");
			return;
		}

		boolean enPC = true;
		while (enPC) {
			System.out.println("\n=== SISTEMA DE ALMACENAMIENTO POKEMON ===");
			System.out.println("--- TU EQUIPO ACTUAL ---");
			for (int i = 0; i < 6; i++) {
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

					if (indexEquipo >= 0 && indexEquipo < 6 && indexCaja >= 6 && indexCaja < todos.size()) {
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

	public static void retarGimnasio(Jugador protagonista, Scanner lector) {
		ArrayList<Pokemon> miEquipo = protagonista.getEquipo();

		if (miEquipo.isEmpty()) {
			System.out.println("No tienes Pokemon! Ve a capturar!");
			return;
		}

		System.out.println("\n=== LIGA POKÉMON ===");
		for (int i = 0; i < listaGimnasios.size(); i++) {
			Gimnasios g = listaGimnasios.get(i);
			System.out.println((i + 1) + ") Gimnasio " + g.getNumeroGimnasio() + " - Lider: " + g.getNombreLider()
					+ " [" + g.getEstadoLider() + "]");
		}
		System.out.println("0) Volver");

		try {
			System.out.println("Elige un Gimnasio para retar: ");
			int opcion = Integer.valueOf(lector.nextLine());
			if (opcion == 0)
				return;

			if (opcion > 0 && opcion <= listaGimnasios.size()) {
				Gimnasios gymElegido = listaGimnasios.get(opcion - 1);
				ArrayList<Pokemon> equipoLider = gymElegido.getEquipoLider();
				System.out.println("\nEl lider " + gymElegido.getNombreLider() + " te desafía a un combate!");

				Pokemon miLuchador = null;
				Pokemon rival = null;

				// conseguir nuestro pokemon
				for (Pokemon p : miEquipo) {
					if (p.getEstado().equals("Vivo") && p.getVida() > 0) {
						miLuchador = p;
						break;
					}
				}

				// conseguir pokemon del rival
				for (Pokemon p : equipoLider) {
					if (p.getEstado().equals("Vivo") && p.getVida() > 0) {
						rival = p;
						break;
					}
				}

				if (miLuchador == null) {
					System.out.println("Todo tu equipo está debilitado. Ve al Centro Pokemon!");
					return;
				}

				if (rival == null) {
					System.out.println("Este lider ya no tiene Pokemon para pelear!");
					return;
				}

				System.out.println("¡Adelante, " + miLuchador.getNombre() + "!");
				System.out.println("El Lider " + gymElegido.getNombreLider() + " envia a " + rival.getNombre() + "!");

				boolean enCombate = true;

				while (enCombate) {
					System.out.println("\n--- TURNO ---");
					System.out.println("Tú: " + miLuchador.getNombre() + " (HP: " + miLuchador.getVida() + ") | Tipo: "
							+ miLuchador.getTipo());
					System.out.println("Rival: " + rival.getNombre() + " (HP: " + rival.getVida() + ") | Tipo: "
							+ rival.getTipo());
					System.out.println("1) Atacar / 2) Huir");

					int accion = Integer.valueOf(lector.nextLine());

					if (accion == 2) {
						System.out.println("Escapaste del combate!");
						enCombate = false;
						break;
					} else if (accion == 1) {
						Pokemon primero, segundo;
						if (miLuchador.getVelocidad() >= rival.getVelocidad()) {
							primero = miLuchador;
							segundo = rival;
						} else {
							primero = rival;
							segundo = miLuchador;
						}

						ejecutarAtaque(primero, segundo);

						if (segundo.getVida() > 0) {
							ejecutarAtaque(segundo, primero);
						}

						if (miLuchador.getVida() <= 0) {
							miLuchador.setVida(0);
							miLuchador.setEstado("Debilitado");
							System.out.println("¡Tu " + miLuchador.getNombre() + " se ha debilitado!");

							Pokemon siguiente = null;
							for (Pokemon p : miEquipo) {
								if (p.getEstado().equals("Vivo") && p.getVida() > 0) {
									siguiente = p;
									break;
								}
							}

							if (siguiente != null) {
								miLuchador = siguiente;
								System.out.println("\nAdelante, " + miLuchador.getNombre());
							} else {
								System.out.println("\n¡Ya no te quedan Pokemon vivos!");
								System.out.println(
										"Has perdido el combate. " + gymElegido.getNombreLider() + " te ha deerrotado");
								enCombate = false;
							}
						}

						if (enCombate && rival.getVida() <= 0) {
							rival.setVida(0);
							rival.setEstado("Debilitado");
							System.out.println("¡El " + rival.getNombre() + " rival se ha debilitado!");

							Pokemon siguienteRival = null;
							for (Pokemon p : equipoLider) {
								if (p.getEstado().equals("Vivo") && p.getVida() > 0) {
									siguienteRival = p;
									break;
								}
							}

							if (siguienteRival != null) {
								rival = siguienteRival;
								System.out.println("\n¡El lider " + gymElegido.getNombreLider() + " ha sacado a su "
										+ rival.getNombre() + "!");
							} else {
								System.out.println(
										"\n¡Felicidades! Has derrotado al Lider " + gymElegido.getNombreLider() + "!");
								gymElegido.setEstadoLider("Derrotado");

								if (protagonista.getMedallas().equals("none")) {
									protagonista.setMedallas(gymElegido.getNombreLider());
								} else {
									protagonista.setMedallas(
											protagonista.getMedallas() + "," + gymElegido.getNombreLider());
								}

								enCombate = false;
							}
						}
					}
				}

			}
		} catch (Exception e) {
			System.out.println("Entrada no valida");
		}
	}

	// metodo para realizar ataques y actualizar vidas
	public static void ejecutarAtaque(Pokemon atacante, Pokemon defensor) {
		System.out.println("-> ¡" + atacante.getNombre() + " ataca!");
		double multiplicador = TablaTipos.multiplicadorAtaque(atacante.getTipo(), defensor.getTipo());

		double danoBase = atacante.getAtaque() - (defensor.getDefensa() * 0.5);
		if (danoBase < 1)
			danoBase = 1;

		int danoTotal = (int) (danoBase * multiplicador);

		// fix loop infinito (ej. magikarp vs magikarp)
		if (multiplicador > 0 && danoTotal < 1) {
			danoTotal = 1;
		}

		if (multiplicador > 1.0) {
			System.out.println("  ¡Es super efectivo!");
		} else if (multiplicador < 1.0 && multiplicador > 0) {
			System.out.println("  No es muy efectivo...");
		} else if (multiplicador == 0) {
			System.out.println("  No afecta a " + defensor.getNombre() + "...");
		}

		System.out.println("  " + defensor.getNombre() + " recibe " + danoTotal + " puntos de daño.");
		defensor.setVida(defensor.getVida() - danoTotal);
	}

	// metodo para curar a los pokemon debilitados
	public static void curarPokemon(Jugador protagonista) {
		ArrayList<Pokemon> todos = protagonista.getMisPokemons();

		if (todos.isEmpty()) {
			System.out.println("No tienes Pokemon para curar.");
			return;
		}

		System.out.println("Enfermera Joy: ¡Bienvenidos al Centro Pokemon!");
		System.out.println("Enfermera Joy: Restaurando la salud de tu equipo...");

		for (Pokemon p : todos) {
			p.setVida(p.getVidaMaxima());
			p.setEstado("Vivo");
		}

		System.out.println("Enfermera Joy: ¡Tus Pokemon estan en perfecto estado!");
	}

	// metodo para desafiar altos mandos
	public static void desafiarAltoMando(Jugador protagonista, Scanner lector) {
		ArrayList<Pokemon> miEquipo = protagonista.getEquipo();

		if (miEquipo.isEmpty()) {
			System.out.println("¡No tienes Pokemon! Ve a capturar primero.");
			return;
		}
		System.out.println("\n=== LIGA POKEMON: ALTO MANDO ===");
		for (int i = 0; i < listaAltosMandos.size(); i++) {
			AltoMando am = listaAltosMandos.get(i);
			System.out.println((i + 1) + ") Alto Mando -" + am.getNombreAltoMando());
		}
		System.out.println("0) Volver");
		try {
			System.out.print("Elige a quien retar: ");
			int opcion = Integer.valueOf(lector.nextLine());

			if (opcion == 0)
				return;

			if (opcion > 0 && opcion <= listaAltosMandos.size()) {
				AltoMando amElegido = listaAltosMandos.get(opcion - 1);
				ArrayList<Pokemon> equipoRival = amElegido.getEquipoAltoMando();

				System.out.println(
						"\n¡El Alto Mando " + amElegido.getNombreAltoMando() + " te desafia a un combate definitivo!");

				Pokemon miLuchador = null;
				Pokemon rival = null;

				for (Pokemon p : miEquipo) {
					if (p.getEstado().equals("Vivo") && p.getVida() > 0) {
						miLuchador = p;
						break;
					}
				}
				for (Pokemon p : equipoRival) {
					if (p.getEstado().equals("Vivo") && p.getVida() > 0) {
						rival = p;
						break;
					}
				}
				if (miLuchador == null) {
					System.out.println("Todo tu equipo está debilitado!");
					return;
				}
				if (rival == null) {
					System.out.println("Este miembro del Alto Mando no tiene Pokemon vivos!");
					return;
				}

				System.out.println("¡Adelante, " + miLuchador.getNombre() + "!");
				System.out.println(
						"¡El Alto Mando " + amElegido.getNombreAltoMando() + " envía a " + rival.getNombre() + "!");

				boolean enCombate = true;

				while (enCombate) {
					System.out.println("\n --- TURNO ---");
					System.out.println("Tu " + miLuchador.getNombre() + " (HP: " + miLuchador.getVida() + ") | Tipo: "
							+ miLuchador.getTipo());
					System.out.println("Rival: " + rival.getNombre() + " (HP: " + rival.getVida() + ") | Tipo: "
							+ rival.getTipo());
					System.out.println("1) Atacar / 2) Huir");

					int accion = Integer.valueOf(lector.nextLine());
					if (accion == 2) {
						System.out.println("Escapaste del combate...");
						enCombate = false;
					} else if (accion == 1) {
						Pokemon primero = (miLuchador.getVelocidad()) >= rival.getVelocidad() ? miLuchador : rival;
						Pokemon segundo = (primero == miLuchador) ? rival : miLuchador;

						ejecutarAtaque(primero, segundo);
						if (segundo.getVida() > 0) {
							ejecutarAtaque(segundo, primero);
						}

						if (miLuchador.getVida() <= 0) {
							miLuchador.setVida(0);
							miLuchador.setEstado("Debilitado");
							System.out.println("¡Tu " + miLuchador.getNombre() + " se ha debilitado!");

							Pokemon siguiente = null;
							for (Pokemon p : miEquipo) {
								if (p.getEstado().equals("Vivo") && p.getVida() > 0) {
									siguiente = p;
									break;
								}
							}
							if (siguiente != null) {
								miLuchador = siguiente;
								System.out.println("\n¡Adelante, " + miLuchador.getNombre() + "!");
							} else {
								System.out.println("\n¡Ya no te quedan Pokemon vivos! " + amElegido.getNombreAltoMando()
										+ " te ha derrotado...");
								enCombate = false;
							}
						}
						if (enCombate && rival.getVida() <= 0) {
							rival.setVida(0);
							rival.setEstado("Debilitado");
							System.out.println("¡El " + rival.getNombre() + " rival se ha debilitado!");

							Pokemon siguienteRival = null;
							for (Pokemon p : equipoRival) {
								if (p.getEstado().equals("Vivo") && p.getVida() > 0) {
									siguienteRival = p;
									break;
								}
							}
							if (siguienteRival != null) {
								rival = siguienteRival;
								System.out.println("\n¡El Alto Mando " + amElegido.getNombreAltoMando()
										+ " ha sacado a su " + rival.getNombre() + "!");
							} else {
								System.out.println("\n¡Felicidades! Has derrotado al Alto Mando "
										+ amElegido.getNombreAltoMando() + "!");
								enCombate = false;
							}
						}
					}
				}
			} else {
				System.out.println("Opcion no valida");
			}
		} catch (Exception e) {
			System.out.println("Entrada invalida.");
		}
	}

	// metodo para guardar partida
	public static void guardarPartida(Jugador protagonista) {
		try {
			java.io.BufferedWriter escritor = new java.io.BufferedWriter(new java.io.FileWriter("registros.txt"));
			escritor.write(protagonista.getNombre() + ";" + protagonista.getMedallas());
			escritor.newLine();

			for (Pokemon p : protagonista.getMisPokemons()) {
				escritor.write(p.getNombre() + ";" + p.getEstado());
				escritor.newLine();
			}

			escritor.close();
			System.out.println("¡Partida guardada con exito!");
		} catch (Exception e) {
			System.out.println("ERROR AL GUARDAR LA PARTIDA");
		}
	}

	// metodo para cargar partida
	public static Jugador cargarPartida() {
		try {
			java.io.BufferedReader lectorArchivo = new java.io.BufferedReader(new java.io.FileReader("registros.txt"));

			String datosJugador = lectorArchivo.readLine();
			if (datosJugador == null) {
				System.out.println("El archivo está vacío.");
				lectorArchivo.close();
				return null;
			}
			String[] partesJugador = datosJugador.split(";");
			Jugador protaCargado = new Jugador(partesJugador[0], partesJugador[1]);
			lectorArchivo.readLine();

			String lineaPoke;
			while ((lineaPoke = lectorArchivo.readLine()) != null) {
				String[] partesPoke = lineaPoke.split(";");
				String nombrePoke = partesPoke[0];
				String estadoPoke = partesPoke[1];
				Pokemon base = buscarEnPokedex(nombrePoke);

				if (base != null) {
					Pokemon clon = new Pokemon(base.getNombre(), base.getHabitat(), base.getPorcentajeAparicion(),
							base.getVida(), base.getAtaque(), base.getDefensa(), base.getAtaqueEspecial(),
							base.getDefensaEspecial(), base.getVelocidad(), base.getTipo());
					clon.setEstado(estadoPoke);
					clon.setVidaMaxima(base.getVida());
					if (estadoPoke.equals("Debilitado")) {
						clon.setVida(0);
					}
					protaCargado.getMisPokemons().add(clon);
				}
			}
			lectorArchivo.close();
			System.out
					.println("¡Partida cargada exitosamente! Bienvenido de vuelta, " + protaCargado.getNombre() + ".");
			return protaCargado;
		} catch (java.io.FileNotFoundException e) {
			System.out.println("No hay ninguna partida guardada");
		} catch (Exception e) {
			System.out.println("Error al cargar el archivo guardado");
		}
		return null;
	}

}