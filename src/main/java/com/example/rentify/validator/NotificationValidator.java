package com.example.rentify.validator;

import com.example.rentify.dto.NotificationDTO;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class NotificationValidator implements Validator {

    private final UserRepository userRepository;

        @Override
        public boolean supports(Class<?> clazz) {
            return clazz.isAssignableFrom(NotificationDTO.class);
        }

        @Override
        public void validate(Object target, Errors errors) {
            NotificationDTO notificationDTO = (NotificationDTO) target;
            validateId(notificationDTO.getId(), errors);
            validateMessage(notificationDTO.getMessage(),errors);
            validateSender(notificationDTO.getSenderUsername(),errors);
            validateReceiver(notificationDTO.getReceiverUsername(),errors);
        }

        private void validateId(Integer id, Errors errors) {
            if (id != null) errors.rejectValue("id", "id.error", "Id should't be sent!");
        }

        private void validateMessage(String message, Errors errors) {
            if (message == null) {
                errors.rejectValue("message", "message.required", "Message is required!");
            } else if (message.trim().equals("")) { //if comment is empty
                errors.rejectValue("message", "message.empty", "Message is empty!");
            }
        }

        private void validateSender(String user, Errors errors) {
            if (user != null) errors.rejectValue("senderUsername", "sender.error", "Sender should't be sent!");
        }

        private void validateReceiver(String user, Errors errors) {
            if (user == null) errors.rejectValue("receiverUsername", "user.required", "Receiver should be sent!");
            else if (!userRepository.existsByUsername(user)) errors.rejectValue("receiverUsername", "user.error", "Receiver not found!");
        }
}