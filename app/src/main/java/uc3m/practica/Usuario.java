package uc3m.practica;

/**
 * Created by Jaime-4 on 14/02/2018.
 */

public class Usuario {
    public String gender,email,phone,nat;
    public Nombre name;
    public Localizacion location;
    public LoginDatos login;

    public Usuario(String gender, String email, String phone, String nat, Nombre name, Localizacion location, LoginDatos login) {
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.nat = nat;
        this.name = name;
        this.location = location;
        this.login = login;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getNat() {
        return nat;
    }

    public Nombre getName() {
        return name;
    }

    public Localizacion getLocation() {
        return location;
    }

    public LoginDatos getLogin() {
        return login;
    }
}
