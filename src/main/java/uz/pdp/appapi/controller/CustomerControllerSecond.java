package uz.pdp.appapi.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appapi.dto.ApiResponse;
import uz.pdp.appapi.dto.CustomerDto;
import uz.pdp.appapi.entity.Customer;
import uz.pdp.appapi.service.CustomerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)

public class CustomerControllerSecond {
    final
    CustomerService customerService;

    public CustomerControllerSecond(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/customer")
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("api/customer/{id}")
    public HttpEntity<Customer> getCustomer(@PathVariable Integer id){
        Customer customerById = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerById);
    }

    @PostMapping("api/customer")
    public HttpEntity<ApiResponse> addCustomer(@Valid @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.addCustomer(customerDto);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @PutMapping("api/customer/{id}")
    public ApiResponse editCustomer(@PathVariable Integer id, @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.editCustomer(customerDto, id);
        return apiResponse;
    }
    @DeleteMapping("api/customer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id){
        ApiResponse apiResponse = customerService.deleteCustomer(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202:409).body(apiResponse);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
