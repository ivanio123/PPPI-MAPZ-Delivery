package com.pppi.novaposhta.util;

import com.pppi.novaposhta.entity.DimensionsFare;

import java.util.List;

public class CsvFileDimensionsFareReader {

    public static List<DimensionsFare> readDimensionsFaresCsv(String filename){
        return CsvFileReader.readCsv(filename,  (String[] parameters) -> DimensionsFare.builder()
                .id(Long.parseLong(parameters[0]))
                .dimensionsFrom(Integer.parseInt(parameters[1]))
                .dimensionsTo(Integer.parseInt(parameters[2]))
                .price(Double.parseDouble(parameters[3])).build()
        );
    }
}
