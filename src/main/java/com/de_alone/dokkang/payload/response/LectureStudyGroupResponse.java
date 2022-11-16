package com.de_alone.dokkang.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class LectureStudyGroupResponse {
    private String status;

    private List<StudyGroupLecture> studygroups;

    private Optional<String> before;
}
