// UserNomineeServiceImpl.java
package com.policymanagement.profile.service;

import com.policymanagement.profile.dao.UserNomineeDAO;
import com.policymanagement.profile.entity.UserNominee;
import com.policymanagement.profile.entity.Userprofile;
import com.policymanagement.profile.exception.ResourceNotFoundException;
import com.policymanagement.profile.model.UserNomineeDTO;
import com.policymanagement.profile.service.UserNomineeService;
import com.policymanagement.profile.service.UserprofileService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNomineeImplementation implements UserNomineeService {

    private final UserNomineeDAO userNomineeDAO;
    private final UserprofileService userprofileService;

    @Autowired
    public UserNomineeImplementation(UserNomineeDAO userNomineeDAO, UserprofileService userprofileService) {
        this.userNomineeDAO = userNomineeDAO;
        this.userprofileService = userprofileService;
    }

    @Override
    public void addNominee(String username, UserNomineeDTO nomineeDTO) {
        Userprofile userprofile = userprofileService.getUserByUsername(username);

        UserNominee userNominee = new UserNominee(
                nomineeDTO.getFullName(),
                nomineeDTO.getDateOfBirth(),
                nomineeDTO.getGender(),
                nomineeDTO.getIdProofType(),
                nomineeDTO.getIdProofDocument(),
                nomineeDTO.getNationality(),
                nomineeDTO.getNomineeRelation(),
                userprofile
        );

        userNomineeDAO.save(userNominee);
    }

    @Override
    public UserNomineeDTO getNominee(String username) {
        Userprofile userprofile = userprofileService.getUserByUsername(username);
        Optional<UserNominee> userNomineeOptional = userNomineeDAO.findByUserProfile(userprofile);

        UserNominee userNominee = userNomineeOptional.orElseThrow();

        UserNomineeDTO nomineeDTO = new UserNomineeDTO();
        nomineeDTO.setFullName(userNominee.getFullName());
        nomineeDTO.setDateOfBirth(userNominee.getDateOfBirth());
        nomineeDTO.setGender(userNominee.getGender());
        nomineeDTO.setIdProofType(userNominee.getIdProofType());
        nomineeDTO.setIdProofDocument(userNominee.getIdProofDocument());
        nomineeDTO.setNationality(userNominee.getNationality());
        nomineeDTO.setNomineeRelation(userNominee.getNomineeRelation());

        return nomineeDTO;
    }


    @Override
    public void removeNominee(String username) {
        Userprofile userprofile = userprofileService.getUserByUsername(username);
        UserNominee userNominee = userNomineeDAO.findByUserProfile(userprofile).orElseThrow();

        userNomineeDAO.delete(userNominee);
    }
}
