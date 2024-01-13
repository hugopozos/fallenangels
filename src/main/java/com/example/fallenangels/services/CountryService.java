package com.example.fallenangels.services;

import com.example.fallenangels.http.response.CountryResponse;
import com.example.fallenangels.models.Database2.Country;
import com.example.fallenangels.repository.Database2.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    @Autowired
    private final CountryRepository countryRepository;

    public List<CountryResponse> getAllCountries()
    {
        List<Country> countrys = countryRepository.findAll();
        return countrys
                .stream()
                .map(country -> CountryResponse.builder()
                        .id(country.getId())
                        .name(country.getName())
                        .build())
                .collect(Collectors.toList());
    }


}
