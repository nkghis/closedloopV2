package ci.nkagou.closedloop.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "User_Role", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "USER_ROLE_UK", columnNames = { "User_Id", "Role_Id" }) })


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Id", nullable = false)
    private AppUser appUser;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Role_Id", nullable = false)
    private AppRole appRole;

    public UserRole(AppUser appUser, AppRole appRole) {
        this.appUser = appUser;
        this.appRole = appRole;
    }
}
