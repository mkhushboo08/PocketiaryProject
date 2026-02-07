package com.pocketiary.pocketiary.service;

import com.pocketiary.pocketiary.entity.NoteEntity;
import com.pocketiary.pocketiary.exception.NoteNotFoundException;
import com.pocketiary.pocketiary.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoteService implements INoteService {
    private final NoteRepository noteRepository;

    @Override
    public NoteEntity saveNote(NoteEntity note) {
        return noteRepository.save(note);
    }

    @Override
    public List<NoteEntity> getAllNotes(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return noteRepository.findAll(pageRequest).getContent();
    }

    @Override
    public NoteEntity getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));

    }

    @Override
    public List<NoteEntity> getNotesByCategory(String category) {
        return noteRepository.findByCategory(category);
    }

    @Override
    public NoteEntity updateNoteById(Long id, NoteEntity newNote) {
        NoteEntity oldNote = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));

        oldNote.setTitle(newNote.getTitle());
        oldNote.setCategory(newNote.getCategory());
        oldNote.setContent(newNote.getContent());
        noteRepository.save(oldNote);
        return oldNote;
    }

    @Override
    public void deleteNoteById(Long id) {
        NoteEntity byId = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
        noteRepository.deleteById(byId.getId());
    }

    @Override
    public void deleteAllNotes() {
        noteRepository.deleteAll();
    }
}