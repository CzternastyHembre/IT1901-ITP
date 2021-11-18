package it1901.rest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
public class RestApplicationTest {

    @Autowired
    private RestController restController;

    @Test
    public void contextLoads(){
        assertThat(restController).isNotNull();
    }
}
