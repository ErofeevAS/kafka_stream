package eu.senla.event.model;

import eu.senla.event.model.enums.OrderEventStatus;
import eu.senla.repository.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {
    @Null
    private Long id;
    private Order value;
    private OrderEventStatus status = OrderEventStatus.NEW;
}
