package uahb.m1gl.gestionscolarite.controller;

import org.springframework.web.bind.annotation.*;
import uahb.m1gl.gestionscolarite.Helper.ClasseHelper;
import uahb.m1gl.gestionscolarite.dto.ClasseRequest;
import uahb.m1gl.gestionscolarite.dto.ClasseResponse;
import uahb.m1gl.gestionscolarite.model.Classe;
import uahb.m1gl.gestionscolarite.model.Personne;
import uahb.m1gl.gestionscolarite.service.ClasseService;

import java.util.List;

@RestController
@RequestMapping("/classe")
public class ClasseController {
    private final ClasseHelper classeHelper;
    public ClasseController(ClasseHelper classeHelper) {
        this.classeHelper = classeHelper;
    }

    @GetMapping
    public @ResponseBody List<ClasseResponse> allClasses(){

        return classeHelper.findaAllClasses();
    }
    @PostMapping
    public @ResponseBody ClasseResponse saveClasse (@RequestBody ClasseRequest classeRequest){

        return classeHelper.saveClasse(classeRequest);
    }
}
