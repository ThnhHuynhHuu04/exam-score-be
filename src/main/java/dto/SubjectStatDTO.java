package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectStatDTO {
    private String subject;
    private Long levelAbove8;
    private Long level6To8;
    private Long level4To6;
    private Long levelBelow4;
}
