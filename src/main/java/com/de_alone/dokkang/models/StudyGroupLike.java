package com.de_alone.dokkang.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyGroupLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "studygroup_id")
    private StudyGroupPost studygroupId;

    public StudyGroupLike(StudyGroupPost studygroup_id, User user_id) {
        this.studygroupId = studygroup_id;
        this.userId = user_id;
    }
}
