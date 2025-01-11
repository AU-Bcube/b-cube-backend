package com.b_cube.website.domain.contact.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "kakaotalk_link")
    private String kakaotalkLink;

    @Column(name = "instagram_link")
    private String instagramLink;

}
