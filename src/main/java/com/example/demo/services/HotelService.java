package com.example.demo.services;

import com.example.demo.entities.Hotel;
import com.example.demo.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Iterable<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Integer id) {
        return hotelRepository.findById(id);
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(Integer id) {
        hotelRepository.deleteById(id);
    }

    public void saveHotelWithImage(Hotel hotel, MultipartFile imageFile) throws IOException {
        byte[] imageData = imageFile.getBytes();
        hotel.setImage(imageData);
        hotelRepository.save(hotel);
    }


}
