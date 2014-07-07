package com.fortmin.proshopping;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

@Api(name = "shopping", namespace = @ApiNamespace(ownerDomain = "fortmin.com", ownerName = "fortmin.com", packagePath = "proshopping"))
public class Shopping {

	/*
	 * Este metodo permite agregar una ubicacion al conjunto de ubicaciones del
	 * Shopping
	 */
	@ApiMethod(name = "insertubicacion", path = "insert_ubicacion")
	public Ubicacion insertUbicacion(@Named("nombre") String nombre,
			@Named("edificio") String edificio, @Named("piso") int piso,
			@Named("sector") String sector, @Named("area") String area,
			@Named("lugar") String lugar) {
		UbicacionEndpoint endpoint = new UbicacionEndpoint();
		Ubicacion ubicacion = new Ubicacion(nombre, edificio, piso, sector,
				area, lugar);
		ubicacion = endpoint.insertUbicacion(ubicacion);
		return ubicacion;
	}

	/*
	 * Este metodo permite actualizar una ubicacion del conjunto de ubicaciones
	 * del Shopping
	 */
	@ApiMethod(name = "updateubicacion", path = "update_ubicacion")
	public Ubicacion updateUbicacion(@Named("nombre") String nombre,
			@Named("edificio") String edificio, @Named("piso") int piso,
			@Named("sector") String sector, @Named("area") String area,
			@Named("lugar") String lugar) {
		UbicacionEndpoint endpoint = new UbicacionEndpoint();
		Ubicacion ubicacion = new Ubicacion(nombre, edificio, piso, sector,
				area, lugar);
		ubicacion = endpoint.updateUbicacion(ubicacion);
		return ubicacion;
	}

	/*
	 * Este metodo permite eliminar una ubicacion del conjunto de ubicaciones
	 * del Shopping
	 */
	@ApiMethod(name = "deleteubicacion", path = "delete_ubicacion")
	public void deleteUbicacion(@Named("ubicacion") String nomubi) {
		UbicacionEndpoint endpoint = new UbicacionEndpoint();
		endpoint.removeUbicacion(nomubi);
	}

	/*
	 * Este metodo permite obtener los datos de una ubicacion del conjunto de
	 * ubicaciones del Shopping
	 */
	@ApiMethod(name = "getubicacion", path = "get_ubicacion")
	public Ubicacion getUbicacion(@Named("ubicacion") String nombre) {
		UbicacionEndpoint endpoint = new UbicacionEndpoint();
		Ubicacion gUbicacion = endpoint.getUbicacion(nombre);
		return gUbicacion;
	}

	/*
	 * Este metodo agrega un comercio al conjunto de comercios del Shopping
	 */
	@ApiMethod(name = "insertcomercio", path = "insert_comercio")
	public void insertComercio(@Named("comercio") String nomcom,
			@Named("ubicacion") String ubicacion) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Comercio comercio = mgr.find(Comercio.class, nomcom);
		if (comercio == null) {
			comercio = new Comercio(nomcom, ubicacion);
			mgr.persist(comercio);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Este metodo actualiza los datos de un comercio
	 */
	@ApiMethod(name = "updatecomercio", path = "update_comercio")
	public void updateComercio(@Named("comercio") String nomcom,
			@Named("ubicacion") String ubicacion) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Comercio comercio = mgr.find(Comercio.class, nomcom);
		if (comercio != null) {
			comercio.setUbicacion(ubicacion);
			mgr.persist(comercio);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Este metodo da de baja un comercio del conjunto de comercios del Shopping
	 */
	@ApiMethod(name = "deletecomercio", path = "delete_comercio")
	public void deleteComercio(@Named("comercio") String nomcom) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Comercio comercio = mgr.find(Comercio.class, nomcom);
		if (comercio != null) {
			mgr.remove(comercio);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Este metodo permite agregar un producto a la lista de productos de un
	 * comercio
	 */
	@ApiMethod(name = "insertproducto", path = "insert_producto")
	public void insertProducto(@Named("comercio") String comercio,
			@Named("codProd") String codigo, @Named("nomProd") String nombre,
			@Named("precioProd") float precio) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Producto producto = new Producto(comercio, codigo, nombre, precio);
		Producto prodesta = mgr.find(Producto.class, producto.getClave());
		if (prodesta == null) {
			mgr.persist(producto);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Este metodo permite eliminar un producto de la lista de productos de un
	 * comercio
	 */
	@ApiMethod(name = "deleteproducto", path = "delete_producto")
	public void deleteProducto(@Named("comercio") String comercio,
			@Named("codProd") String codigo) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Producto producto = new Producto(comercio, codigo);
		producto = mgr.find(Producto.class, producto.getClave());
		if (producto != null) {
			mgr.remove(producto);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Este metodo permite agregar un producto a la lista de productos de un
	 * comercio
	 */
	@ApiMethod(name = "updateproducto", path = "update_producto")
	public void updateProducto(@Named("comercio") String comercio,
			@Named("codProd") String codigo, @Named("nomProd") String nombre,
			@Named("precioProd") float precio) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Producto producto = new Producto(comercio, codigo);
		producto = mgr.find(Producto.class, producto.getClave());
		if (producto != null) {
			producto.setNombre(nombre);
			producto.setPrecio(precio);
			mgr.persist(producto);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Obtener los datos del paquete en base a su nombre
	 */
	@ApiMethod(name = "getpaquete", path = "get_paquete")
	public Paquete getPaquete(@Named("paquete") String nompaq) {
		Paquete resp = null;
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Paquete paquete = mgr.find(Paquete.class, nompaq);
		if (paquete != null) {
			resp = paquete;
		}
		trans.commit();
		mgr.close();
		return resp;
	}

	/*
	 * Crear un paquete nuevo con una lista de productos vacia y luego los
	 * productos deben agregarse con insertProductoPaquete
	 */
	@ApiMethod(name = "insertpaquete", path = "insert_paquete")
	public void insertPaquete(@Named("paquete") String nompaq,
			@Named("puntos") int puntos, @Named("precio") float precio,
			@Named("elementoRf") String elemRf) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Paquete paquete = mgr.find(Paquete.class, nompaq);
		if (paquete == null) {
			paquete = new Paquete(nompaq, puntos, precio, elemRf);
			mgr.persist(paquete);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Eliminar un paquete existente de un comercio junto a todos sus datos
	 */
	@ApiMethod(name = "deletepaquete", path = "delete_paquete")
	public void deletePaquete(@Named("paquete") String nompaq) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Paquete paquete = mgr.find(Paquete.class, nompaq);
		if (paquete != null) {
			mgr.remove(paquete);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Actualizar un paquete existente con nuevos valores de precio y puntos
	 */
	@ApiMethod(name = "updatepaquete", path = "update_paquete")
	public void updatePaquete(@Named("paquete") String nompaq,
			@Named("puntos") int puntos, @Named("precio") float precio,
			@Named("elementoRf") String elemRf) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Paquete paquete = mgr.find(Paquete.class, nompaq);
		if (paquete != null) {
			paquete.setPrecio(precio);
			paquete.setPuntos(puntos);
			paquete.setElementoRF(elemRf);
			mgr.persist(paquete);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Insertar un producto en un paquete ya existente
	 */
	@ApiMethod(name = "insertproductopaquete", path = "insert_producto_paquete")
	public void insertProductoPaquete(@Named("paquete") String nompaq,
			@Named("comercio") String comercio,
			@Named("codProducto") String codigo) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Paquete paquete = mgr.find(Paquete.class, nompaq);
		if (paquete != null) {
			paquete.agregarProducto(comercio, codigo);
			mgr.persist(paquete);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Borrar un producto de un paquete ya existente
	 */
	@ApiMethod(name = "deleteproductopaquete", path = "delete_producto_paquete")
	public void deleteProductoPaquete(@Named("paquete") String nompaq,
			@Named("comercio") String comercio,
			@Named("codProducto") String codigo) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Paquete paquete = mgr.find(Paquete.class, nompaq);
		if (paquete != null) {
			paquete.eliminarProducto(comercio, codigo);
			mgr.persist(paquete);
		}
		trans.commit();
		mgr.close();
	}
	
	/*
	 * Insertar un producto en un paquete ya existente
	 */
	@ApiMethod(name = "inserttag", path = "insert_tag")
	public void insertTag(@Named("tagid") String id,
			@Named("ubicacion") String ubicacion,
			@Named("tipocontenido") String tipo) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Tag tag = mgr.find(Tag.class, id);
		if (tag != null) {
			tag = new Tag(id, ubicacion, tipo);
			mgr.persist(tag);
		}
		trans.commit();
		mgr.close();
	}

	/*
	 * Listar paquete
	 */
	@ApiMethod(name = "listarpaquete", path = "listar_paquete")
	public void listarPaquete(@Named("paquete") String nompaq) {
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Paquete paquete = mgr.find(Paquete.class, nompaq);
		if (paquete != null) {
			System.out.println("Nombre de paquete  : " + nompaq);
			System.out.println("Precio de paquete  : " + paquete.getPrecio());
			System.out.println("Puntos de paquete  : " + paquete.getPuntos());
			System.out.println("Cant. de productos : "
					+ paquete.getCantProductos());
			System.out.println("Prods en Lista     : "
					+ paquete.getProductos().size());
			for (int i = 0; i < paquete.getCantProductos(); i++) {
				System.out.println("Producto " + i + " : "
						+ paquete.getProductos().get(i));
			}
		}
		trans.commit();
		mgr.close();
	}
	
	/*
	 * 
	 */
	@ApiMethod(name = "obtenerpaqueteasociadorf", path = "obtener_paquete_asociado_rf")
	public Paquete obtenerPaqueteAsociadoRF(@Named("elementoRF") String elemRf) {
		Paquete paqresp = null;
		EntityManager mgr = getEntityManager();
		EntityTransaction trans = mgr.getTransaction();
		trans.begin();
		Query querypaq = mgr.createQuery("SELECT p FROM Paquete p WHERE p.elementoRF = :elemrf", Paquete.class);
		querypaq.setParameter("elemrf", elemRf);
		paqresp = (Paquete) querypaq.getSingleResult();
		trans.commit();
		mgr.close();
		return paqresp;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
