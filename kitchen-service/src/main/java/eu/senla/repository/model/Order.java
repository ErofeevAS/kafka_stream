package eu.senla.repository.model;

import eu.senla.repository.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private Long id;
    private BigDecimal price;
    private Long user_id;
    private List<String> items;
    private OrderStatus status = OrderStatus.NEW;
}
