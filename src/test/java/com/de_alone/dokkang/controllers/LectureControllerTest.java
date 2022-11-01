package com.de_alone.dokkang.controllers;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest
public class LectureControllerTest {
    @Autowired
    private MockMvc mockMvc;
    //FIXME : ERROR at mock repository, dll file are not made.
//
//    @MockBean
//    LectureRepository lectureRepository;
//
//    @DisplayName("Test of getting all lecture")
//    @Test
//    public void testGetLectures() throws Exception{
//        Lecture lecture1 = new Lecture(101L,"no", "name", "prof");
//        Lecture lecture2 = new Lecture(102L,"no2", "name2", "prof2");
//
//        List<Lecture> listOfLecture = List.of(lecture1, lecture2);
////        Mockito.when(lectureRepository.save(any(Lecture.class)))
////                .thenReturn(lecture1);
//        Mockito.when(lectureRepository.save(lecture1))
//                .thenReturn(lecture1);
//        Mockito.when(lectureRepository.save(lecture2))
//                .thenReturn(lecture2);
//        assertEquals(listOfLecture, lectureRepository.findAll());
//    }
}
