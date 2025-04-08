package uahb.m1gl.gestionscolarite.model;

public class Personne {
    private int age;
    private String nom;

    public Personne(String nom, int age) {
        this.nom = nom;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
