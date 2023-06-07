package ir.ac.kntu.model;

import java.util.Objects;

public class GamingMonitor extends Accessory {
    private double refreshRate;

    private double size;

    private double responseTime;

    public GamingMonitor(String name, double price, String description, int count, double refreshRate, double size,
                         double responseTime) {
        super(name, price, description, count);
        this.refreshRate = refreshRate;
        this.size = size;
        this.responseTime = responseTime;
    }

    public double getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(double refreshRate) {
        this.refreshRate = refreshRate;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }

    @Override public String toString() {
        return "GamingMonitor{" + "refreshRate=" + refreshRate + ", size=" + size + ", responseTime=" + responseTime +
                '}';
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
        GamingMonitor that = (GamingMonitor) o;
        return Double.compare(that.refreshRate, refreshRate) == 0 && Double.compare(that.size, size) == 0 &&
                Double.compare(that.responseTime, responseTime) == 0;
    }

    @Override public int hashCode() {
        return Objects.hash(super.hashCode(), refreshRate, size, responseTime);
    }
}
