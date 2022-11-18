package com.de_alone.dokkang.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyGroupComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "studygroup_id")
    private StudyGroupPost studyGroupId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created_at;

    @Column(columnDefinition = "TEXT")
    private String content;

    public StudyGroupComment(StudyGroupPost studygroup_id, User user_id, Date created_at, String content) {
        this.studyGroupId = studygroup_id;
        this.userId = user_id;
        this.created_at = created_at;
        this.content = content;
    }
    public StudyGroupComment(Long id, StudyGroupPost studygroup_id, User user_id, Date created_at, String content) {
        this.id = id;
        this.studyGroupId = studygroup_id;
        this.userId = user_id;
        this.created_at = created_at;
        this.content = content;
    }
}
