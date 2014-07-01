package com.fortmin.proshopping;

import java.util.Iterator;
import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Paquete {

	@Id
	private String clave;
	private String comercio;
	private String nombre;
	private int cantProductos;
	private int puntos;
	private float precio;
	@OneToMany
	private LinkedList<Producto> productos;
	
	public Paquete(String comercio, String nombre) {
		this.clave = comercio+"/"+nombre;
		this.comercio = comercio;
		this.nombre = nombre;
		cantProductos = 0;
		puntos = 0;
		precio = 0;
		productos = new LinkedList<Producto>();
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getComercio() {
		return comercio;
	}

	public void setComercio(String comercio) {
		this.comercio = comercio;
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

	public LinkedList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(LinkedList<Producto> productos) {
		this.productos = productos;
	}
	
	public boolean tieneProducto(String nombre) {
		boolean esta = false;
		Iterator<Producto> iprods = productos.iterator();
		while (iprods.hasNext() && !esta) {
			if (iprods.next().getNombre().equals(nombre)) esta = true;
		}
		return esta;
	}
	
	public void agregarProducto(Producto producto) {
		if (!tieneProducto(producto.getNombre())) {
			productos.add(producto);
			this.setCantProductos(cantProductos+1);
		}
	}

	public void eliminarProducto(String nombre) {
		boolean salir = false;
		for (int i = 0; i < productos.size() && !salir; i++) {
			if (productos.get(i).getNombre().equals(nombre)) {
				productos.remove(i);
				salir = true;
			}
		}
	}
}
