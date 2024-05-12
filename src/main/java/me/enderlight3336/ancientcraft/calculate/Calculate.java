package me.enderlight3336.ancientcraft.calculate;

public abstract class Calculater <T extend Number> {
    final CalcType type;
    final T i;
    public Calculater(CalcType type, T i) {
        this.type = type;
        this.i = i;
    }
    public <T> T calculate(T i0) {
        switch (type) {
            case CalcType.ADDITION -> return (T) (i + i0);
            case CalcType.MULTIPLICATION -> return (T) (i * i0);
            case CalcType.DIVISION -> return (T) (i0 / i);
        }
    }
}
public enum CalcType {
    ADDITION,
    MULTIPLICATION,
    DIVISION;
    CalcType () {}
}
public interface DamageCalculater{}