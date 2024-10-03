package UserAuthantication.UserCheckValidOrNot.utill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
//This annotation ensures that any null fields in this class are excluded from the JSON serialization.

public class GenricResponse {
//Declares a public class named `GenricResponse`.

 public int code;
 // A public integer field named `code` to store a status or response code.

 public String message;
 // A public string field named `message` to store a message, typically used for a status message or error description.

 public Object object;
 // A public field of type `Object` named `object` to store any additional data or response object. It can hold any type of data.

 public GenricResponse(int code, String message, Object object) {
     // Constructor for the `GenricResponse` class, which initializes the `code`, `message`, and `object` fields.
     this.code = code;
     // Assigns the value of the parameter `code` to the instance variable `code`.
     this.message = message;
     // Assigns the value of the parameter `message` to the instance variable `message`.
     this.object = object;
     // Assigns the value of the parameter `object` to the instance variable `object`.
 }
}
