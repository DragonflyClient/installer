package net.inceptioncloud.installer.frontend.transition.supplier;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

/**
 * This type of supplier transforms forward if the boolean value is true and
 * does nothing if the value is false.
 */
public interface ForwardNothing extends IntSupplier, BooleanSupplier
{
    @Override
    default int getAsInt () {
        return getAsBoolean() ? 1 : 0;
    }
}
