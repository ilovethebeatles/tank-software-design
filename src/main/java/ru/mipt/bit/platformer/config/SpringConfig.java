package ru.mipt.bit.platformer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mipt.bit.platformer.factory.GameUnitFactory;
import ru.mipt.bit.platformer.factory.GraphicsFactory;
import ru.mipt.bit.platformer.factory.impl.DefaultGameUnitFactory;
import ru.mipt.bit.platformer.factory.impl.DefaultGraphicsFactory;
import ru.mipt.bit.platformer.handler.InputHandler;

@Configuration
public class SpringConfig {
    @Bean
    public GameUnitFactory gameUnitFactory() {
        return new DefaultGameUnitFactory();
    }

    @Bean
    public GraphicsFactory graphicsFactory() {
        return new DefaultGraphicsFactory();
    }

    @Bean
    public InputHandler inputHandler() {
        return new InputHandler();
    }
}
