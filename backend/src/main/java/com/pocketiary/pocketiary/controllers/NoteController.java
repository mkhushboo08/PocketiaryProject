package com.pocketiary.pocketiary.controllers;

import com.pocketiary.pocketiary.dto.NoteRequestDto;
import com.pocketiary.pocketiary.dto.NoteResponseDto;
import com.pocketiary.pocketiary.entity.NoteEntity;
import com.pocketiary.pocketiary.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NoteController {

    private final NoteService noteService;

    private NoteResponseDto toResponseDto(NoteEntity entity) {
        NoteResponseDto dto = new NoteResponseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setCategory(entity.getCategory());
        dto.setContent(entity.getContent());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private List<NoteResponseDto> toResponseList(List<NoteEntity> entities) {
        List<NoteResponseDto> dtos = new ArrayList<>();
        for (NoteEntity entity : entities) {
            dtos.add(toResponseDto(entity));
        }
        return dtos;
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@Valid @RequestBody NoteRequestDto noteRequestDto) {
        NoteEntity note = new NoteEntity();
        note.setTitle(noteRequestDto.getTitle());
        note.setCategory(noteRequestDto.getCategory());
        note.setContent(noteRequestDto.getContent());

        NoteEntity saved = noteService.saveNote(note);
        NoteResponseDto responseDto = toResponseDto(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getAllNotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        List<NoteEntity> allNotes = noteService.getAllNotes(page, size);
        List<NoteResponseDto> responseList = toResponseList(allNotes);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long id) {
        NoteEntity noteById = noteService.getNoteById(id);
        NoteResponseDto responseDto = toResponseDto(noteById);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllNotes() {
        noteService.deleteAllNotes();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNoteById(@PathVariable Long id,@Valid @RequestBody NoteRequestDto note) {
        NoteEntity entity = new NoteEntity();
        entity.setTitle(note.getTitle());
        entity.setCategory(note.getCategory());
        entity.setContent(note.getContent());
        return ResponseEntity.ok(toResponseDto(noteService.updateNoteById(id, entity)));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<NoteResponseDto>> getNotesByCategory(@PathVariable String category) {
        List<NoteEntity> noteByCategory = noteService.getNotesByCategory(category);
        return ResponseEntity.ok(toResponseList(noteByCategory));
    }


}
