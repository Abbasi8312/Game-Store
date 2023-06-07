package ir.ac.kntu.model;

import java.util.Objects;

public class GamingController extends Accessory {
    private String systemCompatibility;

    private ControllerType controllerType;

    public GamingController(String name, double price, String description, int count, String systemCompatibility,
                            ControllerType controllerType) {
        super(name, price, description, count);
        this.systemCompatibility = systemCompatibility;
        this.controllerType = controllerType;
    }

    public String getSystemCompatibility() {
        return systemCompatibility;
    }

    public void setSystemCompatibility(String systemCompatibility) {
        this.systemCompatibility = systemCompatibility;
    }

    public ControllerType getControllerType() {
        return controllerType;
    }

    public void setControllerType(ControllerType controllerType) {
        this.controllerType = controllerType;
    }

    @Override public String toString() {
        return "GamingController{" + "systemCompatibility='" + systemCompatibility + '\'' + ", controllerType=" +
                controllerType + '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        GamingController that = (GamingController) o;
        return Objects.equals(systemCompatibility, that.systemCompatibility) && controllerType == that.controllerType;
    }

    @Override public int hashCode() {
        return Objects.hash(super.hashCode(), systemCompatibility, controllerType);
    }
}
