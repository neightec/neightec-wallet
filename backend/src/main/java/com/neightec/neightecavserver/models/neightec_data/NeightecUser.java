package com.neightec.neightecavserver.models.neightec_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

//import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="neightec_users")
@Getter
@Setter
public class NeightecUser {

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

    @Column(name = "first_name", length = 100)
    @JsonProperty
    private String firstName;

    @Column(name = "last_name", length = 100)
    @JsonProperty
    private String lastName;

    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "valid_start", nullable = false)
    private Instant validStart;

    @Column(name = "valid_end")
    private Instant validEnd;
}
