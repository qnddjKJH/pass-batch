package com.example.pass.repository.user;

import com.example.pass.repository.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_group_mapping")
@IdClass(UserGroupMappingId.class)
public class UserGroupMappingEntity extends BaseEntity {
    @Id
    private String userGroupId;
    @Id
    private String userId;

    private String userGroupName;
    private String description;

    public static UserGroupMappingEntity of(String userGroupId, String userId, String userGroupName, String description) {
        return UserGroupMappingEntity.builder()
                .userGroupId(userGroupId)
                .userId(userId)
                .userGroupName(userGroupName)
                .description(description)
                .build();
    }
}
