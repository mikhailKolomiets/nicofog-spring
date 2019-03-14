package entity;

import javax.persistence.*;

/**
 * Created by mihail on 11/9/18.
 */
@Entity
@Table(name = "user")
public class User {

    public User() {

    }

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String role;
    private String password;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Cigarette cigarette;
    private Integer money;

    /**
     * Price 20 cigarettes of user smoke
     */
    @Column(name = "cigarette_price")
    private Integer cigarettePrice;

    /**
     * Represent like LocalDateTime.now() to string value
     */
    @Column(name = "registration_date")
    private String dateRegistration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getCigarettePrice() {
        return cigarettePrice;
    }

    public void setCigarettePrice(Integer cigarettePrice) {
        this.cigarettePrice = cigarettePrice;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(String dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public Cigarette getCigarette() {
        return cigarette;
    }

    public void setCigarette(Cigarette cigarette) {
        this.cigarette = cigarette;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
