package ci.nkagou.closedloop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "typeoperations", uniqueConstraints = {
        @UniqueConstraint(columnNames = "typeoperation_nom")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Typeoperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "typeoperation_id", nullable = false)
    private Long typeoperationId;

    @Column(name = "typeoperation_nom")
    private String typeoperationNom;


    public Typeoperation(String typeoperationNom) {
        this.typeoperationNom = typeoperationNom;
    }
}
