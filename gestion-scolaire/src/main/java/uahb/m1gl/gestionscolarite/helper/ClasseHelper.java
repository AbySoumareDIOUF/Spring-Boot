package uahb.m1gl.gestionscolarite.helper;

import org.springframework.stereotype.Component;
import uahb.m1gl.gestionscolarite.dto.ClasseRequest;
import uahb.m1gl.gestionscolarite.dto.ClasseResponse;
import uahb.m1gl.gestionscolarite.dto.ClasseUpdateRequest;
import uahb.m1gl.gestionscolarite.exception.ScolariteException;
import uahb.m1gl.gestionscolarite.exception.ScolariteNotFoundException;
import uahb.m1gl.gestionscolarite.mapper.ClassMapper;
import uahb.m1gl.gestionscolarite.model.Classe;
import uahb.m1gl.gestionscolarite.repository.ClasseRepository;
import uahb.m1gl.gestionscolarite.service.ClasseService;
import uahb.m1gl.gestionscolarite.service.FiliereService;

import java.util.List;
import java.util.Optional;

@Component
public class ClasseHelper {
    private final ClasseService classeService;
    private final FiliereService filiereService;
    private final ClassMapper classMapper;

    public ClasseHelper(ClasseService classeService, FiliereService filiereService, ClassMapper classMapper, ClasseRepository classeRepository) {
        this.classeService = classeService;
        this.filiereService = filiereService;
        this.classMapper = classMapper;
    }

    public List<ClasseResponse> findAllClasses() {
        return classeService.findAll().stream()
                .map(classMapper::ClasseEntityToClasseResponse)
                .toList();
    }

    public ClasseResponse saveClasse(ClasseRequest classeRequest) {
        // Vérifier si la filliere existe
        if (filiereService.findById(classeRequest.getFiliereId()) == null) {
            throw new ScolariteNotFoundException("Aucune filere avec l'id " + classeRequest.getFiliereId() + " n'est trouvé ");
        }
        // Vérifier si le code existe dans la base de données
        if (classeService.findByCode(classeRequest.getCode()) != null) {
            throw new ScolariteNotFoundException("Une classe avec le code " + classeRequest.getCode() + " existe déjà");
        }

        // Vérifier si le nom existe dans la base de données
        if (classeService.findByNom(classeRequest.getNom()) != null) {
            throw new ScolariteNotFoundException("Une classe avec le nom " + classeRequest.getNom() + " existe déjà");
        }

        // Vérifier si le montant de l'inscription est >= 30k
        valideMontant(classeRequest.getMontantInscription(), 30000);

        // Vérifier si la mensualité est >= 30k
        valideMontant(classeRequest.getMensualite(), 30000);

        // Vérifier si les autres frais sont >= 10k
        valideMontant(classeRequest.getAutreFrais(), 10000);

        // Si toutes les validations passent, sauvegarder la classe
        var savedClasse = classeService.save(classMapper.classeRequestToClasseEntity(classeRequest));
        return classMapper.ClasseEntityToClasseResponse(savedClasse);

    }

    private void valideMontant(int montant, int seuil) {
        if (montant < seuil) {
            throw new ScolariteNotFoundException("La mensualité doit être d'au moins " + seuil);
        }
    }

    public ClasseResponse findClasseById(long id) {
        Classe classe = classeService.findById(id);
        if (classe == null) {
            throw new ScolariteNotFoundException("Aucune classe avec l'id " + id + " n'est trouvé ");
        }
        return classMapper.ClasseEntityToClasseResponse(classe);

    }
//Modificatiopn

    public ClasseResponse updateClasse(ClasseUpdateRequest classeUpdateRequest) {
        Classe classe = classeService.findById(classeUpdateRequest.getId());
        if (classe == null) {
            throw new ScolariteNotFoundException("Aucune classe avec l'id " + classeUpdateRequest.getId() + " n'est trouvé ");
        }
        List<Classe> otherClasses = classeService.SelectWithoutId(classe.getId());
        Optional<Classe> doublonCode = otherClasses.stream()
                .filter(c-> c.getCode().equals(classe.getCode())).findAny();
        if (doublonCode.isPresent()) {
            throw new ScolariteException("Une Classe avec le code " + doublonCode.get() .getCode() + " existe déja");
        }

        Optional<Classe> doublonNom = otherClasses.stream()
                .filter(c-> c.getNom().equals(classe.getNom())).findAny();
        if (doublonNom.isPresent()) {
            throw new ScolariteException("Une Classe avec le nom " + doublonNom.get().getNom() + " existe déja");
        }

        // Vérifier si le montant de l'inscription est >= 30k
        valideMontant(classeUpdateRequest.getClasseRequest().getMontantInscription(), 30000);

        // Vérifier si la mensualité est >= 30k
        valideMontant(classeUpdateRequest.getClasseRequest().getMensualite(), 30000);

        // Vérifier si les autres frais sont >= 10k
        valideMontant(classeUpdateRequest.getClasseRequest().getAutreFrais(), 10000);

        return classMapper.
                ClasseEntityToClasseResponse(classeService
                        .save(classMapper.classeUpdateRequestToClasseEntity(classeUpdateRequest)));
    }
}
