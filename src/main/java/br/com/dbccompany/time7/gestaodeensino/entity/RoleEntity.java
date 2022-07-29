package br.com.dbccompany.time7.gestaodeensino.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "role")
@Getter
@Setter
public class RoleEntity implements GrantedAuthority {

    @Id
    @SequenceGenerator(name = "ROLE_SEQ", sequenceName = "seq_role", allocationSize = 1)
    @GeneratedValue(generator = "ROLE_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_role")
    private Integer idRole;

    @Column(name = "role")
    private String role;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_role",
            joinColumns = @JoinColumn(name = "id_role"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<UsuarioEntity> usuarioEntities;

    @Override
    public String getAuthority() {
        return role;
    }
}
