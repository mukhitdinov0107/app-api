package uz.pdp.appapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotNull(message = "F.I.SH bo'sh bolmasliki kerak")
    private String fullName;

    @NotNull(message = "Phone number bo'sh bolmasliki kerak")
    private String phoneNumber;

    @NotNull(message = "Adres bo'sh bolmasliki kerak")
    private String address;
}
