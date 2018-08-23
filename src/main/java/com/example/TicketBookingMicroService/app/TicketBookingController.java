package com.example.TicketBookingMicroService.app;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class TicketBookingController {


private static ConcurrentHashMap<Integer,Seat> seats = new ConcurrentHashMap<>();
private static Set<Movie> movies = new HashSet<>();

    @PostMapping("/addMovie")
    public ResponseEntity<String> addMovies(@RequestBody Movie movie  ) {
        try{
            movies.add(movie);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/getAllMovies")
    public @ResponseBody List<Movie> getAllMovies() throws Exception {
        try {
            return movies.stream().collect(Collectors.toList());
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
@GetMapping("/bookTicket")
public ResponseEntity<String> bookTicket(@RequestParam("seat") int n){
        if(n>0 && n<=100){
            Seat s= seats.get(n);
            Date d= new Date();
            if(s != null){
                if(s.getStatus() == Status.FREE || (d.getTime()-s.getBlockedTime().getTime())> 120000) {
                    s.setBlockedTime(new Date());
                    s.setStatus(Status.BOOKED);
                    seats.put(n, s);
                    return ResponseEntity.ok().body("Seat Booked");
                }
                else{
                    s.setStatus(Status.BLOCKED);
                    return ResponseEntity.ok().body("Seat Not available/Blocked");
                }

            }else{
                s= new Seat();
                s.setStatus(Status.BOOKED);
                s.setBlockedTime(new Date());
                seats.put(n,s);
                return ResponseEntity.ok().body("Seat Booked");
            }
        }

    return ResponseEntity.ok().body("Seat Not valid");
}
/*

    @PostMapping
    public ResponseEntity<String> addMovies(@RequestBody Movie movies  ) {
        try{
            hospitalService.addHospital(hospital);
        }catch(Exception e){ e.printStackTrace();}
        return ResponseEntity.ok().body("success");
    }

    @PutMapping
    public ResponseEntity<String> bookMovie(@RequestBody Movie hospital) {
        try{
            hospitalService.updateHospital(hospital);
        }catch(Exception e){ e.printStackTrace();}
        return ResponseEntity.ok().body("success");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteHospital(@RequestBody Hospital hospital) {
        try{
            hospitalService.deleteHospital(hospital);
        }catch(Exception e){ e.printStackTrace();}
        return ResponseEntity.ok().body("success");
    }
*/
}


