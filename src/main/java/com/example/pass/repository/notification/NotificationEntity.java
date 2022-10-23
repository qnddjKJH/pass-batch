package com.example.pass.repository.notification;

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
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notification")
public class NotificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationSeq;
    private String uuid;

    private NotificationEvent event;
    private String text;
    private boolean sent;
    private LocalDateTime sentAt;

    public static NotificationEntity of(String uuid, NotificationEvent event, String text, boolean sent, LocalDateTime sentAt) {
        return NotificationEntity.builder()
                .uuid(uuid)
                .event(event)
                .text(text)
                .sent(sent)
                .sentAt(sentAt)
                .build();
    }

    public void successSendMessage() {
        this.sent = true;
        this.sentAt = LocalDateTime.now();
    }
}
