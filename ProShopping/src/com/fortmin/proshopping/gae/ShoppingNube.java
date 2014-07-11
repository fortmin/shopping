package com.fortmin.proshopping.gae;

import java.io.IOException;
import java.util.List;

import com.fortmin.proshopping.CloudEndpointUtils;
import com.fortmin.proshopping.shopping.Shopping;
import com.fortmin.proshopping.shopping.Shopping.GetPaqueteRf;
import com.fortmin.proshopping.shopping.Shopping.GetProductosPaquete;
import com.fortmin.proshopping.shopping.model.Paquete;
import com.fortmin.proshopping.shopping.model.Producto;
import com.fortmin.proshopping.shopping.model.ProductoCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ShoppingNube extends AsyncTask<Context, Integer, Long> {

	public static String OPE_GET_PAQUETE_RF = "GetPaqueteRf";
	public static String OPE_GET_PRODUCTOS_PAQUETE = "GetProductosPaquete";	

	private String TAG = "ProShopping";
	private String operacion;				// Señala el nombre de la operacion a ejecutar
	private String idElementoRF;			// Identificacion de elemento RF
	private String nomPaquete;				// Nombre de paquete
	private Paquete paquete;
	private ProductoCollection productos;
	private List<Producto> listaProductos;
	private GetPaqueteRf resp;
	public ShoppingNube(String operacion) {
		this.operacion = operacion;
	}

	@Override
	protected Long doInBackground(Context... params) {
		long ret = 1;
		Shopping.Builder endpointBuilder = new Shopping.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		Shopping endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
		try {
			if (operacion.equals(OPE_GET_PAQUETE_RF)) {
				if (idElementoRF != null) {
					Log.i(this.TAG,OPE_GET_PAQUETE_RF);
				    resp = endpoint.getPaqueteRf(idElementoRF);
					//paquete=resp.execute();
					//Log.i(this.TAG,"Grabe el paquete");
					ret = 0;
				}
				else
					Log.i(this.TAG,OPE_GET_PAQUETE_RF+" - Falta Id de Elemento RF");
			}
			if (operacion.equals(OPE_GET_PRODUCTOS_PAQUETE)) {
				if (nomPaquete != null) {
					Log.i(this.TAG,OPE_GET_PRODUCTOS_PAQUETE);
					GetProductosPaquete resp = endpoint.getProductosPaquete(nomPaquete);
					productos=resp.execute();// fortti
					listaProductos=productos.getItems();
					ret = 0;
				}
				else
					Log.i(this.TAG,OPE_GET_PRODUCTOS_PAQUETE+" - Falta Nombre de Paquete");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public GetPaqueteRf getResp() {
		return resp;
	}

	public void setResp(GetPaqueteRf resp) {
		this.resp = resp;
	}

	public Paquete getPaquete() {
		return paquete;
	}

	public void setPaquete(Paquete paquete) {
		this.paquete = paquete;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getIdElementoRF() {
		return idElementoRF;
	}

	public void setIdElementoRF(String idElementoRF) {
		this.idElementoRF = idElementoRF;
	}

	public String getNomPaquete() {
		
		return paquete.getNombre();
	}

	public void setNomPaquete(String nomPaquete) {
		this.nomPaquete = nomPaquete;
	}

}
