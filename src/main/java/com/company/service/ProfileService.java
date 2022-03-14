package com.company.service;

import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto) { // admin
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLogin(dto.getLogin());
        entity.setPswd(dto.getPswd());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());

        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Profile not found"));
    }

}
