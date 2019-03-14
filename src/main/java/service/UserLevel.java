package service;

/**
 * Help count user time for next smoke
 */
public enum UserLevel {
    ZERO(0, 100, 0),
    ONE(1, 100, 1),
    TWO(2, 95, 2),
    THREE(3, 90, 4),
    FOUR(4, 80, 7),
    FIVE(5, 67, 9),
    SIX(6, 50, 11),
    SEVEN(7, 30, 15),
    EIGHT(8, 6, 20),
    NINE(9, 0, 30);

    private int value;
    private int percent;
    private int dayAfterRegistration;

    UserLevel(int value, int percent, int dayAfterRegistration) {
        this.value = value;
        this.percent = percent;
        this.dayAfterRegistration = dayAfterRegistration;
    }

    public int getValue() {
        return value;
    }

    public int getPercent() {
        return percent;
    }

    public UserLevel getByValue(int value) {
        for (UserLevel lvl : UserLevel.values()) {
            if (lvl.value == value) {
                return lvl;
            }
        }
        return ZERO;
    }

    public UserLevel getByDays(int days) {
        UserLevel userLevel = ZERO;
        for (UserLevel lvl : UserLevel.values()) {
            if (lvl.value > userLevel.value && lvl.dayAfterRegistration <= days) {
                userLevel = lvl;
            }
        }
        return userLevel;
    }

    public int getDayAfterRegistration() {
        return dayAfterRegistration;
    }
}
