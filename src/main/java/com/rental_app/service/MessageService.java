package com.rental_app.service;

import com.rental_app.dto.MessageDTO;
import com.rental_app.model.Message;
import com.rental_app.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Optional<MessageDTO> getMessageById(Long id) {
        return messageRepository.findById(id)
                .map(this::convertToDTO);
    }

    public MessageDTO createMessage(Message message) {
        if (message.getRental() == null || message.getUser() == null || message.getMessage() == null) {
            throw new IllegalArgumentException("Rental, User, Message ne peut pas être nul ");
        }

        if (message.getCreatedAt() == null) {
            message.setCreatedAt(LocalDateTime.now());
        }
        message.setUpdatedAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        return convertToDTO(savedMessage);
    }

    private MessageDTO convertToDTO(Message message) {
        return new MessageDTO(
            message.getId(),
            message.getRental().getId(),
            message.getUser().getId(),
            message.getMessage(),
            message.getCreatedAt(),
            message.getUpdatedAt()
        );
    }
}
