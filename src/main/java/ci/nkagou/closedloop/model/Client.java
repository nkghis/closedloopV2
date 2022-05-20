package ci.nkagou.closedloop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "clients", uniqueConstraints = {
        @UniqueConstraint(columnNames = "client_name")
})
@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Long clientId;


    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_contact", nullable = true)
    private String clientContact;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "marchandId")

    private Marchand marchand;

    public Client(String clientName, String clientContact, Marchand marchand) {
        this.clientName = clientName;
        this.clientContact = clientContact;
        this.marchand = marchand;
    }
}
