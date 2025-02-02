package com.example.restservices1.ctrl;

import festival.model.Spectecol;
import festival.persistence.repository.DBRepoSpectacol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("festival/spectacol")
public class RestController1 {

    @Autowired
    private DBRepoSpectacol crrRepository;


    @GetMapping("/test")
    public  String test(@RequestParam(value="name", defaultValue="Hello") String name) {
        return name.toUpperCase();
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public Optional create(@RequestBody Spectecol crrRequest){
        System.out.println("Creating spectacol");
        return crrRepository.save(crrRequest);

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Spectecol> getAll(){
        System.out.println("Getting computerRepairRequests");
        Collection<Spectecol> all= (Collection<Spectecol>) crrRepository.findAll();
        return all;
    }

//    @GetMapping
//    public Collection<Spectecol> filterByStatus(@RequestParam (value="status", required=false)RequestStatus status){
//        Collection<Spectecol> all;
//        if (status!=null){
//            System.out.println("Getting computerRepairRequests by status "+status);
//            all=crrRepository.filterByStatus(status);
//        }else
//            all=crrRepository.findAll();
//
//        return all;
//
//    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        System.out.println("Get by id "+id);
        Optional request=crrRepository.findOne((long)id);
        if (request.isEmpty())
            return new ResponseEntity<String>("Entity not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Spectecol update(@RequestBody Spectecol user,@PathVariable Integer id) {
        System.out.println("Updating user ...");
        crrRepository.update(user, Long.valueOf(id));
        user.setId(Long.valueOf(id));
        return user;
    }
    // @CrossOrigin(origins = "http://localhost:3000")

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        System.out.println("Deleting user ... "+id);
        try {
            crrRepository.delete(id);
            return new ResponseEntity<Spectecol>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public User create(@RequestBody User user){
//        userRepository.save(user);
//        return user;
//
//    }
//    @RequestMapping(method = RequestMethod.DELETE)
//    public ResponseEntity<?> delete(@RequestBody Spectecol user){
//        System.out.println("Deleting spectacol ... "+user.getData_inc());
//
//
//        try {
//            crrRepository.delete(user);
//            return new ResponseEntity<Spectecol>(HttpStatus.OK);
//        }catch (Exception ex){
//            System.out.println("Ctrl Delete user exception");
//            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
}
