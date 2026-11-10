package com.neightec.neightecavserver.models.neightec_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="wallet_account")
@Getter
@Setter
public class WalletAccount {

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

    @Column(name = "name", length = 50)
    @JsonProperty
    private String name;

    @Column(name = "balance", precision = 10, scale = 2)
    @JsonProperty
    private BigDecimal balance;

    @Column(name="creation_timestamp", nullable = false)
    private Timestamp creation_timestamp;

    @Column(name="deletion_timestamp")
    private Timestamp deletion_timestamp;

    @Column(name = "is_archived")
    private Boolean is_archived;
}
