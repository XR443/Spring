package com.github.hardlolmaster.security.tables;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "acl_object_identity")
public class AclObjectIdentity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    @GeneratedValue(generator = "sequence-generator")
    //    @GenericGenerator(
    //            name = "sequence-generator",
    //            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    //            parameters = {
    //                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "user_sequence"),
    //                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "100"),
    //                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
    //            }
    //    )
    private Long id;
    @JoinColumn(name = "object_id_class", nullable = false, unique = false)
    @OneToOne
    private AclClass objectClass;
    @Column(name = "object_id_identity", nullable = false, unique = false)
    private Long objectIdIdentity;
    @JoinColumn(name = "parent_object")
    @OneToOne
    private AclObjectIdentity parentObject;
    @JoinColumn(name = "owner_sid")
    @OneToOne
    private AclSid ownerSid;
    @Column(name = "entries_inheriting", nullable = false)
    private Boolean entriesInheriting;
}
