package com.example.demo.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.demo.entity.Event;
import com.example.demo.entity.EventDTO;
import com.example.demo.entity.Organizer;
import com.example.demo.entity.OrganizerDTO;
import com.example.demo.entity.Participant;
import com.example.demo.entity.ParticipantDTO;
import com.example.demo.entity.ParticipantEventsDTO;

@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    @Mapping(target = "participants", source = "participants")
    EventDTO getEventDto(Event event);

    List<EventDTO> getEventDto(List<Event> events);

    ParticipantDTO getParticipantDTO(Participant participant);
    List<ParticipantDTO> getParticipantDTO(List<Participant> participants);

    OrganizerDTO getOrganizerDTO(Organizer organizer);
    List<OrganizerDTO> getOrganizerDTO(List<Organizer> organizers);
    ParticipantEventsDTO getParticipantEventsDTO(Participant participant);
    List<ParticipantEventsDTO> getParticipantEventsDTO(List<Participant> participants);
}
