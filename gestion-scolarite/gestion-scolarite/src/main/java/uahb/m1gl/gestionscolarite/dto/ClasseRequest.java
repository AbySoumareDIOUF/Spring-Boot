package uahb.m1gl.gestionscolarite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClasseRequest {

    private  String code;
    private String nom;
    private int montantInscription;
    private int mensualite;
    private int autreFrais;
    private long filiereId;
}
