package uc3m.practica;

import java.util.List;

/**
 * Created by Jaime-4 on 14/02/2018.
 */

public class ListaUsuarios {
    public List<Usuario> lista;

    public ListaUsuarios(List<Usuario> lista) {
        this.lista = lista;
    }

    public List<Usuario> getLista() {
        return lista;
    }
}
