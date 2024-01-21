package com.example.demo.controllers;

import com.example.demo.entities.Hotel;
import com.example.demo.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public Iterable<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Integer id) {
        Optional<Hotel> hotel = hotelService.getHotelById(id);
        return hotel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*@PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.saveHotel(hotel);
    }*/

    @PostMapping
    public ResponseEntity<String> createHotelWithImage(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("imageFile")MultipartFile imageFile
            ) {
        try {
            Hotel hotel = new Hotel();
            hotel.setName(name);
            hotel.setAddress(address);

            hotelService.saveHotelWithImage(hotel, imageFile);

            return ResponseEntity.ok("Hotel created successfully with image.");
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating hotel with image");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Integer id) {
        if (hotelService.getHotelById(id).isPresent()) {
            hotelService.deleteHotel(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
