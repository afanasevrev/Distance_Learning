package com.example.client_correct.test;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TestSecurity {
    private String question;
    private String reply_1;
    private String reply_2;
    private String reply_3;
    private int true_reply;
    public TestSecurity() {}
    public TestSecurity(String question, String reply_1, String reply_2, String reply_3, int true_reply) {
        this.question = question;
        this.reply_1 = reply_1;
        this.reply_2 = reply_2;
        this.reply_3 = reply_3;
        this.true_reply = true_reply;
    }
}
