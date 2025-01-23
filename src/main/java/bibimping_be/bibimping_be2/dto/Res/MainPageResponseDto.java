package bibimping_be.bibimping_be2.dto.Res;

import bibimping_be.bibimping_be2.dto.AlarmDto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MainPageResponseDto {

    private Long businessGroupId;
    private String businessGroupName;
    private List<AlarmDto> alarm;


    public Long getBusinessGroupId() {
        return businessGroupId;
    }

    public void setBusinessGroupId(Long businessGroupId) {
        this.businessGroupId = businessGroupId;
    }

    public String getBusinessGroupName() {
        return businessGroupName;
    }

    public void setBusinessGroupName(String businessGroupName) {
        this.businessGroupName = businessGroupName;
    }

    public List<AlarmDto> getAlarm() {
        return alarm;
    }

    public void setAlarm(List<AlarmDto> alarm) {
        this.alarm = alarm;
    }

    public MainPageResponseDto() {}


    public MainPageResponseDto(Long businessGroupId, String businessGroupName, List<AlarmDto> alarm) {
        this.businessGroupId = businessGroupId;
        this.businessGroupName = businessGroupName;
        this.alarm = alarm;
    }
}
