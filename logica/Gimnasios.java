package logica;

import java.util.ArrayList;

public class Gimnasios {
	private int numeroGimnasio;
	private String nombreLider;
	private String estadoLider;
	private ArrayList<Pokemon> equipoLider;

	public Gimnasios(int numeroGimnasio, String nombreLider, String estadoLider) {
		this.numeroGimnasio = numeroGimnasio;
		this.nombreLider = nombreLider;
		this.estadoLider = estadoLider;
		this.equipoLider = new ArrayList<>();
	}

	public int getNumeroGimnasio() {
		return numeroGimnasio;
	}

	public String getNombreLider() {
		return nombreLider;
	}

	public String getEstadoLider() {
		return estadoLider;
	}

	public void setEstadoLider(String estadoLider) {
		this.estadoLider = estadoLider;
	}

	public ArrayList<Pokemon> getEquipoLider() {
		return equipoLider;
	}
}
