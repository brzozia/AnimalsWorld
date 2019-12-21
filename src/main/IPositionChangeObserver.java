package main;

public interface IPositionChangeObserver {
    void positionChanged(Animal cow, Vector2D oldPosition, Vector2D newPosition);
}