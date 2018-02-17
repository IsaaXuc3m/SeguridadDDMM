package uc3m.practica;

/**
 * Created by Jaime-4 on 16/02/2018.
 */


public class ParametrosUsuarios {
    public String nacionalidad;
    public String sexo;
    public int numUsuarios;
    public String fecha;

    public ParametrosUsuarios(String nacionalidad, String sexo, int numUsuarios, String fecha) {
        this.nacionalidad = nacionalidad;
        this.sexo = sexo;
        this.numUsuarios = numUsuarios;
        this.fecha = fecha;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getSexo() {
        return sexo;
    }

    public int getNumUsuarios() {
        return numUsuarios;
    }

    public String getFecha() {
        return fecha;
    }
}
