package uc3m.practica;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Insertar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
    }


    //https://code.tutsplus.com/es/tutorials/android-from-scratch-using-rest-apis--cms-27117
    // api https://randomuser.me/documentation#howto
    class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            try{
                URL direccion = new URL("https://randomuser.me/api/");
                // Create connection
                HttpsURLConnection myConnection =
                        (HttpsURLConnection) direccion.openConnection();
                if (myConnection.getResponseCode() == 200) {
                    // Success
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginObject(); // Start processing the JSON object
                    // BUSCAR EN EL JSON LEIDO
                    while (jsonReader.hasNext()) { // Loop through all keys
                        String key = jsonReader.nextName(); // Fetch the next key
                        if (key.equals("results")) { // Check if desired key
                            // Fetch the value as a String
                            String value = jsonReader.nextString();
                            //mostrar por pantalla o hacer algo con este dato a ver que sale.

                            // Do something with the value
                            // ...

                            break; // Break out of the loop
                        } else {
                            jsonReader.skipValue(); // Skip values of other keys
                        }
                    }
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
