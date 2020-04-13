package eu.senla.repository.model;

import eu.senla.repository.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private BigDecimal price;
    @Column
    private Long user_id;
    //    @ElementCollection
    @Transient
    private List<String> items;
    @Column
    private OrderStatus status = OrderStatus.NEW;
}
