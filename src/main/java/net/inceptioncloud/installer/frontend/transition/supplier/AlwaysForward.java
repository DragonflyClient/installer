package net.inceptioncloud.installer.frontend.transition.supplier;

import java.util.function.IntSupplier;

/**
 * This type of supplier always transforms the the value forward.
 */
public class AlwaysForward implements IntSupplier
{
    @Override
    public int getAsInt ()
    {
        return 1;
    }
}
