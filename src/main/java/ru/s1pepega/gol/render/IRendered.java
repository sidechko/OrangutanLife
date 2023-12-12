package ru.s1pepega.gol.render;

public interface IRendered {
    static CameraSpaceRenderer camera = null;
    public default boolean is3D() {return false;}; // default 2d
    public void render(double yAngle, double xAngle);
}
