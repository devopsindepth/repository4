package hello;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user")
public class UserController {

@Autowired
private UserRepository userRepository;

@RequestMapping(value = "/list", method= RequestMethod.GET)
public Iterable list(Model model){
    Iterable userList = userRepository.findAll();
    return userList;
}

@RequestMapping(value = "/show/{id}", method= RequestMethod.GET)
public User showUser(@PathVariable Integer id, Model model){
   User user = userRepository.findById(String.valueOf(id)).get();
    return user;
}

@RequestMapping(value = "/add", method = RequestMethod.POST)
public ResponseEntity saveProduct(@RequestBody User user){
	if (userRepository.findByFirstName(user.firstName) == null)
	{
    userRepository.save(user);
    return new ResponseEntity("User saved successfully", HttpStatus.OK);
	}
	else
	{
		return new ResponseEntity("User already exists", HttpStatus.UNPROCESSABLE_ENTITY);
	}
}

@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody User user){
    User user1 = userRepository.findById(String.valueOf(id)).get();
    user1.setFirstName(user.firstName);
    user1.setLastName(user.lastName);
    user1.setEmailAddress(user.emailAddress);
    user1.setPhonenumber(user.phonenumber);
    user.setRole(user.role);
    userRepository.save(user1);
    return new ResponseEntity("User updated successfully", HttpStatus.OK); 
}

@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
public ResponseEntity delete(@PathVariable Integer id){
    userRepository.deleteById(String.valueOf(id));
    return new ResponseEntity("User deleted successfully", HttpStatus.OK);

}

}