package logica;

import java.util.ArrayList;

public class AltoMando {
	private int numeroAltoMando;
	private String nombreAltoMando;
	private ArrayList<Pokemon> equipoAltoMando;
	
	public AltoMando(int numeroAltoMando, String nombreAltoMando) {
		this.numeroAltoMando = numeroAltoMando;
		this.nombreAltoMando = nombreAltoMando;
		this.equipoAltoMando = new ArrayList<>();
	}

	public int getNumeroAltoMando() {
		return numeroAltoMando;
	}

	public String getNombreAltoMando() {
		return nombreAltoMando;
	}
	
	public ArrayList<Pokemon> getEquipoAltoMando() {
		return equipoAltoMando;
	}
}