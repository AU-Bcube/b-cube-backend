package com.b_cube.website.domain.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    private Long id;
    private String email;
    private String kakaotalkLink;
    private String instagramLink;

}
