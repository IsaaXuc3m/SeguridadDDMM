package uc3m.practica;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Insertar extends AppCompatActivity
{
    private final String TAG = "Insertar";
    private EditText textoNacionalidad, textoSexo, textoNumero, textoFecha;
    DataBase baseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        textoNacionalidad = (EditText) findViewById(R.id.editTextNacionalidad);
        textoSexo = (EditText) findViewById(R.id.editTextSexo);
        textoNumero = (EditText) findViewById(R.id.editTextNumero);
        textoFecha = (EditText) findViewById(R.id.editTextFechaRegistro);
        baseDatos = new DataBase(this);
    }

    public void botonInsertar(View view) {
        Log.d("Insertar","Insertando");
        String nacionalidad = textoNacionalidad.getText().toString();
        Log.d(TAG, "la nacionalidad es:" +nacionalidad);
        if(nacionalidad.equals(""))nacionalidad = null;
        Log.d(TAG, "la nacionalidad es:" +nacionalidad);
        String sexo = textoSexo.getText().toString();
        if(sexo.equals("")) sexo = null;
        String numeroS = textoNumero.getText().toString();
        int numero;
        if(numeroS.equals("")) numero = 1;
        else
        {
            numero = Integer.parseInt(numeroS);
        }
        String fecha = textoFecha.getText().toString();
        if(fecha.equals("")) fecha = null;

        ParametrosUsuarios parametros=new ParametrosUsuarios(
                nacionalidad,
                sexo,
                numero,
                null);//"20/01/2012"

        MiTareaAsincrona tarea=new MiTareaAsincrona();
        tarea.execute(parametros);
    }

    public void insertar(Usuario usuario)
    {
        if(baseDatos.insert(usuario))
        {
            //Toast.makeText(this, "Insercion correcta", Toast.LENGTH_SHORT).show();
            Log.d("Insertar", "bien");
        }
        else
        {
            //Toast.makeText(this, "Fallo al hacer el insert", Toast.LENGTH_SHORT).show();
            Log.d("Insertar", "mal");
        }
    }

    // Sexo puede ser "male" o "female"
    // Los valores que no se tengan ser'an nulos en vez de ""
    // https://code.tutsplus.com/es/tutorials/android-from-scratch-using-rest-apis--cms-27117
    // api https://randomuser.me/documentation#howto
    class MiTareaAsincrona extends AsyncTask<ParametrosUsuarios, Integer, Boolean>
    {
        //https://gist.github.com/hnaoto/f492c9ceae264897dd6f
        @Override
        protected Boolean doInBackground(ParametrosUsuarios... args)
        {
            List<Usuario> usuarios=new ArrayList<>();
            try
            {
                String direccionweb="https://randomuser.me/api/?";
                int cont=0;
                if (args[0].getSexo()!=null)
                {
                    //meter el string concatenado
                    //https://msdn.microsoft.com/es-es/library/system.text.stringbuilder.append(v=vs.110).aspx
                    String temp="";
                    if(cont!=0){//primero no agrega nada
                        temp="&";
                    }
                    temp=temp.concat("gender=" + args[0].getSexo());
                    direccionweb=direccionweb.concat(temp);
                    cont++;
                } if (args[0].getNacionalidad()!=null){
                    String temp="";
                    if(cont!=0){//primero no agrega nada
                        temp="&";
                    }
                    temp=temp.concat("nat=" + args[0].getNacionalidad());
                    direccionweb=direccionweb.concat(temp);
                } if (args[0].getNumUsuarios()!=0){
                    String temp="";
                    if(cont!=0){//primero no agrega nada
                        temp="&";
                    }
                    temp=temp.concat( ("results=" + (args[0].getNumUsuarios())));
                    direccionweb=direccionweb.concat(temp);
                }
                Log.d("STATE","URL: " + direccionweb );
                URL direccion = new URL(direccionweb);
                // Create connection
                HttpsURLConnection myConnection =
                        (HttpsURLConnection) direccion.openConnection();
                if (myConnection.getResponseCode() == 200)
                {
                    // Success
                    List<Usuario> usuariosSinFiltrar=new ArrayList<>();
                    String contenido1=readStream(myConnection.getInputStream());
                    usuariosSinFiltrar=parseUsuarios(contenido1);
                    myConnection.disconnect();
                    Log.d("STATE", "Numero usuarios " + usuariosSinFiltrar.size());
                    if (args[0].getFecha()!=null)
                    {
                        //llamar a la funcion de parseo
                        usuarios=parseUsuarios(usuariosSinFiltrar,args[0].getFecha());

                    }
                    else
                    {
                        usuarios=usuariosSinFiltrar;
                    }
                    Log.d("STATE", "Numero usuarios filtrados " + usuarios.size());
                    for (int i=0;i<usuarios.size();i++)
                    {
                        Log.d("STATE", "Usuario " + i + ": " + usuarios.get(i).getName().title + " " +usuarios.get(i).getName().first
                                + " " +usuarios.get(i).getName().last + " Sexo: " + usuarios.get(i).getGender() + " Fecha: " +usuarios.get(i).getRegister()
                                + " Nacionalidad: " +usuarios.get(i).getNat() + " Imagen " +usuarios.get(i).getImage()
                                + " Localizacion " +usuarios.get(i).getLocation().street  + " " +usuarios.get(i).getLocation().city);
                        Log.d("Insertar", "antes de insertar");
                        insertar(usuarios.get(i));
                        Log.d("Insertar", "despues de insertar");
                    }

                }
                else
                {
                    // Error handling code goes here
                    Log.d("Insertar", "fallo de conexion");
                }
            }
            catch (MalformedURLException exc)
            {

            }
            catch (IOException ec2)
            {

            }
            Log.d("STATE", "fin");
            return true;
        }

        private String readStream(InputStream in)
        {
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();
            try
            {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    response.append(line);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }
        private List<Usuario> parseUsuarios (String jString)
        {
            List<Usuario> listaUsuarios=new ArrayList<Usuario>();
            try
            {
                JSONObject jObj=new JSONObject(jString);
                JSONArray arr=jObj.getJSONArray("results");
                if (arr!=null)
                {
                    for (int i=0;i<arr.length();i++)
                    {
                        String gender=arr.getJSONObject(i).getString("gender");
                        String email=arr.getJSONObject(i).getString("email");
                        String phone=arr.getJSONObject(i).getString("phone");
                        String register=arr.getJSONObject(i).getString("registered");
                        register=register.substring(0,9);
                        String nat=arr.getJSONObject(i).getString("nat");
                        String im=arr.getJSONObject(i).getJSONObject("picture").getString("large");
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
                        Usuario usuario= new Usuario(gender, email, phone, nat,register, name, location,login,im);
                        //Log.d("STATE","datos usuario email objeto" + usuario.email);
                        listaUsuarios.add(usuario);
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return listaUsuarios;
        }
        private List<Usuario> parseUsuarios (List<Usuario> listaSinFiltrar,String fecha)
        {
            List<Usuario> usuarios=new ArrayList<>();
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            try
            {
                SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
                Date strDate=sdf.parse(fecha); //esta es la fecha a partir de la cual se pillan usuarios.
                // Ahora se recorren todos los usuarios y si coinciden se meten en la lista.
                for (int i=0;i<listaSinFiltrar.size();i++)
                {
                    Date strDateCompare=sdf2.parse(listaSinFiltrar.get(i).getRegister());
                    if (strDate.before(strDateCompare))
                    {
                        usuarios.add(listaSinFiltrar.get(i));
                    }
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            return usuarios;
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
