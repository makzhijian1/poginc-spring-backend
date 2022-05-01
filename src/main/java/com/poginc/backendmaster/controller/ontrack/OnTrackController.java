package com.poginc.backendmaster.controller.ontrack;

import com.poginc.backendmaster.entity.ontrack.Track;
import com.poginc.backendmaster.entity.ontrack.User;
import com.poginc.backendmaster.entity.ontrack.UserHist;
import com.poginc.backendmaster.repository.ontrack.TrackRepository;
import com.poginc.backendmaster.repository.ontrack.UserHistRepository;
import com.poginc.backendmaster.repository.ontrack.UserRepository;
import com.poginc.backendmaster.response.ontrack.trackResponse;
import com.poginc.backendmaster.response.ontrack.userResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RestController
@Validated
@Api(description = "OnTrack Controller - Users/Trackers/Budgets")
@RequestMapping("/ontrack")
public class OnTrackController {
    @Autowired
    private TrackRepository trackRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserHistRepository userHistRepo;


    private static final String users_url = "/users";
    private static final String track_url = "/track";

    private userResponse userResp = new userResponse();
    private trackResponse trackResp = new trackResponse();

    /* ============================= Users API ============================= */
    @ApiOperation(value = "This method returns all users in DB.ot_users")
    @GetMapping(users_url + "/get_all_users")
    @Valid
    public ResponseEntity<List<User>> getAllUsers(){
        List <User> res = this.userRepo.findAll();
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @ApiOperation(value = "This method returns info for specified user in path")
    @GetMapping(users_url + "/get_user"+ "/{chatid}")
    @Valid
    public ResponseEntity<Object> getUser(
            @PathVariable(value = "chatid") long chatId,
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "month", required = false) String month) {
        ResponseEntity respToReturn;
        if ((year == null) || (month == null)) {
            java.util.Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int currentMthNum = cal.get(Calendar.MONTH);
            List<String> trackInfo = (trackRepo.getMonthlyOverviewByrefchatid(chatId, ++currentMthNum, cal.get(Calendar.YEAR)));
            respToReturn = this.userResp.formUserResponse(this.userRepo.findBychatid(chatId), trackInfo);
        } else {
            List<String> trackInfo = (trackRepo.getMonthlyOverviewByrefchatid(chatId, Integer.parseInt(month), Integer.parseInt(year)));
            respToReturn = this.userResp.formUserResponse(this.userRepo.findBychatid(chatId), trackInfo);
        }
        return respToReturn;
    }

    @ApiOperation(value = "This method updates User Current Bank Bal value")
    @PutMapping(users_url + "/set_user/cbb"+ "/{chatid}")
    @Valid
    public ResponseEntity<String> setUserCurrentBankBal(
            @RequestParam double newBalance,
            @PathVariable(value = "chatid") long chatId){
        ResponseEntity resp;
        try{
            User user = this.userRepo.findBychatid(chatId);
            user.setCurrentbankbal(newBalance);
            this.userRepo.save(user);
            resp = new ResponseEntity("Success", HttpStatus.OK);
        }
        catch (Exception e) {
            resp = new ResponseEntity("Failure", HttpStatus.BAD_REQUEST);
        }
        return resp;
    }

    @ApiOperation(value = "This method updates User Current Cash value")
    @PutMapping(users_url + "/set_user/cc"+ "/{chatid}")
    @Valid
    public ResponseEntity<String> setUserCurrentCashBal(
            @RequestParam double newBalance,
            @PathVariable(value = "chatid") long chatId){
        ResponseEntity resp;
        try{
            User user = this.userRepo.findBychatid(chatId);
            user.setCurrentcash(newBalance);
            this.userRepo.save(user);
            resp = new ResponseEntity("Success", HttpStatus.OK);
        }
        catch (Exception e){
            resp = new ResponseEntity("Failure", HttpStatus.BAD_REQUEST);
        }
        return resp;
    }

    @ApiOperation(value = "This method updates User Budget value")
    @PutMapping(users_url + "/set_user/budget"+ "/{chatid}")
    @Valid
    public ResponseEntity<String> setUserBudget(
            @RequestParam double budget,
            @PathVariable(value = "chatid") long chatId){
        ResponseEntity resp;
        try{
            User user = this.userRepo.findBychatid(chatId);
            user.setBudget(budget);
            this.userRepo.save(user);
            resp = new ResponseEntity("Success", HttpStatus.OK);
        }
        catch (Exception e){
            resp = new ResponseEntity("Failure", HttpStatus.BAD_REQUEST);
        }
        return resp;
    }

    @PostMapping(users_url + "/add_user")
    @ApiOperation(value = "This method adds a new user to DB.ot_users")
    public ResponseEntity<User> addUser (@RequestBody @Valid User user){
        this.userRepo.save(user);
        User newUser = this.userRepo.findBychatid(user.getChatid());
        return userResp.getUser(newUser);
    }

    @DeleteMapping(users_url + "/delete_user/{chatid}")
    @ApiOperation(value = "This method deletes a specified user")
    public ResponseEntity<String> deleteUser(@PathVariable("chatid") long chatId) {
        ResponseEntity resp;
        try {
            User existingUser = this.userRepo.findBychatid(chatId);
            this.userRepo.delete(existingUser);
            resp = new ResponseEntity("Success", HttpStatus.OK);
        }
        catch (Exception e) {
            resp = new ResponseEntity("Failure", HttpStatus.BAD_REQUEST);
        }
        return resp;
    }
    /* ============================= Users API ============================= */

    /* ============================= Track API ============================= */
    @ApiOperation(value = "This method returns all records in DB.ot_track")
    @GetMapping(track_url + "/get_all_trackinfo")
    @Valid
    public List<Track> getAllTrackInfo(){
        return this.trackRepo.findAll();
    }

    @ApiOperation(value = "This method returns track info records based on specified user")
    @GetMapping(track_url + "/get_userinfo"+ "/{chatid}")
    @Valid
    public List<Track> getUserTrackInfo(
            @PathVariable("chatid") long chatId,
            @RequestParam boolean limit //if false return all else return latest 30
    ){
        if (!limit)
            return this.trackRepo.findByrefchatid(chatId);
        else
            return this.trackRepo.findTop30Byrefchatid(chatId);
    }

    @ApiOperation(value = "This method returns the Monthly Overview (mthov) of Income & Expenses")
    @GetMapping(track_url + "/get_mthov/{chatid}")
    @Valid
    public ResponseEntity<Object> getMthOV(@PathVariable(value = "chatid") long chatid, @RequestParam int month, @RequestParam int year){
        return this.trackResp.parseQueryResultToMap(
                this.trackRepo.getMonthlyOverviewByrefchatid(chatid, month, year)
        );
    }

    @ApiOperation(value = "This method returns the Monthly Expenses (mthex) by Category")
    @GetMapping(track_url + "/get_mthex/{chatid}")
    @Valid
    public ResponseEntity<Object> getMthex(@PathVariable(value = "chatid") long chatid, @RequestParam int month, @RequestParam int year){
        return this.trackResp.parseQueryResultToMap(
                this.trackRepo.getMonthlyExpenseByrefchatid(chatid, month, year)
        );
    }

    @PostMapping(track_url + "/add") //This method needs to be updated to also update ot_users table based on logic
    @ApiOperation(value = "This method adds a new activity record to DB.ot_track")
    public ResponseEntity<Track> addTrackInfo (@RequestBody @Valid Track track){
        ResponseEntity response = null;
        User user = this.userRepo.findBychatid(track.getRefchatid());
        Track parsedTrack = this.trackResp.convertToLowercase(track); //Convert all field values to lowercase first
        int ValidationResult = this.trackResp.requestValidator(parsedTrack);
        if(ValidationResult == 0){
            response = new ResponseEntity(parsedTrack, HttpStatus.BAD_REQUEST);
        }
        else {
            this.trackRepo.save(this.trackRepo.save(parsedTrack));
            if(ValidationResult == 1) {
                user.setCurrentbankbal((user.getCurrentbankbal() + track.getAmount()));
                this.userRepo.save(user);
            }
            else {
                user.setCurrentbankbal((user.getCurrentbankbal() - track.getAmount()));
                this.userRepo.save(user);
            }
            List<Integer> currentMthPIDs = this.trackRepo.getLatestPid(track.getRefchatid()); //Retrieve Sorted List of Track PIDs for user this month
            Track savedTrack = this.trackRepo.findBypid(currentMthPIDs.get(0));
            response = new ResponseEntity(savedTrack, HttpStatus.OK);
        }

        return response;
    }

    @DeleteMapping(track_url + "/remove/{pid}")
    @ApiOperation(value = "This method deletes a track record, based on given pid")
    public ResponseEntity<Track> deleteTrackInfo(@PathVariable("pid") int pid){
        ResponseEntity resp;
        try{
            Track existingTrack = this.trackRepo.findBypid(pid);
            this.trackRepo.delete(existingTrack);
            resp = new ResponseEntity("Success", HttpStatus.OK);
        }
        catch(Exception e){
            resp = new ResponseEntity("Failure", HttpStatus.BAD_REQUEST);
        }
        return resp;
    }

    /* ============================= Budget API ============================= */
    @ApiOperation(value = "This method returns all user history records in DB.ot_h_users")
    @GetMapping(users_url + "/get_all_userhist_info")
    @Valid
    public List<UserHist> getAllUserHistInfo() {
        return this.userHistRepo.findAll();

    }

}
