package com.don.usersservice.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Donald Veizi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post_ext")
public class PostExt extends BaseEntity {

    // id e user
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user; // maybe unnecessary. if i remove this -> we would have UniDirectional where only @OneToMany side is
    // present (Parent) - not reccomended generally. UniDirectional is usually ok only if we have @ManyToOne (children side only)

    // id e post qe eshte ext
    @Column(nullable = false, name = "post_id_ext")
    private Long postIdExt;

}
