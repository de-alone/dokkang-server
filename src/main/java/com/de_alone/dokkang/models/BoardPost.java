package com.de_alone.dokkang.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lectureId;

    @CreationTimestamp
    private Timestamp created_at;

    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    public BoardPost(Lecture lecture_id, User user_id, String title, String content) {
        this.lectureId = lecture_id;
        this.userId = user_id;
        this.title = title;
        this.content = content;
    }
    public BoardPost(Long id, Lecture lecture_id, User user_id, String title, String content) {
        this.id = id;
        this.lectureId = lecture_id;
        this.userId = user_id;
        this.title = title;
        this.content = content;
    }
    //For Test

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
