package com.example.SpringJwtStudyOnYoutube.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record JoinDto (
    String userName,
    String password
)
{

}
