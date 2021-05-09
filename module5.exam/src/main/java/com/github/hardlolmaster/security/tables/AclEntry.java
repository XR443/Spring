package com.github.hardlolmaster.security.tables;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Data
@Table(name = "acl_entry")
public class AclEntry
{
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator = "sequence-generator")
//    @GenericGenerator(
//            name = "sequence-generator",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter(name = "sequence_name", value = "user_sequence"),
//                    @Parameter(name = "initial_value", value = "100"),
//                    @Parameter(name = "increment_size", value = "1")
//            }
//    )
    private Long id;
    @JoinColumn(name = "acl_object_identity", nullable = false)
    @OneToOne
    private AclObjectIdentity aclObjectIdentity;
    @Column(name = "ace_order", nullable = false, unique = false)
    private Integer aceOrder;
    @JoinColumn(name = "sid", nullable = false)
    @OneToOne
    private AclSid sid;
    @Column(name = "mask", nullable = false)
    private Integer mask;
    @Column(name = "granting", nullable = false)
    private Boolean granting;
    @Column(name = "audit_success", nullable = false)
    private Boolean auditSuccess;
    @Column(name = "audit_failure", nullable = false)
    private Boolean auditFailure;
}
