package com.pocketiary.pocketiary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteRequestDto {

    @NotBlank(message = "Title cannot have spaces, blank or empty!")
    @Size(min = 3, max = 20)
    private String title;

    @NotBlank(message = "Category cannot have spaces, blank or empty!")
    @Size(max = 10)
    private String category;

    @NotBlank
    private String content;
}
