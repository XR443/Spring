package com.github.hardlolmaster.security.tables;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Data
@Table(name = "acl_sid")
public class AclSid
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
    @Column(nullable = false, unique = true)
    private Boolean principal;
    @Column(nullable = false, unique = true)
    private String sid;
}
