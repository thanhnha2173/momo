//package com.example.do_an.model;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Comparator;
//import java.util.Date;
//
//
//public class HisCarDateComparator implements Comparator<HisCar> {
//    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//
//    @Override
//    public int compare(HisCar hisCar1, HisCar hisCar2) {
//        Date date1 = null;
//        try {
//            date1 = formatter.parse(hisCar1.getDateFix());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        Date date2 = null;
//        try {
//            date2 = formatter.parse(hisCar2.getDateFix());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        return date1.compareTo(date2);
//    }
//}