package net.inceptioncloud.installer;

public class Main
{
    public static void main (String[] args)
    {
        System.out.println("Running Dragonfly Installation Wizard");
        MinecraftModInstaller.INSTANCE.init();
    }
}
