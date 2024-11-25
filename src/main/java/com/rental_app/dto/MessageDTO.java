package com.rental_app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Long id;
    private Long rentalId;
    private Long userId;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
