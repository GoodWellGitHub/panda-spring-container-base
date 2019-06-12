package com.org.wa.hj.elasticsearch.domain;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    String name;
    private String stuNO;
    private String sex;
    private int age;

    public static Student getStudent() {
        return Student.builder().name("林黛玉").sex("女").age(18).stuNO("1101").build();
    }
}
