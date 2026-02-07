package com.pocketiary.pocketiary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteResponseDto {
    private Long id;
    private String title;
    private String category;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM yyyy, hh:mm a", locale = "en")
    private LocalDateTime createdAt;
}
