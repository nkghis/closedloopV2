package ci.nkagou.closedloop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "marchands", uniqueConstraints = {
        @UniqueConstraint(columnNames = "marchand_name")
})
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Marchand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marchand_id", nullable = false)
    private Long marchandId;


    @Column(name = "marchand_name", nullable = false)
    private String marchandName;

    @Column(name = "marchand_contact", nullable = true)
    private String marchandContact;

    public Marchand(String marchandName, String marchandContact) {
        this.marchandName = marchandName;
        this.marchandContact = marchandContact;
    }
}

