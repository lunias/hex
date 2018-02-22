package com.ethanaa.hex;


import javafx.animation.Animation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import org.codetome.hexameter.core.api.Hexagon;

import java.util.HashMap;
import java.util.Map;

public class Sprite extends ImageView {

    private Hexagon<GridData> hexagon;
    private Rotate mapRotate;

    private Map<Action, Map<Orientation, SpriteAnimation>> animationMap = new HashMap<>();

    private SpriteAnimation currentAnimation;

    private ObjectProperty<Action> currentAction = new SimpleObjectProperty<>();
    private ObjectProperty<Orientation> currentOrientation = new SimpleObjectProperty<>();

    public enum Orientation {BACKWARD, FORWARD}

    public enum Action {IDLE}

    public Sprite(SpriteBuilder spriteBuilder) {

        this.hexagon = spriteBuilder.getHexagon();
        this.mapRotate = spriteBuilder.getMapRotate();

        this.animationMap = spriteBuilder.getAnimationMap();

        this.currentAction.addListener((observable, oldAction, newAction) -> {

            Map<Orientation, SpriteAnimation> orientationSpriteAnimationMap = animationMap.get(newAction);
            if (orientationSpriteAnimationMap == null) {
                return;
            }

            SpriteAnimation animation = orientationSpriteAnimationMap.get(currentOrientation.get());
            if (animation == null) {
                animation = orientationSpriteAnimationMap.get(Orientation.FORWARD);
                if (animation == null) {
                    return;
                }
            }

            startAnimation(animation);
        });

        this.currentOrientation.addListener((observableValue, oldOrientation, newOrientation) ->  {

            Map<Orientation, SpriteAnimation> orientationSpriteAnimationMap = animationMap.get(currentAction.get());
            if (orientationSpriteAnimationMap == null) {
                return;
            }

            SpriteAnimation animation = orientationSpriteAnimationMap.get(newOrientation);
            if (animation == null) {
                return;
            }

            startAnimation(animation);
        });

        this.currentAction.set(spriteBuilder.getAction());
        this.currentOrientation.set(spriteBuilder.getOrientation());

        this.mapRotate.angleProperty().addListener((observableValue, oldAngle, newAngle) -> {

            double absoluteAngle = Math.abs(newAngle.doubleValue());

            if (absoluteAngle == 90 || absoluteAngle == 270) {
                this.currentOrientation.set(this.currentOrientation.get() == Orientation.FORWARD
                        ? Orientation.BACKWARD : Orientation.FORWARD);
            }

        });
    }

    private void stopCurrentAnimation() {

        if (this.currentAnimation == null) return;

        this.currentAnimation.stop();
    }

    private void startAnimation(SpriteAnimation animation) {

        stopCurrentAnimation();

        ImageView animationImageView = animation.getImageView();

        setImage(animationImageView.getImage());
        setViewport(animationImageView.getViewport());
        setSmooth(animationImageView.isSmooth());
        setPreserveRatio(animationImageView.isPreserveRatio());
        setFitWidth(animationImageView.getFitWidth());
        setX(animationImageView.getX());
        setY(animationImageView.getY());
        getTransforms().clear();
        getTransforms().addAll(animationImageView.getTransforms());
        setTranslateZ(animationImageView.getTranslateZ());

        this.currentAnimation = new SpriteAnimation(this, animation);
        this.currentAnimation.setCycleCount(Animation.INDEFINITE);
        this.currentAnimation.play();
    }

    public void moveTo(Hexagon<GridData> hexagon) {

        this.hexagon = hexagon;

        final Rotate spriteStandRotate = new Rotate(90, hexagon.getCenterX(), hexagon.getCenterY(), 0, Rotate.X_AXIS);
        final Rotate spriteCameraRotate = new Rotate(0, hexagon.getCenterX(), hexagon.getCenterY(), 0, Rotate.Y_AXIS);

        for (Map<Orientation, SpriteAnimation> orientationSpriteAnimationMap : animationMap.values()) {
            for (SpriteAnimation spriteAnimation : orientationSpriteAnimationMap.values()) {

                ImageView imageView = spriteAnimation.getImageView();
                imageView.setX(hexagon.getCenterX() - 25);
                imageView.setY(hexagon.getCenterY() - 25);
                imageView.getTransforms().clear();
                imageView.getTransforms().addAll(spriteStandRotate, spriteCameraRotate);
            }
        }

        setX(hexagon.getCenterX() - 25);
        setY(hexagon.getCenterY() - 25);
        getTransforms().clear();
        getTransforms().addAll(spriteStandRotate, spriteCameraRotate);
    }

    public Hexagon<GridData> getHexagon() {
        return hexagon;
    }

    public Rotate getMapRotate() {
        return mapRotate;
    }

    public void setMapRotate(Rotate mapRotate) {
        this.mapRotate = mapRotate;
    }

    public Action getCurrentAction() {
        return currentAction.get();
    }

    public ObjectProperty<Action> currentActionProperty() {
        return currentAction;
    }

    public void setCurrentAction(Action currentAction) {
        this.currentAction.set(currentAction);
    }

    public Orientation getCurrentOrientation() {
        return currentOrientation.get();
    }

    public ObjectProperty<Orientation> currentOrientationProperty() {
        return currentOrientation;
    }

    public void setCurrentOrientation(Orientation currentOrientation) {
        this.currentOrientation.set(currentOrientation);
    }

}
