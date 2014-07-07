package com.fortmin.proshopping.gae;

import java.io.IOException;

import com.fortmin.proshopping.CloudEndpointUtils;
import com.fortmin.proshopping.shopping.Shopping;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ComercioNube extends AsyncTask<Context, Integer, Long> {

	private String TAG = "ProShopping";
	private String operacion;
	private String nomComercio;
	private String nomUbicacion;
	
	public ComercioNube(String operacion, String comercio, String ubicacion) {
		this.operacion = operacion;
		this.nomComercio = comercio;
		this.nomUbicacion = ubicacion;
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
			if (operacion.equals("INSERT")) {
				if (nomComercio != null && nomUbicacion != null) {
					Log.i(this.TAG,"insertComercio");
					endpoint.insertcomercio(nomComercio, nomUbicacion);		
					ret = 0;
				}
				else
					Log.i(this.TAG,"insertComercio - Falta comercio/ubicacion");
			}
			else if (operacion.equals("UPDATE")) {
				if (nomComercio != null && nomUbicacion != null) {
					Log.i(this.TAG,"updateComercio");
					endpoint.updatecomercio(nomComercio, nomUbicacion);	
					ret = 0;
				}
				else
					Log.i(this.TAG,"updateComercio - Falta comercio/ubicacion");
			}
			else if (operacion.equals("DELETE")) {
				if (nomComercio != null) {
					Log.i(this.TAG,"deleteComercio");
					endpoint.deletecomercio(nomComercio);	
					ret = 0;
				}
				else
					Log.i(this.TAG,"deleteComercio - Falta comercio");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getNomComercio() {
		return nomComercio;
	}

	public void setNomComercio(String nomComercio) {
		this.nomComercio = nomComercio;
	}

	public String getNomUbicacion() {
		return nomUbicacion;
	}

	public void setNomUbicacion(String nomUbicacion) {
		this.nomUbicacion = nomUbicacion;
	}

}
