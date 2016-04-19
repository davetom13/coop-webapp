package org.seanano.coop.model;

/**
 * Abstract Camera representation. Handles everything but isControllable().
 */
public abstract class AbstractCamera implements Camera {
    private final Integer id;
    private final String description;
    private final Integer panAngle;
    private final Integer tiltAngle;

    /**
     * Creates a new AbstractCamera.
     * 
     * @param id identifier of camera
     * @param description human readable description of camera
     */
    protected AbstractCamera(Integer id, String description) {
        this.id = id;
        this.description = description;
        this.panAngle = null;
        this.tiltAngle = null;
    }

    /**
     * Creates a new AbstractCamera.
     * 
     * @param camera existing camera
     * @param panAngle pan angle of the camera
     * @param tiltAngle tile angle of the camera
     */
    protected AbstractCamera(Camera camera, Integer panAngle, Integer tiltAngle) {
        this.id = camera.getId();
        this.description = camera.getDescription();
        this.panAngle = panAngle;
        this.tiltAngle = tiltAngle;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Integer getPanAngle() {
        return panAngle;
    }

    @Override
    public Integer getTiltAngle() {
        return tiltAngle;
    }

}
