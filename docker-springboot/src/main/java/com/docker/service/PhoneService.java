package com.docker.service;

import com.docker.entity.Phone;
import com.docker.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public Phone createPhone(Phone phone){
        Phone save = this.phoneRepository.save(phone);
        return save;
    }

    public List<Phone> getAllPhone(){
        List<Phone> all = this.phoneRepository.findAll();
        return all;
    }

}
