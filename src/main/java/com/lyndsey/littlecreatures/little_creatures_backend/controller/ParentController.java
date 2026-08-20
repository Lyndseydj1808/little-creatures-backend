package com.lyndsey.littlecreatures.little_creatures_backend.controller;

import com.lyndsey.littlecreatures.little_creatures_backend.DTO.ParentRequestDTO;
import com.lyndsey.littlecreatures.little_creatures_backend.DTO.ParentResponseDTO;
import com.lyndsey.littlecreatures.little_creatures_backend.model.Parent;
import com.lyndsey.littlecreatures.little_creatures_backend.repository.ChildRepository;
import com.lyndsey.littlecreatures.little_creatures_backend.repository.ParentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("parent")
public class ParentController {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @PostMapping
    public ParentResponseDTO createParent(@Valid @RequestBody ParentRequestDTO dto) {
        Parent parent = new Parent(dto.getEmail(), dto.getPassword(), dto.getFirstName(), dto.getLastName());
        parent = parentRepository.save(parent);

        ParentResponseDTO response = new ParentResponseDTO();
        response.setParentId(parent.getParentId());
        response.setEmail(parent.getEmail());
        response.setFirstName(parent.getFirstName());
        response.setLastName(parent.getLastName());

        return response;
    }

    @GetMapping("{parentId}")
    public ParentResponseDTO getParent(@PathVariable int parentId) {
        Parent parent = parentRepository.findById(parentId).orElseThrow();

        ParentResponseDTO response = new ParentResponseDTO();
        response.setParentId(parent.getParentId());
        response.setEmail(parent.getEmail());
        response.setFirstName(parent.getFirstName());
        response.setLastName(parent.getLastName());

        return response;
    }

    @PutMapping("{parentId}")
    public ParentResponseDTO updateParent(@PathVariable int parentId, @Valid @RequestBody ParentRequestDTO dto) {
        Parent existingParent = parentRepository.findById(parentId).orElseThrow();

        existingParent.setEmail(dto.getEmail());
        existingParent.setPwHash(dto.getPassword());
        existingParent.setFirstName(dto.getFirstName());
        existingParent.setLastName(dto.getLastName());

        Parent updatedParent = parentRepository.save(existingParent);

        ParentResponseDTO response = new ParentResponseDTO();
        response.setParentId(updatedParent.getParentId());
        response.setEmail(updatedParent.getEmail());
        response.setFirstName(updatedParent.getFirstName());
        response.setLastName(updatedParent.getLastName());

        return response;
    }


    @DeleteMapping("{parentId}")
    public void deleteParent(@PathVariable int parentId) {
        parentRepository.deleteById(parentId);
    }
}


