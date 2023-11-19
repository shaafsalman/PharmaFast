package Helpers;

import java.io.Serializable;

public class AdminConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private float vatRate;
    private int adminCode;

    public AdminConfig(float vatRate, int adminCode) {
        this.vatRate = vatRate;
        this.adminCode = adminCode;
    }

    // Getters and Setters
    public float getVatRate() {
        return vatRate;
    }

    public void setVatRate(float vatRate) {
        this.vatRate = vatRate;
    }

    public int getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(int adminCode) {
        this.adminCode = adminCode;
    }
}
