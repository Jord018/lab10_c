package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantEventsDTO {
    Long id;
    String name;
    String telNo;
    @Builder.Default
    List<ParticipantEventHistoryDTO> eventHistories = new ArrayList<>();
}
