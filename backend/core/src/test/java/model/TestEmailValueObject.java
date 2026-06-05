package model;

import exception.EmailException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestEmailValueObject {
    @Test
    void testEmail_Success() {
        EmailValueObject email= EmailValueObject.createEmail("romi@gmail.com");
        EmailValueObject email1= EmailValueObject.createEmail("romi@hotmail.com");
        EmailValueObject email2= EmailValueObject.createEmail("romi@example.com");
        EmailValueObject email3= EmailValueObject.createEmail("romi@example12.com");

        Assertions.assertNotNull(email);
        Assertions.assertNotNull(email1);
        Assertions.assertNotNull(email2);
        Assertions.assertNotNull(email3);

    }
    @Test
    void testEmail_Success2() {
        EmailValueObject email= EmailValueObject.createEmail("roMI@gmail.com");
        EmailValueObject email1= EmailValueObject.createEmail("romi@hotMAIL.com");
        EmailValueObject email2= EmailValueObject.createEmail("RoMI@EXample.com");


       Assertions.assertEquals("romi@gmail.com",email.getEmail());
       Assertions.assertEquals("romi@hotmail.com",email1.getEmail());

       Assertions.assertEquals("romi@example.com",email2.getEmail());


    }
    @Test
    void testEmail_NotSuccess_EmptyEmail_Null() {

        Assertions.assertThrows(EmailException.class,()->EmailValueObject.createEmail(""));
        Assertions.assertThrows(EmailException.class,()->EmailValueObject.createEmail(null));


    }
    @Test
    void testEmail_NotSuccess_invalidFormat() {

        Assertions.assertThrows(EmailException.class,()->EmailValueObject.createEmail("romigmail.com"));
        Assertions.assertThrows(EmailException.class,()->EmailValueObject.createEmail("romi@hotmail"));
        Assertions.assertThrows(EmailException.class,()->EmailValueObject.createEmail("romi@ example. com"));
        Assertions.assertThrows(EmailException.class,()->EmailValueObject.createEmail("romi@@@@example12.com"));

    }
}
