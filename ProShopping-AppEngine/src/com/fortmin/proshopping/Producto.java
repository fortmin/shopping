package com.fortmin.proshopping;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Producto {

	@Id
	private String clave;
	private String comercio;
	private String codigo;
	private String nombre;
	private float precio;

	public Producto(String comercio, String codigo, String nombre, float precio) {
		this.clave = comercio+"::"+codigo;
		this.comercio = comercio;
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
	}

	public Producto(String comercio, String codigo) {
		this.clave = comercio+"::"+codigo;
		this.comercio = comercio;
		this.codigo = codigo;
		this.nombre = null;
		this.precio = 0;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getProducto() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

}
