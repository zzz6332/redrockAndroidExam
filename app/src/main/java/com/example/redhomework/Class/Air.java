package com.example.redhomework.Class;

public class Air {
    String PM10;
    String PM25;
    String SO2;
    String NO2;
    String O3;
    String CO;
    String air_count;
    String air_qulity;

    public String getAir_qulity() {
        return air_qulity;
    }

    public Air(String PM10, String PM25, String NO2, String SO2, String O3, String CO, String air_count, String qlty){
        this.PM10 = PM10;
        this.PM25 =PM25;
        this.NO2 = NO2;
        this.SO2 = SO2;
        this.O3 = O3;
        this.CO = CO;
        this.air_count = air_count;
        this.air_qulity= qlty;

    }
    public String getPM10() {
        return PM10;
    }

    public String getPM25() {
        return PM25;
    }

    public String getSO2() {
        return SO2;
    }

    public String getNO2() {
        return NO2;
    }

    public String getO3() {
        return O3;
    }

    public String getCO() {
        return CO;
    }

    public String getAir_count() {
        return air_count;
    }



}
