package com.byt3social.analisedocumental.dto;

import java.util.List;

public record PDSignProcessoDTO(
        String id,
        String title,
        String status,
        List<PDSignMemberDTO> members,
        List<PDSignDocumentDTO> documents

) {
}
