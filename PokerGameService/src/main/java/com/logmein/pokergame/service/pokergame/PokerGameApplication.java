package com.logmein.pokergame.service.pokergame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PokerGameApplication
{	
    public static void main( String[] args )
    {
    	ApplicationContext applicationContext = SpringApplication.run(PokerGameApplication.class);
    	
    }
}
