package com.b_cube.website.domain.contact.api;

import com.b_cube.website.domain.contact.dto.ContactDTO;
import com.b_cube.website.domain.contact.service.ContactService;
import com.b_cube.website.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<ContactDTO> getContact() {
        ContactDTO contact = contactService.getContact();
        return ResponseEntity.ok(contact);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addContact(
            @RequestParam("email") String email,
            @RequestParam("instagramLink") String instagramLink,
            @RequestParam("kakaotalkLink") String kakaotalkLink
    ) {
        BaseResponse baseResponse = contactService.addContact(email, instagramLink, kakaotalkLink);
        return ResponseEntity.ok(baseResponse);
    }

    @PatchMapping("/update")
    public ResponseEntity<ContactDTO> updateContact(
            @RequestParam("email") String email,
            @RequestParam("instagramLink") String instagramLink,
            @RequestParam("kakaotalkLink") String kakaotalkLink
    ) {
        ContactDTO contact = contactService.updateContact(email, instagramLink, kakaotalkLink);
        return ResponseEntity.ok(contact);
    }


}
