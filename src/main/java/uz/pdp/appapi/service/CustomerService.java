package uz.pdp.appapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.appapi.dto.ApiResponse;
import uz.pdp.appapi.dto.CustomerDto;
import uz.pdp.appapi.entity.Customer;
import uz.pdp.appapi.repository.CustomerRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getCustomers(){
        List<Customer> customerList = customerRepository.findAll();
        return customerList;
    }

    public Customer getCustomerById(Integer id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElse(null);
    }

    public ApiResponse addCustomer( @RequestBody CustomerDto customerDto){
        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber());
        if (existsByPhoneNumber){
            return new ApiResponse("This user already exists", false);
        }
        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);
        return new ApiResponse("Client Saved", true);
    }

    public ApiResponse editCustomer(@RequestBody CustomerDto customerDto, @PathVariable Integer id){
        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(), id);
        if (existsByPhoneNumber){
            return new ApiResponse("This phone number already exists", false);
        }
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent())
            return new ApiResponse("this customer doesn't exist", false);
        Customer customer = optionalCustomer.get();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
            return new ApiResponse("customer edited", true);
    }

    public ApiResponse deleteCustomer(@PathVariable Integer id){
        customerRepository.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
