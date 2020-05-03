package com.elastic.search.service;

import com.elastic.search.model.StudentModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    List<StudentModel> findAll();

    boolean batchAdd();

    String delete(String id);

    Object update();

    boolean add();

    Object singleWord(String word, Pageable pageable);

    Object singleMatch(String key, String value, Pageable pageable);

    Object singlePhraseMatch(String word, Pageable pageable);

    Object singleTerm(String id, String interest, Pageable pageable);

    Object multiMatch(String content, Pageable pageable);

    Object contain(String content, Pageable pageable);

    Object containPercent(String content, Pageable pageable);
}
