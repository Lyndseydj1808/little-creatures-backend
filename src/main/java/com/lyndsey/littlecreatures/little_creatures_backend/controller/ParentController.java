package com.lyndsey.littlecreatures.little_creatures_backend.controller;

import com.lyndsey.littlecreatures.little_creatures_backend.DTO.ParentRequestDTO;
import com.lyndsey.littlecreatures.little_creatures_backend.model.Parent;
import com.lyndsey.littlecreatures.little_creatures_backend.repository.ChildRepository;
import com.lyndsey.littlecreatures.little_creatures_backend.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parent")
public class ParentController {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @PostMapping
    public Parent newParent(@RequestBody ParentRequestDTO dto) {
        Parent parent = new Parent(dto.getEmail(), dto.getPassword(), dto.getFirstName(), dto.getLastName());
        parent = parentRepository.save(parent);
        return parent;
    }
}


