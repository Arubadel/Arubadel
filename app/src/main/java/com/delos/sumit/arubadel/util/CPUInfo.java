package com.delos.sumit.arubadel.util;

/**
 * Created by: veli
 * Date: 10/21/16 12:55 AM
 */

public class CPUInfo
{
    public String governor = null;
    public long speedCurrent = 0;
    public long speedMin = 0;
    public long speedMax = 0;

    // Wait idle
    public boolean lock = false;

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Maximum speed: " + speedMax / 1000 + "MHz");
        sb.append("\n");
        sb.append("Minimum speed: " + speedMin / 1000 + "MHz");
        sb.append("\n");
        sb.append("Current Speed: " + speedCurrent / 1000 + "MHz");
        sb.append("\n");
        sb.append("Active governor: " + governor);

        return sb.toString();
    }
}
