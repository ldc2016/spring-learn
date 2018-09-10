package com.ldc.spring.core.aspect;

/**
 * created by liudacheng on 2018/9/10.
 */
public class SerialNoContainerHolder {

    private static InheritableThreadLocal<SerialNoContainer> SERIAL_NUMBER_HOLDER = new InheritableThreadLocal();

    static void put(SerialNoContainer container) {
        SERIAL_NUMBER_HOLDER.set(container);
    }

    static void clear() {
        SERIAL_NUMBER_HOLDER.remove();
    }

    public static SerialNoContainer get() {
        return SERIAL_NUMBER_HOLDER.get();
    }

}
