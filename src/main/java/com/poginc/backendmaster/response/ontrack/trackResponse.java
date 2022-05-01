package com.poginc.backendmaster.response.ontrack;

import com.poginc.backendmaster.entity.ontrack.Track;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public class trackResponse {

    public trackResponse(){

    }

    public Track convertToLowercase(Track track) {
        track.setTrack_type(track.getTrack_type().toLowerCase());
        track.setPaymode(track.getPaymode().toLowerCase());
        track.setCat(track.getCat().toLowerCase());
        track.setDesc(track.getDesc().toLowerCase());
        track.setUpdated_on(java.time.Clock.systemUTC().instant());

        return track;
    }

    public ResponseEntity<Object> parseQueryResultToMap(List<String> rawData) {
        Map <String, Double> jsonResponse = new HashMap();
        for (String data : rawData) {
            String[] tmp = data.split(",");
            jsonResponse.put(tmp[0], Double.parseDouble(tmp[1]));
        }
        return new ResponseEntity(jsonResponse, HttpStatus.OK);
    }

    public int requestValidator(Track track){
        //Returns 0 if Request Fields are invalid
        //If Request is validated, return -1 if it is an expense, or return 1 if it is an income
        int valid = 0;

        String [] validTrackTypes = {"expense", "income"};
        String [] validPaymodes = {"debit", "credit", "cash"};
        String [] validCats = {"transport","food","insurance","lifestyle","fun","bills","others", "salary"};

        if( Arrays.asList(validTrackTypes).contains(track.getTrack_type()) ){
            if( Arrays.asList(validPaymodes).contains(track.getPaymode()) ) {
                if( Arrays.asList(validCats).contains(track.getCat()) ) {
                    if (track.getTrack_type().equals("expense") ){
                        valid = -1;
                    }
                    else {
                        valid = 1;
                    }
                }
            }
        }
        else
            valid = 0;
        return valid;
    }
}
