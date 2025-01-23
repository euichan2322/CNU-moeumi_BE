package bibimping_be.bibimping_be2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alarms")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_group_id", nullable = false)
    private BusinessGroup businessGroup;

    @Column(nullable = false, unique = true, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String link;

    @Column(name = "alarm_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime alarmAt;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int liked;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessGroup getBusinessGroup() {
        return businessGroup;
    }

    public void setBusinessGroup(BusinessGroup businessGroup) {
        this.businessGroup = businessGroup;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getAlarmAt() {
        return alarmAt;
    }

    public void setAlarmAt(LocalDateTime alarmAt) {
        this.alarmAt = alarmAt;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }


}
