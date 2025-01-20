package bibimping_be.bibimping_be2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bookmark_group")
public class BookmarkGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_group_id")
    private BusinessGroup businessGroupId;

    private int liked;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BusinessGroup getBusinessGroupId() {
        return businessGroupId;
    }
    public void setBusinessGroupId(BusinessGroup businessGroupId) {
        this.businessGroupId = businessGroupId;
    }
    public int getLiked() { return liked; }
    public void setLiked(int like) { this.liked = like; }

    public BookmarkGroup(Long userId, BusinessGroup businessGroupId, int like) {
        this.userId = userId;
        this.businessGroupId = businessGroupId;
        this.liked = like;
    }
    public BookmarkGroup() {}
}