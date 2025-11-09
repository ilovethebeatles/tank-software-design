package ru.mipt.bit.platformer.command.impl;

import ru.mipt.bit.platformer.command.Command;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class ToggleHealthBarsCommand implements Command {
    private final BooleanSupplier getter;
    private final Consumer<Boolean> setter;

    public ToggleHealthBarsCommand(BooleanSupplier getter, Consumer<Boolean> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public void execute() {
        boolean newValue = !getter.getAsBoolean();
        setter.accept(newValue);
    }
}

