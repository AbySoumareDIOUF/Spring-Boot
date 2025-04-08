package uahb.m1gl.gestionscolarite.Helper;

import org.springframework.stereotype.Component;
import uahb.m1gl.gestionscolarite.Exception.ScolariteNotFoundException;
import uahb.m1gl.gestionscolarite.dto.ClasseResponse;
import uahb.m1gl.gestionscolarite.dto.ClasseRequest;
import uahb.m1gl.gestionscolarite.mapper.ClasseMapper;
import uahb.m1gl.gestionscolarite.model.Classe;
import uahb.m1gl.gestionscolarite.service.ClasseService;
import uahb.m1gl.gestionscolarite.service.FiliereService;

import java.util.List;
import java.util.function.Function;
@Component
public class ClasseHelper {
    private  final ClasseService classeService;
    private final ClasseMapper classeMapper;
    private final FiliereService filiereService;

    public ClasseHelper (ClasseService classeService, ClasseMapper classeMapper, FiliereService filiereService){
        this.classeService = classeService;
        this.classeMapper = classeMapper;
        this.filiereService = filiereService;
    }
    public List<ClasseResponse> findaAllClasses(){
        return classeService.findAll().stream()
                .map(classeMapper::classeEntityToClasseResponse).toList();
    }

    public ClasseResponse saveClasse(ClasseRequest classeRequest){

        //Vérification de l'existence de la filière dans la base de données

        if (filiereService.findById(classeRequest.getFiliereId()) == null){
            throw new ScolariteNotFoundException("Aucune filière avec l'id "+classeRequest.getFiliereId()+" n'a été trouvé");
        }

        //Vérification de l'existence du code dans la base de données

        if (classeService.findByCode(classeRequest.getCode()) != null){
            throw new ScolariteNotFoundException("Une classe avec le code "+classeRequest.getCode()+" existe déja");
        }

        //vérifier si le nom existe

        if (classeService.findByNom(classeRequest.getNom()) != null){
            throw new ScolariteNotFoundException("Une classe avec le nom "+classeRequest.getNom()+" existe déja");
        }

        //vérifier si le montant de l'inscription est >= 30000
        validerMontant(classeRequest.getMontantInscription(),30000,"Inscription");

        //vérifier si le montant de la mensualié est >= 30000
        validerMontant(classeRequest.getMensualite(),30000,"Mensualité");

        //vérifier si le montant des autres frais est >= 10000
        validerMontant(classeRequest.getAutreFrais(),10000, "Autres Frais");

        Classe classe = classeMapper.classeRequestToClasseEntity(classeRequest);
        classe = classeService.save(classe);

        //Enregistrer la classe
        return classeMapper.classeEntityToClasseResponse(classe);
    }
    private void validerMontant (int montant, int seuil,String nomDuChamp) {

        if (montant < seuil) {
            throw new ScolariteNotFoundException("Le montant ["+nomDuChamp+"] doit etre supérieur ou égal à " + seuil);
        }

    }

}

