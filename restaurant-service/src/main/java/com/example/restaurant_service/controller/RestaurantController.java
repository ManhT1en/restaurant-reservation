package com.example.restaurant_service.controller;

import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // [1] Tạo nhà hàng
    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant) {
        restaurantRepository.save(restaurant);
        return ResponseEntity.ok("Created restaurant " + restaurant.getName());
    }

    // [2] Lấy danh sách nhà hàng
    @GetMapping
    public ResponseEntity<?> getAllRestaurants() {
        return ResponseEntity.ok(restaurantRepository.findAll());
    }

    // [3] Lấy chi tiết 1 nhà hàng
    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> found = restaurantRepository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found.get());
    }

    // [4] Sửa nhà hàng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable Long id, 
                                              @RequestBody Restaurant updated) {
        Optional<Restaurant> found = restaurantRepository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Restaurant restaurant = found.get();
        restaurant.setName(updated.getName());
        restaurant.setAddress(updated.getAddress());
        restaurant.setTotalTables(updated.getTotalTables());
        restaurantRepository.save(restaurant);
        return ResponseEntity.ok("Updated restaurant " + id);
    }

    // [5] Xóa nhà hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        if (!restaurantRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        restaurantRepository.deleteById(id);
        return ResponseEntity.ok("Deleted restaurant " + id);
    }
}
