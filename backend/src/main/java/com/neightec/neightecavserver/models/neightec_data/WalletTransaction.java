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
@Table(name="wallet_transaction")
@Getter
@Setter
public class WalletTransaction {

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

    @Column(name = "category_type", length = 50)
    @JsonProperty
    private String category_type;

    @Column(name = "price", precision = 10, scale = 2)
    @JsonProperty
    private BigDecimal price;

    @Column(name = "currency_type", length = 50)
    @JsonProperty
    private String currency_type;

    @Column(name="timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name="wallet_transaction_type_id", nullable = false)
    private UUID wallet_transaction_type_id;
}
