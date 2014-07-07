package com.fortmin.proshopping;

import java.io.IOException;

import com.fortmin.proshopping.gae.ComercioNube;
import com.fortmin.proshopping.shopping.Shopping;
import com.fortmin.proshopping.shopping.model.Ubicacion;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * The Main Activity.
 * 
 * This activity starts up the RegisterActivity immediately, which communicates
 * with your App Engine backend using Cloud Endpoints. It also receives push
 * notifications from backend via Google Cloud Messaging (GCM).
 * 
 * Check out RegisterActivity.java for more details.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Start up RegisterActivity right away
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	
	public void onResume() {
		Toast.makeText(getApplicationContext(), "Insertando comercio en la nube", Toast.LENGTH_SHORT).show();
		ComercioNube comNube = new ComercioNube("INSERT", "La Cancha", "HallEntrada1");
		InsertarComercio epic = new InsertarComercio();
		epic.execute();
	}
	
	/*
	public class InsertarUbicacion extends AsyncTask<Context, Integer, Long> {
		protected Long doInBackground(Context... contexts) {
			long ret = 1;
			Ubicacionendpoint.Builder endpointBuilder = new Ubicacionendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), 
					new JacksonFactory(),
					new HttpRequestInitializer() {
						public void initialize(HttpRequest httpRequest) {
						}
					});
			Ubicacionendpoint endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
			try {
				Ubicacion ubic = new Ubicacion();
				ubic.setNombre("Hall principal");
				ubic.setPiso(1);
				ubic.setEdificio("Principal");
				ubic.setArea("Hall");
				ubic = endpoint.insertUbicacion(ubic).execute();
				ret = 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ret;
		}*/

		public class InsertarComercio extends AsyncTask<Context, Integer, Long> {
			
			protected Long doInBackground(Context... contexts) {
				long ret = 1;
				Shopping.Builder endpointBuilder = new Shopping.Builder(
						AndroidHttp.newCompatibleTransport(), 
						new JacksonFactory(),
						new HttpRequestInitializer() {
							public void initialize(HttpRequest httpRequest) {
							}
						});
				Shopping endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
				try {
					Ubicacion ubic = new Ubicacion();
					ubic.setNombre("HallEntrada1");
					ubic.setPiso(1);
					ubic.setEdificio("Principal");
					ubic.setArea("Hall");
					ubic.setSector("HallEntrada");
					ubic.setLugar("Atras del kiosco");
					endpoint.insertubicacion(ubic).execute();
					endpoint.insertcomercio("La Cancha", "HallPrincipal");
					ret = 0;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return ret;
			}
	}

}
