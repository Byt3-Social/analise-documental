package com.byt3social.analisedocumental.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PDSignTokenDTO(
        @JsonProperty("access_token")
        String accessToken
) {
}
