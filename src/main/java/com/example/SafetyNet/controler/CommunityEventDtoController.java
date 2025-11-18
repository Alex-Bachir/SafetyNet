package com.example.SafetyNet.controler;

import com.example.SafetyNet.dto.PersonEmailDto;
import com.example.SafetyNet.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/communityEmails")
public class CommunityEventDtoController {

    private PersonService personService;

    @GetMapping
    public List<PersonEmailDto> getEmailsByCity(@RequestParam String city) {
        return personService.getEmailsByCity(city);
    }
}
