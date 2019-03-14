package service;

import dao.CRUDbase;
import entity.Cigarette;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by mihail on 12/9/18.
 */
@Service
public class CigaretteService {

    /**
     * Change Qualifier: "cigaretteHibernate" of "cigaretteMysql" for implement one
     */
    @Autowired
    @Qualifier(value = "cigaretteHibernate")
    private CRUDbase<Cigarette> cigaretteRepository;

    /**
     * Avoid when user smoke cigarette
     */
    public Cigarette updateOnSmoke(User user) {
        Cigarette cigarette = user.getCigarette();
        cigarette.setLastSmokeTime(LocalDateTime.now());

        /**
         *  in this case todo main service of site
         */
        LocalDateTime userStartDay = LocalDateTime.parse(user.getDateRegistration());
        int dayInService = (int) userStartDay.until(LocalDateTime.now(), ChronoUnit.DAYS);

        UserLevel userLevel = UserLevel.ZERO.getByDays(dayInService);

        if (cigarette.getLevel() == 0 && dayInService > 0) {
            cigarette.setCigarettesPerDay(cigarette.getAllCigarettesSmoke());
            countAverageTimeBetweenCigarettes(cigarette);
            //seconds for economy 1 cent
            int oneCentTime =  36 * 24 * 20 / user.getCigarettePrice() / cigarette.getCigarettesPerDay();

            cigarette.setOneSecondEconomy(oneCentTime);
        }
        cigarette.setLevel(userLevel.getValue());
        LocalDateTime smokeTime = LocalDateTime.now();
        if (cigarette.getLevel() > 0) {
            moneyCount(user);
            /**
             * This one correct sleep time
             */
            int dayFix = (int) cigarette.getLastSmokeTime().until(LocalDateTime.now(), ChronoUnit.DAYS);
            user.setMoney(user.getMoney() - (dayFix * 8 * 3600 / cigarette.getOneSecondEconomy()));

            if (userLevel == UserLevel.NINE) {
                smokeTime.plusMonths(3);
            } else {
                int secondsWithoutSmoke = cigarette.getAverageTime() * 100 / userLevel.getPercent();
                smokeTime = smokeTime.plusSeconds(secondsWithoutSmoke);
            }
        }

        cigarette.setLastSmokeTime(smokeTime);
        cigarette.setAllCigarettesSmoke(cigarette.getAllCigarettesSmoke() + 1);
        return cigaretteRepository.update(cigarette);
    }

    public Cigarette getById(long id) {
        return cigaretteRepository.getById(id);
    }

    public String getMessageFromRepository() {
        return cigaretteRepository.getThrowableMessage();
    }

    /**
     *  Count seconds between smoke time and set it. Thinks those user sleep 8 hours by 24
     */
    private void countAverageTimeBetweenCigarettes(Cigarette cigarette) {
        int time = 16 * 3600 / cigarette.getAllCigarettesSmoke();
        cigarette.setAverageTime(time);
    }

    /**
     *  Count profit for no smoke time
     */
    private void moneyCount(User user) {
        int profit;
        Cigarette cigarette = user.getCigarette();
        int percentTime = (UserLevel.ZERO.getByValue(cigarette.getLevel()).getPercent());
        int nextTime;
        if (percentTime != 0) {
            nextTime = cigarette.getAverageTime() * 100 / percentTime;
        } else {
            nextTime = 3600 * 24 * 30;
        }

        int between = (int) cigarette.getLastSmokeTime().until(LocalDateTime.now(), ChronoUnit.SECONDS);
        if (between > 0) {
            between = between - cigarette.getAverageTime() + nextTime;
        }

        profit = between / cigarette.getOneSecondEconomy();
        user.setMoney(user.getMoney() + profit);
    }

}
