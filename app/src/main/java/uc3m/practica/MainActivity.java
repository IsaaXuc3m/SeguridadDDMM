package uc3m.practica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread reloj = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            } /** Fin del metodo public void run() */
        }; /** Fin del Thread reloj = new Thread() */
        reloj.start();
    }
}
