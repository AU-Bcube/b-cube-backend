package com.b_cube.website.domain.contact.api;

import com.b_cube.website.domain.contact.dto.ContactDTO;
import com.b_cube.website.domain.contact.service.ContactService;
import com.b_cube.website.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ContactController", description = "연락 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @Operation(summary = "연락 목록 조회")
    @GetMapping
    public ResponseEntity<ContactDTO> getContact() {
        ContactDTO contact = contactService.getContact();
        return ResponseEntity.ok(contact);
    }

    @Operation(summary = "연락 목록 추가", description = "form-data 형식으로 진행해야 함")
    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addContact(
            @Parameter(description = "이메일(문자열)")
            @RequestParam("email") String email,
            @Parameter(description = "인스타그램 주소(문자열)")
            @RequestParam("instagramLink") String instagramLink,
            @Parameter(description = "카카오톡 주소(문자열)")
            @RequestParam("kakaotalkLink") String kakaotalkLink
    ) {
        BaseResponse baseResponse = contactService.addContact(email, instagramLink, kakaotalkLink);
        return ResponseEntity.ok(baseResponse);
    }

    @Operation(summary = "연락 목록 수정", description = "form-data 형식으로 진행해야 함")
    @PatchMapping("/update")
    public ResponseEntity<ContactDTO> updateContact(
            @Parameter(description = "이메일(문자열)")
            @RequestParam("email") String email,
            @Parameter(description = "인스타그램 주소(문자열)")
            @RequestParam("instagramLink") String instagramLink,
            @Parameter(description = "카카오톡 주소(문자열)")
            @RequestParam("kakaotalkLink") String kakaotalkLink
    ) {
        ContactDTO contact = contactService.updateContact(email, instagramLink, kakaotalkLink);
        return ResponseEntity.ok(contact);
    }


}
