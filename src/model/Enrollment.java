package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
    private Integer id;
    private Integer scholarshipId;
    private Integer userId;
    private String fullName;
    private String gender;
    private LocalDate dob;
    private String phoneNumber;
    private Integer yearLevel;
    private String school;
    private String major;
    private String paymentStatus;
    private String paymentMethod;
    private Boolean isDeleted;
}