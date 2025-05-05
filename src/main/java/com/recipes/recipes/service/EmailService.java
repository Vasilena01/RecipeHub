package com.recipes.recipes.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
