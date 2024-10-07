package com.riwi.industry.domain.models;

import com.riwi.industry.utils.enums.StatusPallet;
import com.riwi.industry.utils.enums.TypePallet;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Builder
@Table(name = "pallets")
public class Pallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private StatusPallet status;

    private String ubication;

    @Enumerated(value = EnumType.STRING)
    private TypePallet type;

    private Long maxCapacity;

    @OneToOne(mappedBy = "pallet", cascade = CascadeType.ALL)
    private User carrier;

    @OneToMany(mappedBy = "pallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Load> loads;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
