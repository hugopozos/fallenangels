package com.example.fallenangels.http.controllers;

import com.example.fallenangels.http.response.CountryResponse;
import com.example.fallenangels.repository.Database2.CountryRepository;
import com.example.fallenangels.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryRepository countryRepository;
    private final CountryService countryService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CountryResponse>> getAllCountries()
    {
        List<CountryResponse> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }

}
