package uc3m.practica;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
            List<Usuario> usuarios=new ArrayList<>();
            try{
                URL direccion = new URL("https://randomuser.me/api/?results=3");
                // Create connection
                HttpsURLConnection myConnection =
                        (HttpsURLConnection) direccion.openConnection();
                if (myConnection.getResponseCode() == 200) {
                    // Success
                    String contenido1=readStream(myConnection.getInputStream());
                    usuarios=parseUsuarios(contenido1);
                    myConnection.disconnect();

                    Log.d("STATE","usuario " + usuarios.get(0).getName().first);


                } else {
                    // Error handling code goes here
                }
            }
            catch (MalformedURLException exc){

            }catch (IOException ec2){

            }


            return true;
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }
        private List<Usuario> parseUsuarios (String jString){
            List<Usuario> listaUsuarios=new ArrayList<Usuario>();
            try{
                JSONObject jObj=new JSONObject(jString);
                JSONArray arr=jObj.getJSONArray("results");
                if (arr!=null){
                    for (int i=0;i<arr.length();i++){
                        String gender=arr.getJSONObject(i).getString("gender");
                        String email=arr.getJSONObject(i).getString("email");
                        String phone=arr.getJSONObject(i).getString("phone");
                        String nat=arr.getJSONObject(i).getString("nat");
                        Nombre name=new Nombre(
                                arr.getJSONObject(i).getJSONObject("name").getString("title"),
                                arr.getJSONObject(i).getJSONObject("name").getString("first"),
                                arr.getJSONObject(i).getJSONObject("name").getString("last"));
                        Localizacion location=new Localizacion(
                                arr.getJSONObject(i).getJSONObject("location").getString("street"),
                                arr.getJSONObject(i).getJSONObject("location").getString("city"),
                                arr.getJSONObject(i).getJSONObject("location").getString("state"),
                                arr.getJSONObject(i).getJSONObject("location").getString("postcode"));
                        LoginDatos login=new LoginDatos(
                                arr.getJSONObject(i).getJSONObject("login").getString("username"),
                                arr.getJSONObject(i).getJSONObject("login").getString("password"),
                                arr.getJSONObject(i).getJSONObject("login").getString("salt"),
                                arr.getJSONObject(i).getJSONObject("login").getString("md5"),
                                arr.getJSONObject(i).getJSONObject("login").getString("sha1"),
                                arr.getJSONObject(i).getJSONObject("login").getString("sha256")
                        );
                        //LoginDatos(String username, String password, String salt, String md5, String sha1, String sha256)
                        Usuario usuario= new Usuario(gender, email, phone, nat, name, location,login);
                        //Log.d("STATE","datos usuario email objeto" + usuario.email);
                        listaUsuarios.add(usuario);
                    }
                }


        } catch (JSONException e) {
                e.printStackTrace();
            }

        return listaUsuarios;
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
