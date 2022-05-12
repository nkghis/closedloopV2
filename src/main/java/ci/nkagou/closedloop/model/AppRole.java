package ci.nkagou.closedloop.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "App_Role", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_ROLE_UK", columnNames = "Role_Name") })
/*@Data*/ @NoArgsConstructor @AllArgsConstructor @Getter
@Setter
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Role_Id", nullable = false)
    private Long roleId;

    @Column(name = "Role_Name", length = 30, nullable = false)
    private String roleName;

    public AppRole(String roleName) {
        this.roleName = roleName;
    }
}