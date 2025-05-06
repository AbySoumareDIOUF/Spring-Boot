package uahb.m1gl.gestionscolarite.service;

import org.springframework.stereotype.Service;
import uahb.m1gl.gestionscolarite.model.Filiere;
import uahb.m1gl.gestionscolarite.repository.FiliereRepository;

@Service
public class FiliereService {

    private  final FiliereRepository filiereRepository;

    public FiliereService(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
    }

    public Filiere findById(long id){
        return filiereRepository.findById(id);
    }
}
