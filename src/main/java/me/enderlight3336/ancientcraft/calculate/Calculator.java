package me.enderlight3336.ancientcraft.calculate;

public class Calculator {
    final CalcType type;
    final float i;
    public Calculator(CalcType type, float i) {
        this.type = type;
        this.i = i;
    }
    public float calculate(float i0) {
        switch (type) {
            case ADDITION -> {
                return i + i0;
            }
            case MULTIPLICATION -> {
                return i * i0;
            }
            case DIVISION -> {
                return i0 / i;
            }
        }
        return i0;
    }
}
