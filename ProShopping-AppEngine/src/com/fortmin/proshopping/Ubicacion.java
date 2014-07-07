package com.fortmin.proshopping;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ubicacion {

	@Id
	private String nombre;
	private String edificio;
	private int piso;
	private String sector;
	private String area;
	private String lugar;
	
	public Ubicacion(String nombre, String edificio, int piso, String sector,
			String area, String lugar) {
		this.nombre = nombre;
		this.edificio = edificio;
		this.piso = piso;
		this.sector = sector;
		this.area = area;
		this.lugar = lugar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEdificio() {
		return edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public int getPiso() {
		return piso;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

}
