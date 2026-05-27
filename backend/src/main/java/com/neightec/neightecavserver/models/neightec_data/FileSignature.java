package com.neightec.neightecavserver.models.neightec_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="file_signature")
@Getter
@Setter
public class FileSignature {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", nullable = false)
    @JsonProperty
    private UUID id;

    @Column(name = "hex_signature", nullable = false)
    @JsonProperty
    private byte[] hexSignature;

    @Column(name = "iso_8859", nullable = false)
    @JsonProperty
    private String iso_8859;

    @Column(name = "extension", nullable = false)
    @JsonProperty
    private String extension;

    @Column(name = "description")
    @JsonProperty
    private String description;
}
