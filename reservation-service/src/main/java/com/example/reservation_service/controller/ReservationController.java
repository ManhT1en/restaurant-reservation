package com.example.reservation_service.controller;

import com.example.reservation_service.entity.Reservation;
import com.example.reservation_service.repository.ReservationRepository;
import com.example.reservation_service.config.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AmqpTemplate amqpTemplate; // Để gửi message lên RabbitMQ

    // [1] Tạo đặt chỗ
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation req) {
        // Trong thực tế, bạn có thể gọi sang Restaurant Service để kiểm tra còn bàn trống
        // hay không. Ở đây, ví dụ đơn giản, ta bỏ qua check.
        if (req.getDateTime() == null) {
            req.setDateTime(LocalDateTime.now().plusDays(1)); // ví dụ set mặc định
        }
        Reservation saved = reservationRepository.save(req);

        // Gửi message "reservation created" lên RabbitMQ
        amqpTemplate.convertAndSend(
            RabbitConfig.EXCHANGE_NAME, 
            RabbitConfig.ROUTING_KEY, 
            saved // Có thể gửi object, JSON, string...
        );

        return ResponseEntity.ok("Created reservation with ID=" + saved.getId());
    }

    // [2] Lấy danh sách đặt chỗ
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationRepository.findAll());
    }

    // [3] Lấy chi tiết 1 đặt chỗ
    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id) {
        Optional<Reservation> found = reservationRepository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found.get());
    }

    // [4] Hủy đặt chỗ (có thể PATCH hoặc DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
        if (!reservationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reservationRepository.deleteById(id);
        return ResponseEntity.ok("Cancelled reservation " + id);
    }
}
