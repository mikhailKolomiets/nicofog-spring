package entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represent depend one to one from user.
 * Make extend function for issue by cigarette of user
 */
@Entity
@Table(name = "cigarette")
public class Cigarette {
    public Cigarette() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "last_smoke_time")
    private String lastSmokeTime;
    @Column(name = "average_time")
    private int averageTime;
    private int level;
    @Column(name = "cigarettes_per_day")
    private int cigarettesPerDay;
    @Column(name = "all_cigarettes_smoke")
    private int allCigarettesSmoke;
    @Column(name = "one_second_economy")
    private int oneSecondEconomy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getLastSmokeTime() {
        return LocalDateTime.parse(lastSmokeTime);
    }

    public void setLastSmokeTime(LocalDateTime lastSmokeTime) {
        this.lastSmokeTime = lastSmokeTime.toString();
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCigarettesPerDay() {
        return cigarettesPerDay;
    }

    public void setCigarettesPerDay(int cigarettesPerDay) {
        this.cigarettesPerDay = cigarettesPerDay;
    }

    public int getAllCigarettesSmoke() {
        return allCigarettesSmoke;
    }

    public void setAllCigarettesSmoke(int allCigarettesSmoke) {
        this.allCigarettesSmoke = allCigarettesSmoke;
    }

    public int getOneSecondEconomy() {
        return oneSecondEconomy;
    }

    public void setOneSecondEconomy(int oneSecondEconomy) {
        this.oneSecondEconomy = oneSecondEconomy;
    }
}
