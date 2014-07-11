package com.fortmin.proshopping;

import java.io.IOException;

import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.shopping.Shopping;
import com.fortmin.proshopping.shopping.Shopping.GetPaqueteRf;
import com.fortmin.proshopping.shopping.model.Paquete;
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
    private ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_PAQUETE_RF); 
    private Paquete paquete;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Obtener el paquete asociado a un Elmento RF
		ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_PAQUETE_RF);
		comNube.setIdElementoRF("BEACON001");
		comNube.execute();
		
		
		
		Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG).show();
		
	}
	
	
	
}
