package uahb.m1gl.gestionscolarite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClasseUpdateRequest {
    private long id ;
    private ClasseRequest classeRequest;
}
