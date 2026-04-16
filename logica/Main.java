package logica;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class Main {
	public static ArrayList<Pokemon> listaPokedex = new ArrayList<>();
	public static ArrayList<Zona> listaZonas = new ArrayList<>();
	
	public static void main(String[] args) {
		leerPokedex();
		System.out.println(listaPokedex.size());
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
				Pokemon nuevoPoke = new Pokemon(partes[0], partes[1], spawnChance, vidaPoke, ataquePoke, defensaPoke, ataqueEspPoke, defensaEspPoke, velocidadPoke, partes[9]);
				listaPokedex.add(nuevoPoke);
				
			}
			
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
		} catch (FileNotFoundException e) {
			System.out.println("No se encontro el archivo de Habitats.");
		}
	}
}
