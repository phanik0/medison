package com.medison.mysql.note.service;

import com.medison.mysql.note.domain.Note;
import com.medison.mysql.note.domain.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public Note createNote(Note note) {
//        note.setRegDate(new Timestamp(System.currentTimeMillis()));
        return noteRepository.save(note);
    }

    public Note getNoteByStudykey(Long studykey) {
        return noteRepository.findByStudykey(studykey);
    }
}
