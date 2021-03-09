package uz.pdp.appjparelationships.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.demomap.model.Area;


@EqualsAndHashCode(callSuper = true)
@Data
public class DistrictDAO extends Area {

    private Integer regionId;

}
