package com.neightec.neightecavserver.models.neightec_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="wallet_session")
@Getter
@Setter
public class WalletSession {

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

    @Column(name="is_active", nullable = false)
    private boolean isActive;

    @Column(name="start_session", nullable = false)
    private Timestamp startSession;

    @Column(name="end_session", nullable = false)
    private Timestamp endSession;
}
