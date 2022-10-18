package com.example.pass.repository.packaze;

import com.example.pass.repository.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "package")
public class PackageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageSeq;

    private String packageName;
    private Integer count;
    private Integer period;

    public static PackageEntity of(String packageName, Integer count, Integer period) {
        return new PackageEntity().builder()
                .packageName(packageName)
                .count(count)
                .period(period)
                .build();
    }
}
