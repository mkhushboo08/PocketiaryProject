package com.pocketiary.pocketiary.service;

import com.pocketiary.pocketiary.entity.NoteEntity;

import java.util.List;

public interface INoteService {

    NoteEntity saveNote(NoteEntity note);

    List<NoteEntity> getAllNotes(int page, int size);

    NoteEntity getNoteById(Long id);

    List<NoteEntity> getNotesByCategory(String category);

    NoteEntity updateNoteById(Long id, NoteEntity newNote);

    void deleteNoteById(Long id);

    void deleteAllNotes();

}
