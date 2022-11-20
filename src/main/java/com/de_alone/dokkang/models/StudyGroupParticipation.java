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
public class StudyGroupParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "studygroup_id")
    private StudyGroupPost studyGroupId;

    public StudyGroupParticipation(StudyGroupPost studygroup_id, User user_id) {
        this.studyGroupId = studygroup_id;
        this.userId = user_id;
    }

    public StudyGroupParticipation(Long id, StudyGroupPost studygroup_id, User user_id)
    {
        this.id = id;
        this.studyGroupId = studygroup_id;
        this.userId = user_id;
    }
}
