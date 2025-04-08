package uahb.m1gl.gestionscolarite.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 8)
    private  String code;
    @Column(length = 150)
    private String nom;
    private int montantInscription;
    private int mensualite;
    private int autreFrais;
    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;
}
