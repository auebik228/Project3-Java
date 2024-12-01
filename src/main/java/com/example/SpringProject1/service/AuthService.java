package com.example.SpringProject1.service;

import com.example.SpringProject1.web.dto.auth.JwtRequest;
import com.example.SpringProject1.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}
