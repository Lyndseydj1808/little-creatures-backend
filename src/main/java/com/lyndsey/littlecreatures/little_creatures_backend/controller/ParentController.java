package com.lyndsey.littlecreatures.little_creatures_backend.controller;

import com.lyndsey.littlecreatures.little_creatures_backend.DTO.ChildRequestDTO;
import com.lyndsey.littlecreatures.little_creatures_backend.DTO.ChildResponseDTO;
import com.lyndsey.littlecreatures.little_creatures_backend.DTO.ParentRequestDTO;
import com.lyndsey.littlecreatures.little_creatures_backend.DTO.ParentResponseDTO;
import com.lyndsey.littlecreatures.little_creatures_backend.model.Child;
import com.lyndsey.littlecreatures.little_creatures_backend.model.Parent;
import com.lyndsey.littlecreatures.little_creatures_backend.repository.ChildRepository;
import com.lyndsey.littlecreatures.little_creatures_backend.repository.ParentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @PostMapping("{parentId}/child")
    public ChildResponseDTO createChild(@PathVariable int parentId, @Valid @RequestBody ChildRequestDTO dto) {
        Parent parent = parentRepository.findById(parentId).orElseThrow();
        Child child = new Child(dto.getName(), dto.getAge(), dto.getStarCount(), dto.getCreatureChoice());
        parent.addChild(child);
        child = childRepository.save(child);


        ChildResponseDTO response = new ChildResponseDTO();
        response.setChildId(child.getChildId());
        response.setName(child.getName());
        response.setAge(child.getAge());
        response.setStarCount(child.getStarCount());
        response.setCreatureChoice(child.getCreatureChoice());
        response.setParentId(child.getParent().getParentId());

        return response;
    }

    @GetMapping("{parentId}/childList")
    public List<ChildResponseDTO> getChildList(@PathVariable int parentId) {
        Parent parent = parentRepository.findById(parentId).orElseThrow();
        List<Child> childList = parent.getChildList();
        List<ChildResponseDTO> responseList = new ArrayList<>();

        for (Child child : childList) {
            ChildResponseDTO response = new ChildResponseDTO();
            response.setChildId(child.getChildId());
            response.setName(child.getName());
            response.setAge(child.getAge());
            response.setStarCount(child.getStarCount());
            response.setCreatureChoice(child.getCreatureChoice());
            response.setParentId(child.getParent().getParentId());
            responseList.add(response);
        }

        return responseList;
        }
    }


