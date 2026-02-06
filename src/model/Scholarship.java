package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scholarship {
    private Integer id;
    private String type;
    private String description;
    private Integer scholarship;
    private BigDecimal fullPrice;
    private String sponsor;
    private String duration;
    private Integer maxQuota;
    private Integer yearLevel;
    private String week;
    private Boolean isEnabled =true ;

}
