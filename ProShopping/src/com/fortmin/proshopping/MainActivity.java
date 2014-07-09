package com.fortmin.proshopping;

import java.io.IOException;

import com.fortmin.proshopping.gae.ShoppingNube;
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
		
		// Obtener el paquete asociado a un Elmento RF
		ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_PAQUETE_RF);
		comNube.setIdElementoRF("BEACON001");
		comNube.execute();

		// Obtener la lista de productos asociados a un Paquete
		comNube = new ShoppingNube(ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE);
		comNube.setNomPaquete("MundialAbrigado");
		comNube.execute();


	}
	
}
