package com.policymanagement.profile.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policymanagement.profile.dao.NomineeRelationDAO;

import com.policymanagement.profile.entity.NomineeRelation;
import com.policymanagement.profile.model.NomineeRelationDTO;
import com.policymanagement.profile.util.ModelMapperUtil;


@Service
public class NomineeRelationImplementation implements NomineeRelationService {

    private final NomineeRelationDAO nomineeRelationRepository;

    @Autowired
    public NomineeRelationImplementation(NomineeRelationDAO nomineeRelationRepository) {
        this.nomineeRelationRepository = nomineeRelationRepository;
    }

    @Override
    public List<NomineeRelationDTO> getNomineeTypes() {
        List<NomineeRelation> nomineeRelations = nomineeRelationRepository.findAll();
        List<NomineeRelationDTO> nomineeTypes = new ArrayList<>();

        for (NomineeRelation nomineeRelation : nomineeRelations) {
            NomineeRelationDTO nomineeType = new NomineeRelationDTO();
            nomineeType.setId(nomineeRelation.getId());
            nomineeType.setType(nomineeRelation.getType());
            nomineeTypes.add(nomineeType);
        }

        return nomineeTypes;
    }

    @Override
    public void seedNomineeTypes() {
        if (nomineeRelationRepository.count() == 0) {
            List<String> types = Arrays.asList("Parent", "Child", "Sibling", "Spouse", "Cousin", "Other");

            for (String type : types) {
                NomineeRelation nomineeRelation = new NomineeRelation();
                nomineeRelation.setType(type);
                nomineeRelationRepository.save(nomineeRelation);
            }
        }
    }
}
