package tec.jvgualdi.user_service.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CustomerAddress {

    private String street;
    private String city;
    private String state;
    private String country;
    @Column(name = "zip_code")
    private String zipCode;
    private String neighborhood;
    private int number;
    private String complement;
}
