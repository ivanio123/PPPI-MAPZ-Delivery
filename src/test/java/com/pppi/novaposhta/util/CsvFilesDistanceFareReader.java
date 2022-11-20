package com.pppi.novaposhta.util;

import com.pppi.novaposhta.entity.DistanceFare;

import java.util.List;

public class CsvFilesDistanceFareReader {

    public static List<DistanceFare> readDistanceFaresCsv(String fileName){
        return CsvFileReader.readCsv(fileName, CsvFilesDistanceFareReader::parseDistanceFare);
    }

    private static DistanceFare parseDistanceFare(String[] params){
        Long id = Long.parseLong(params[0]);
        Integer from = Integer.parseInt(params[1]);
        Integer to = Integer.parseInt(params[2]);
        Double price = Double.parseDouble(params[3]);
        return DistanceFare.builder()
                .id(id)
                .distanceFrom(from)
                .distanceTo(to)
                .price(price)
                .build();
    }
}
