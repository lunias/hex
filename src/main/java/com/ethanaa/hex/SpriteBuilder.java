package com.ethanaa.hex;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.codetome.hexameter.core.api.Hexagon;

import java.util.HashMap;
import java.util.Map;

public class SpriteBuilder {

    private Hexagon<GridData> hexagon;
    private Rotate mapRotate;
    private Sprite.Orientation orientation;
    private Sprite.Action action;

    private Map<Sprite.Action, Map<Sprite.Orientation, SpriteAnimation>> animationMap = new HashMap<>();

    public Sprite build() {

        return new Sprite(this);
    }

    public SpriteBuilder setHexagon(Hexagon<GridData> hexagon) {

        this.hexagon = hexagon;
        return this;
    }

    public SpriteBuilder setMapRotate(Rotate mapRotate) {

        this.mapRotate = mapRotate;
        return this;
    }

    private SpriteAnimation createAnimation(Image image, int offsetX, int offsetY, int width, int height, int count, int columns, int duration, int translateZ) {

        final Rotate spriteStandRotate = new Rotate(90, hexagon.getCenterX(), hexagon.getCenterY(), 0, Rotate.X_AXIS);
        final Rotate spriteCameraRotate = new Rotate(0, hexagon.getCenterX(), hexagon.getCenterY(), 0, Rotate.Y_AXIS);

        spriteCameraRotate.angleProperty().bind(mapRotate.angleProperty().multiply(-1.0));

        final ImageView imageView = new ImageView(image);
        imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        imageView.setX(hexagon.getCenterX() - 25);
        imageView.setY(hexagon.getCenterY() - 25);
        imageView.getTransforms().addAll(spriteStandRotate, spriteCameraRotate);
        imageView.setTranslateZ(translateZ);

        return new SpriteAnimation(
                imageView,
                Duration.millis(duration),
                count, columns,
                offsetX, offsetY,
                width, height);
    }

    public SpriteBuilder setIdleAnimation(Image image, int offsetX, int offsetY, int width, int height,
                                          int count, int columns, int duration, int translateZ) {

        SpriteAnimation animation = createAnimation(image, offsetX, offsetY,
                width, height, count, columns, duration, translateZ);

        this.animationMap.computeIfAbsent(Sprite.Action.IDLE, k -> new HashMap<>())
                .put(Sprite.Orientation.FORWARD, animation);

        return this;
    }

    public SpriteBuilder setIdleAnimationBehind(Image image, int offsetX, int offsetY, int width, int height,
                                                int count, int columns, int duration, int translateZ) {

        SpriteAnimation animation = createAnimation(image, offsetX, offsetY,
                width, height, count, columns, duration, translateZ);

        this.animationMap.computeIfAbsent(Sprite.Action.IDLE, k -> new HashMap<>())
                .put(Sprite.Orientation.BACKWARD, animation);

        return this;
    }

    public SpriteBuilder setOrientation(Sprite.Orientation orientation) {

        this.orientation = orientation;
        return this;
    }

    public SpriteBuilder setAction(Sprite.Action action) {

        this.action = action;
        return this;
    }

    public Hexagon<GridData> getHexagon() {
        return hexagon;
    }

    public Rotate getMapRotate() {
        return mapRotate;
    }

    public Sprite.Orientation getOrientation() {
        return orientation;
    }

    public Sprite.Action getAction() {
        return action;
    }

    public Map<Sprite.Action, Map<Sprite.Orientation, SpriteAnimation>> getAnimationMap() {
        return animationMap;
    }
}
