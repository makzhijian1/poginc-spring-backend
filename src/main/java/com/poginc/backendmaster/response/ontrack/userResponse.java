package com.poginc.backendmaster.response.ontrack;

import com.poginc.backendmaster.entity.ontrack.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.DecimalFormat;
import java.time.YearMonth;
import java.util.*;

public class userResponse {

    java.util.Date date = new Date();
    Calendar cal = Calendar.getInstance();
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public userResponse(){

    }

    public ResponseEntity<User> getUser(User user) {
        if (user == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        else

            return new ResponseEntity(user, HttpStatus.OK);
    }

    public double calculateBudgetValues(List<String> trackInfo) {
        double expenseAmt = 0;
        if (trackInfo.size() != 0) { //trackInfo size will be 0 if user has no expenses/income tracked for the currentMonth
            String expenseString = trackInfo.get(0);
            String [] splitExpenseString = expenseString.split(",");
            if (splitExpenseString[0].equals("expense")) { //Verify that the value is an Expense Sum and not an Income sum
                expenseAmt = Double.parseDouble(splitExpenseString[1]);
            }
        }
        return expenseAmt; //Return default as 0 if trackInfo is empty
    }

    public Double calculateRemainingMthPct(){
        cal.setTime(date);
        YearMonth yearMonthObject = YearMonth.of((cal.get(Calendar.YEAR)), (cal.get(Calendar.MONTH) + 1));
        double daysInMonth = yearMonthObject.lengthOfMonth();
        double daysRemaining = (daysInMonth-(cal.get(Calendar.DATE)));
        return Double.parseDouble(df.format((daysRemaining/daysInMonth)*100));
    }

    public ResponseEntity<Object> formUserResponse (User user, List<String> trackInfo) {
        userResponseBody responseBody = new userResponseBody();
        responseBody.setPid(user.getPid());
        responseBody.setChatid(user.getChatid());
        responseBody.setUsername(user.getUsername());
        responseBody.setCurrentbankbal(user.getCurrentbankbal());
        responseBody.setCurrentcash(user.getCurrentcash());
        responseBody.setBudget(user.getBudget());
        if (responseBody.getBudget() == 0) {
            responseBody.setBudgetbal(0);
            responseBody.setBudgetbalpct(0);
            responseBody.setBudgetmthpct(0);
        } else {
            double exp = calculateBudgetValues(trackInfo);
            responseBody.setBudgetbal(user.getBudget() - exp);
            responseBody.setBudgetbalpct(Double.parseDouble(df.format(((responseBody.getBudgetbal())/(user.getBudget())) * 100)));
            responseBody.setBudgetmthpct(calculateRemainingMthPct());
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
