package bibimping_be.bibimping_be2.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "business_group")
public class BusinessGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @OneToMany(mappedBy = "businessGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Alarm> alarms;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
}
