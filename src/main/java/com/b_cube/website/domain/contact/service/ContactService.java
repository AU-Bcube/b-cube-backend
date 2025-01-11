package com.b_cube.website.domain.contact.service;

import com.b_cube.website.domain.contact.dto.ContactDTO;
import com.b_cube.website.domain.contact.entity.Contact;
import com.b_cube.website.domain.contact.exception.ContactAlreadyExistsException;
import com.b_cube.website.domain.contact.exception.ContactNotFoundException;
import com.b_cube.website.domain.contact.repository.ContactRepository;
import com.b_cube.website.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final String SUCCESS_CONTACT_UPLOAD = "연락처 업로드가 완료되었습니다.";

    public ContactDTO getContact() {
        Contact contact = contactRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ContactNotFoundException("연락처 정보가 존재하지 않습니다."));

        return ContactDTO.builder()
                .email(contact.getEmail())
                .instagramLink(contact.getInstagramLink())
                .kakaotalkLink(contact.getKakaotalkLink())
                .build();
    }

    public BaseResponse addContact(String email, String instagramLink, String kakaotalkLink) {
        if (contactRepository.count() > 0) {
            throw new ContactAlreadyExistsException("이미 다른 연락처 정보가 존재합니다.");
        }

        // DB에 저장
        Contact contact = Contact.builder()
                .email(email)
                .instagramLink(instagramLink)
                .kakaotalkLink(kakaotalkLink)
                .build();
        contactRepository.save(contact);

        return BaseResponse.builder()
                .message(SUCCESS_CONTACT_UPLOAD)
                .build();
    }

    public ContactDTO updateContact(String email, String instagramLink, String kakaotalkLink) {
        Contact existingContact = contactRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ContactNotFoundException("연락처 정보가 존재하지 않습니다."));

        // 새로운 객체를 빌더 패턴으로 생성
        Contact updatedContact = Contact.builder()
                .id(existingContact.getId()) // 기존 ID 유지
                .email(email)
                .instagramLink(instagramLink)
                .kakaotalkLink(kakaotalkLink)
                .build();

        contactRepository.save(updatedContact);

        return convertToContactDTO(updatedContact);
    }

    private ContactDTO convertToContactDTO(Contact contact) {
        return ContactDTO.builder()
                .id(contact.getId())
                .email(contact.getEmail())
                .kakaotalkLink(contact.getKakaotalkLink())
                .instagramLink(contact.getInstagramLink())
                .build();
    }
}
