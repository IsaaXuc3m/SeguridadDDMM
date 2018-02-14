package uc3m.practica;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Insertar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        Log.d("STATE","PROBANDO APLICACION");
        MiTareaAsincrona tarea=new MiTareaAsincrona();
        tarea.execute();
    }


    //https://code.tutsplus.com/es/tutorials/android-from-scratch-using-rest-apis--cms-27117
    // api https://randomuser.me/documentation#howto
    class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d("STATE","PRUEBAS BACKGROUND");
            try{
                URL direccion = new URL("https://randomuser.me/api/");
                // Create connection
                Log.d("STATE","PRUEBAS BACKGROUND2");
                HttpsURLConnection myConnection =
                        (HttpsURLConnection) direccion.openConnection();
                Log.d("RESPUESTA","CONTENIDO" + myConnection.getResponseCode());
                if (myConnection.getResponseCode() == 200) {
                    // Success
                    Log.d("STATE","PRUEBAS BACKGROUND3");
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");
                    JSONObject datos=new JSONObject();
                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginObject(); // Start processing the JSON object
                    // BUSCAR EN EL JSON LEIDO

                    Log.d("STATE","PRUEBAS BACKGROUND4");

                    while (jsonReader.hasNext()) { // Loop through all keys
                        String key = jsonReader.nextName(); // Fetch the next key
                        if (key.equals("results")) { // Check if desired key
                            // Fetch the value as a String
                            String value =jsonReader.nextString();
                            Gson gson=new Gson();
                            Usuario usuario= gson.fromJson(value,Usuario.class);

                            Log.d("STATE","pruebas 5" + usuario.email);
                            jsonReader.beginArray();
                            jsonReader.beginObject();
                            String nombre=jsonReader.nextName();
                            Log.d("STATE","CONTENIDO paco " + nombre);
                            String genero=jsonReader.nextString();
                            Log.d("STATE","genero :  " + genero);
                            Log.d("STATE","pruebas 6");

                            //mostrar por pantalla o hacer algo con este dato a ver que sale.
                            try{
                                datos=new JSONObject(value);

                            }
                            catch (JSONException a){}


                            break; // Break out of the loop
                        } else {
                            jsonReader.skipValue(); // Skip values of other keys
                        }
                    }
                    // Ya tengo en datos el resultado
                    String genero="";
                    try{
                        genero=datos.getString("gender");}
                    catch (JSONException a){}
                    Log.d("PRUEBAS","TEST impresion" + genero);
                    jsonReader.close();
                    myConnection.disconnect();
                } else {
                    // Error handling code goes here
                }
            }
            catch (MalformedURLException exc){

            }catch (IOException ec2){

            }


            return true;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean result) {

        }

        @Override
        protected void onCancelled() {

        }
    }
}
