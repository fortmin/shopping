package com.fortmin.proshopping.gae;

import java.io.IOException;

import com.fortmin.proshopping.CloudEndpointUtils;
import com.fortmin.proshopping.shopping.Shopping;
import com.fortmin.proshopping.shopping.Shopping.Getubicacion;
import com.fortmin.proshopping.shopping.model.Ubicacion;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UbicacionNube extends AsyncTask<Context, Integer, Long> {

	private String TAG = "ProShopping";
	private String operacion;
	private Ubicacion ubicacion;

	public UbicacionNube(String ope, String nomU, String edifU, int pisoU,
			String sectU, String areaU, String lugU) {
		operacion = ope;
		ubicacion = new Ubicacion();
		ubicacion.setNombre(nomU);
		ubicacion.setEdificio(edifU);
		ubicacion.setPiso(pisoU);
		ubicacion.setSector(sectU);
		ubicacion.setArea(areaU);
		ubicacion.setLugar(lugU);
	}

	public UbicacionNube(String ope, String nomU) {
		operacion = ope;
		ubicacion = new Ubicacion();
		ubicacion.setNombre(nomU);
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
		Shopping endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder)
				.build();
		try {
			if (operacion.equals("INSERT")) {
				if (ubicacion != null) {
					Log.i(this.TAG, "insertUbicacion");
					endpoint.insertubicacion(ubicacion.getNombre(),ubicacion.getEdificio(),ubicacion.getSector(), ubicacion.getArea(), ubicacion.getPiso(),ubicacion.getLugar());
					ret = 0;
				} else
					Log.i(this.TAG, "insertUbicacion - Falta ubicacion");
			} else if (operacion.equals("UPDATE")) {
				if (ubicacion != null) {
					Log.i(this.TAG, "updateUbicacion");
					
					endpoint.updateubicacion(ubicacion.getNombre(),	ubicacion.getEdificio(), ubicacion.getSector(), ubicacion.getArea(),ubicacion.getPiso(),ubicacion.getLugar());
					ret = 0;
				} else
					Log.i(this.TAG, "updateUbicacion - Falta ubicacion");
			} else if (operacion.equals("DELETE")) {
				if (ubicacion != null) {
					Log.i(this.TAG, "deleteUbicacion");
					endpoint.deleteubicacion(ubicacion.getNombre());
					ret = 0;
				} else
					Log.i(this.TAG, "deleteUbicacion - Falta ubicacion");
			} else if (operacion.equals("GET")) {
				Getubicacion gUbicacion = endpoint.getubicacion(ubicacion.getNombre());
				
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

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

}
