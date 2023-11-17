package kr.co.anna.domain.model;

import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "NEW_MEMBER")
public class NewMember {

    @Id
    @Column(name = "OID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;

    @NonNull
    @Column(name = "USER_ID")
    private String userId;

    @NonNull
    @Column(name = "PASSWORD")
    private String password;

}
