# currency-conversion-service


Enabling Feign for an application
=================================

To include Feign in the project, we have to add to the dependencies 
the spring-cloud-starter-feign artifact or spring-cloud-starter-openfeign.

<dependency>
<groupId>org.springframework.cloud</groupId> 
<artifactId>spring-cloud-starter-feign</artifactId>
</dependency>


The next step is to enable Feign for the application by annotating a main or a configuration class with @EnableFeignClients. 
This annotation will result in a search for all clients implemented in the application.
 We may also reduce the number of client used by setting the clients or basePackages annotation properties, 
for example, @EnableFeignClients(clients = {AccountClient.class, Product.class}). Here's the main class of the order-service application:

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderApplication {  
  public static void main(String[] args) {     
   SpringApplication.run(OrderApplication.class, args);
 }    

}




Building Feign interfaces
==========================
      An approach where only an interface with some annotations has to be created to provide a component is standard for Spring Framework. 
For Feign, an interface must be annotated with @FeignClient(name = "..."). 
It has one required property name, which corresponds to the invoked microservice name if service discovery is enabled. 
Otherwise, it is used together with the url property, where we can set a concrete network address.
 @FeignClient is not the only annotation that needs to be used here. 
 
 @FeignClient(name = "account-service")
 public interface AccountClient { 
 
 @PutMapping("/withdraw/{accountId}/{amount}")   
 Account withdraw(@PathVariable("accountId") Long id, @PathVariable("amount") int amount);
 
 }
