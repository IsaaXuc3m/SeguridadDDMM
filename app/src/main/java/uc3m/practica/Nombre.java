package uc3m.practica;

/**
 * Created by Jaime-4 on 14/02/2018.
 */

public class Nombre {
    public String title, first,last;

    public Nombre(String title, String first, String last) {
        this.title = title;
        this.first = first;
        this.last = last;
    }

    public String getTitle() {
        return title;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }
}
