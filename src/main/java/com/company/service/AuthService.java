package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.dto.auth.AuthorizationDTO;
import com.company.dto.auth.RegistrationDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailService emailService;

    public ProfileDTO authorization(AuthorizationDTO dto) { // eshmat1
        String pswd = DigestUtils.md5Hex(dto.getPassword());

        Optional<ProfileEntity> optional = profileRepository
                .findByLoginAndPswd(dto.getLogin(), pswd);
        if (!optional.isPresent()) {
            throw new RuntimeException("Login or Password incorrect");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("You are not allowed");
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(optional.get().getName());
        profileDTO.setSurname(optional.get().getSurname());
        profileDTO.setJwt(JwtUtil.createJwt(optional.get().getId(), optional.get().getRole()));
        return profileDTO;
    }

    public void registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("Mazgi Email uje bant");
        }

        optional = profileRepository.findByLogin(dto.getLogin());
        if (optional.isPresent()) {
            throw new BadRequestException("Mazgi Login uje bant");
        }

        String pswd = DigestUtils.md5Hex(dto.getPassword());

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLogin(dto.getLogin());
        entity.setPswd(pswd);
        entity.setEmail(dto.getEmail());
        entity.setRole(ProfileRole.USER_ROLE);
        entity.setStatus(ProfileStatus.CREATED);
        profileRepository.save(entity);

        String jwt = JwtUtil.createJwt(entity.getId());
        StringBuilder builder = new StringBuilder();
        builder.append("Salom jigar Qalaysan\n");
        builder.append("Agar bu sen bo'lsang Shu linkga bos: ");
        builder.append("http://localhost:8080/auth/verification/" + jwt);

        emailService.sendEmail(dto.getEmail(), "Registration KunUz Test", builder.toString());

    }

    public void verification(String jwt) {
        Integer id = JwtUtil.decodeJwtAndGetId(jwt);

        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("User Not found");
        }
        optional.get().setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(optional.get());
    }
}
