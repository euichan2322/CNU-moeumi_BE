package bibimping_be.bibimping_be2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "business_group")
public class BusinessGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
