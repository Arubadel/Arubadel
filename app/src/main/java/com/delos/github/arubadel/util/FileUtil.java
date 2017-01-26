package com.delos.github.arubadel.util;

import java.io.File;

/**
 * Created by sumit on 5/11/16.
 */

public class FileUtil {

    public static boolean hasSelinux() {
        return new File("/sys/fs/selinux/enforce").isFile();
    }

    public static boolean hasRoot() {
        return new File("/system/xbin/su").isFile();
    }

    public static boolean hasGpu() {
        return new File("/sys/class/kgsl/kgsl-3d0/gpuclk").isFile();
    }

    public static boolean hasFastCharge() {
        return new File("/sys/kernel/fast_charge/force_fast_charge").isFile();
    }

}
