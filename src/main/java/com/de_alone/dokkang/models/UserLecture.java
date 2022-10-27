package com.de_alone.dokkang.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "userlecture",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames={"user_id", "lecture_id"}
                )
        })
public class UserLecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture_id;

}

