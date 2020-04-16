package eu.senla.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cook {
    private Long id;
    private String firstname;
    private String lastname;
    private boolean isFree;
}
