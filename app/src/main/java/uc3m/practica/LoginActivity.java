package uc3m.practica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity
{
    private final String TAG = "Login";
    private final String USUARIO = "pedro";
    private final String CONTRASENA = "picapiedra";
    private String hash;
    private String user, pass;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hash = sha1(CONTRASENA);
        sp = getSharedPreferences("ArchivoSP",MODE_PRIVATE);
        //Log.d(TAG, hash);
        String valor = sp.getString("Usuario","");
        ((EditText) findViewById(R.id.editTextUsuario)).setText(valor);
        valor = sp.getString("Contrasena","");
        if(valor.equals(hash))
        {
            ((EditText) findViewById(R.id.editTextContrasena)).setText(CONTRASENA);
        }

        //Toast.makeText(this,valor,Toast.LENGTH_SHORT).show();

    }

    public void botonLogin(View view)
    {
        //Intent intent = new Intent(this, Principal.class);
        //startActivity(intent);
        //((Button) findViewById(R.id.botonSignIn)).setText("hola");
        user = ((EditText) findViewById(R.id.editTextUsuario)).getText().toString();
        pass = ((EditText) findViewById(R.id.editTextContrasena)).getText().toString();
        pass = sha1(pass);
        if(user.equals(USUARIO) && pass.equals(hash))
        {
            Toast.makeText(this, "Bienvenido " + user, Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Usuario",user);
            editor.putString("Contrasena",pass);
            editor.commit();
            Intent intent = new Intent(this, Principal.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Usuario y contraseña incorrectos", Toast.LENGTH_SHORT).show();
            Log.d(TAG, user);
            Log.d(TAG, pass);
        }
    }

    public String sha1(String input)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
            byte[] result = mDigest.digest(input.getBytes());

            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
        }
        catch(NoSuchAlgorithmException e)
        {
            Log.d(TAG + " Sha1", "NoSuchAlgorithmException");
        }

        return sb.toString();
    }
}

