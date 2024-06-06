package com.example.Server.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "test_security_category_5")
public class TestSecurityCategory5 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "question")
    private String question;
    @Column(name = "reply_1")
    private String reply_1;
    @Column(name = "reply_2")
    private String reply_2;
    @Column(name = "reply_3")
    private String reply_3;
    @Column(name = "true_reply")
    private int true_reply;
    public TestSecurityCategory5() {}
    public TestSecurityCategory5(String question, String reply_1, String reply_2, String reply_3, int true_reply) {
        this.question = question;
        this.reply_1 = reply_1;
        this.reply_2 = reply_2;
        this.reply_3 = reply_3;
        this.true_reply = true_reply;
    }
}
