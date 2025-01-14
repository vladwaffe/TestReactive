package com.testreactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
class TestReactiveApplicationTests {

    /*@Test
    void contextLoads() {
    }*/
    @Test
    void simpleFluxExample() {
        Flux<String> fluxColors = Flux.just("red", "green", "blue");
        //fluxColors.subscribe(System.out::println);
        //fluxColors.log().subscribe(System.out::println);
        fluxColors.log();
    }

}
