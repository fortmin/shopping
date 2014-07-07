package com.fortmin.proshopping;

import java.util.LinkedList;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.datanucleus.api.jpa.annotations.Index;

@Entity
public class Paquete {

	@Id
	private String nombre;
	private int cantProductos;
	private int puntos;
	private float precio;
	@ElementCollection(fetch=FetchType.EAGER)
	private LinkedList<String> productos;
	@Index(name="PaqElemRfIdx", unique="true")
	private String elementoRF;
	
	public Paquete(String nombre) {
		this.nombre = nombre;
		this.cantProductos = 0;
		this.puntos = 0;
		this.precio = 0;
		this.productos = new LinkedList<String>();
		this.elementoRF = "";
	}

	public Paquete(String nombre, int puntos, float precio, String elementoRF) {
		this.nombre = nombre;
		this.cantProductos = 0;
		this.puntos = puntos;
		this.precio = precio;
		this.productos = new LinkedList<String>();
		this.elementoRF = elementoRF;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantProductos() {
		return cantProductos;
	}

	public void setCantProductos(int cantProductos) {
		this.cantProductos = cantProductos;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public LinkedList<String> getProductos() {
		return productos;
	}

	public void setProductos(LinkedList<String> productos) {
		this.productos = productos;
	}
	
	public String getElementoRF() {
		return elementoRF;
	}

	public void setElementoRF(String elementoRF) {
		this.elementoRF = elementoRF;
	}

	public void actualizarPaquete(int puntos, float precio) {
		this.puntos = puntos;
		this.precio = precio;
	}
	
	public boolean tieneProducto(String comercio, String codigo) {
		boolean esta = false;
		for (int i = 0; i < productos.size() && !esta; i++) {
			if (productos.get(i).equals(comercio+"::"+codigo)) {
				esta = true;
			}
		}
		return esta;
	}
	
	public void agregarProducto(String comercio, String codigo) {
		if (!tieneProducto(comercio, codigo)) {
			productos.add(comercio+"::"+codigo);
			this.setCantProductos(cantProductos+1);
		}
	}

	public void eliminarProducto(String comercio, String codigo) {
		boolean salir = false;
		for (int i = 0; i < productos.size() && !salir; i++) {
			if (productos.get(i).equals(comercio+"::"+codigo)) {
				productos.remove(i);
				salir = true;
			}
		}
	}
}
