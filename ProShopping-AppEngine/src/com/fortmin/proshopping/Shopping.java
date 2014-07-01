package com.fortmin.proshopping;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

@Api(name = "shopping", namespace = @ApiNamespace(ownerDomain = "fortmin.com", ownerName = "fortmin.com", packagePath = "proshopping"))
public class Shopping {

	/* 
	 * Este metodo agregar un comercio al conjunto de comercios del Shopping
	 */
	@ApiMethod(name = "insertcomercio")
	public Comercio insertComercio( @Named("comercio") String nomcom, 
									@Named("ubicacion") String ubicacion) {
		ComercioEndpoint endpoint = new ComercioEndpoint();
		Comercio comercio = endpoint.getComercio(nomcom);
		if (comercio != null) {
			comercio = new Comercio(nomcom, ubicacion);
			endpoint.insertComercio(comercio);		
		}
		return comercio;
	}
	
	/*
	 * Este metodo actualiza los datos de un comercio
	 */
	@ApiMethod(name = "updatecomercio")
	public Comercio updateComercio( @Named("comercio") String nomcom, 
									@Named("ubicacion") String ubicacion) {
		ComercioEndpoint endpoint = new ComercioEndpoint();
		Comercio comercio = endpoint.getComercio(nomcom);
		if (comercio != null) {
			comercio.setUbicacion(ubicacion);
			endpoint.updateComercio(comercio);		
		}
		return comercio;
	}
	
	/* 
	 * Este metodo da de baja un comercio del conjunto de comercios del Shopping
	 */
	@ApiMethod(name = "deletecomercio")
	public Comercio deleteComercio( @Named("comercio") String nomcom) {
		ComercioEndpoint endpoint = new ComercioEndpoint();
		Comercio comercio = endpoint.getComercio(nomcom);
		if (comercio != null) {
			endpoint.removeComercio(nomcom);		
		}
		return comercio;
	}
	
	/* 
	 * Este metodo permite agregar una ubicacion al conjunto de ubicaciones del Shopping
	 */
	@ApiMethod(name = "insertubicacion")
	public Ubicacion insertUbicacion( Ubicacion ubicacion) {
		UbicacionEndpoint endpoint = new UbicacionEndpoint();
		ubicacion = endpoint.insertUbicacion(ubicacion);
		return ubicacion;
	}

	/* 
	 * Este metodo permite actualizar una ubicacion del conjunto de ubicaciones del Shopping
	 */
	@ApiMethod(name = "updateubicacion")
	public Ubicacion updateUbicacion( Ubicacion ubicacion) {
		UbicacionEndpoint endpoint = new UbicacionEndpoint();
		ubicacion = endpoint.updateUbicacion(ubicacion);
		return ubicacion;
	}
	
	/* 
	 * Este metodo permite eliminar una ubicacion del conjunto de ubicaciones del Shopping
	 */
	@ApiMethod(name = "deleteubicacion")
	public void deleteUbicacion( Ubicacion ubicacion) {
		UbicacionEndpoint endpoint = new UbicacionEndpoint();
		endpoint.removeUbicacion(ubicacion.getNombre());
	}
	
	/* 
	 * Este metodo permite agregar un producto a un paquete de un comercio
	 */
	@ApiMethod(name = "insertproducto")
	public void insertProducto(	@Named("comercio") String comercio, 
								@Named("paquete") String nompaq, 
								Producto producto) {
		PaqueteEndpoint endpoint = new PaqueteEndpoint();
		Paquete paquete = new Paquete(comercio, nompaq);
		paquete = endpoint.getPaquete(paquete.getClave());
		if (paquete != null) {
			paquete.agregarProducto(producto);
			endpoint.updatePaquete(paquete);
		}
	}

	/* 
	 * Este metodo permite eliminar un producto del paquete de un comercio
	 */
	@ApiMethod(name = "deleteproducto")
	public void deleteProducto( @Named("comercio") String comercio, 
								@Named("paquete") String nompaq, 
								@Named("producto") String nomprod) {
		PaqueteEndpoint endpoint = new PaqueteEndpoint();
		Paquete paquete = new Paquete(comercio, nompaq);
		paquete = endpoint.getPaquete(paquete.getClave());
		if (paquete != null) {
			paquete.eliminarProducto(nomprod);
			endpoint.updatePaquete(paquete);
		}
	}

}
