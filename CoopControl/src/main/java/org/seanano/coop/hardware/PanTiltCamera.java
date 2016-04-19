package org.seanano.coop.hardware;

import org.seanano.coop.model.AbstractCamera;
import org.seanano.coop.model.Camera;

class PanTiltCamera extends AbstractCamera {
    /**
     * Creates a new PanTiltCamera.
     * 
     * @param id identifier of the camera
     * @param description human readable description of the camera
     */
    PanTiltCamera(Integer id, String description) {
        super(id, description);
    }

    /**
     * Creates a new PanTiltCamera.
     * 
     * @param camera existing camera
     * @param panAngle pan angle of the camera
     * @param tiltAngle tile angle of the camera
     */
    PanTiltCamera(Camera camera, Integer panAngle, Integer tiltAngle) {
        super(camera, panAngle, tiltAngle);
    }

    @Override
    public boolean isControllable() {
        return true;
    }

}
