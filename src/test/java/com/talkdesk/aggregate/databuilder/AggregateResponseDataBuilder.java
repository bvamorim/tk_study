package com.talkdesk.aggregate.databuilder;

import com.talkdesk.aggregate.model.datatransferobject.AggregateResponse;
import com.talkdesk.aggregate.model.datatransferobject.PrefixDTO;
import com.talkdesk.aggregate.model.datatransferobject.SectorSumDTO;
import com.talkdesk.aggregate.model.domainvalue.Sector;

import java.util.ArrayList;
import java.util.List;

public class AggregateResponseDataBuilder {


    public static AggregateResponse getValidAggregateResponse() {

        AggregateResponse aggResponse = new AggregateResponse();
        List<PrefixDTO> listPrefixDto = new ArrayList<PrefixDTO>();

        PrefixDTO prefixOneDTO = new PrefixDTO();
        prefixOneDTO.setPrefix("1");
        List<SectorSumDTO> listSectorSum = new ArrayList<SectorSumDTO>();

        SectorSumDTO sectorSumBanking = new SectorSumDTO(Sector.Banking, 1);
        SectorSumDTO sectorSumTechnology  = new SectorSumDTO(Sector.Technology, 1);
        listSectorSum.add(sectorSumTechnology);
        listSectorSum.add(sectorSumBanking);


        prefixOneDTO.setListSectorSum(listSectorSum);

        PrefixDTO prefix3519DTO = new PrefixDTO();
        prefix3519DTO.setPrefix("3519173");
        List<SectorSumDTO> listSector3519Sum = new ArrayList<SectorSumDTO>();

        SectorSumDTO sectorSumClothing = new SectorSumDTO(Sector.Clothing, 2);
        listSector3519Sum.add(sectorSumClothing);

        prefix3519DTO.setListSectorSum(listSector3519Sum);
        listPrefixDto.add(prefixOneDTO);
        listPrefixDto.add(prefix3519DTO);


        aggResponse.setPrefixSumList(listPrefixDto);

        aggResponse.transformToJson();

        return aggResponse;
    }

    public static AggregateResponse getEmptyAggregateResponse() {

        return new AggregateResponse();

    }
}
