package com.example.demo;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Event;
import com.example.demo.entity.Organizer;
import com.example.demo.entity.Participant;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.OrganizerRepository;
import com.example.demo.repository.ParticipantRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
    final EventRepository eventRepository;
    final OrganizerRepository organizerRepository;
    final ParticipantRepository participantRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Organizer org1, org2, org3;
        org1 = organizerRepository.save(Organizer.builder()
                .name("CAMT").build());
        org2 = organizerRepository.save(Organizer.builder()
                .name("CMU").build());
        org3 = organizerRepository.save(Organizer.builder()
                .name("ChiangMai").build());

        Event tempEvent;
        tempEvent = eventRepository.save(Event.builder()
                .category("Academic")
                .title("Midterm Exam")
                .description("A time for taking the exam")
                .location("CAMT Building")
                .date("3rd Sept")
                .time("3.00-4.00 pm.")
                .petsAllowed(false)
                .build());
        tempEvent.setOrganizer(org1);
        org1.getOwnEvents().add(tempEvent);
        Event event1 = tempEvent;

        tempEvent = eventRepository.save(Event.builder()
                .category("Academic")
                .title("Commencement Day")
                .description("A time for celebration")
                .location("CMU Convention hall")
                .date("21th Jan")
                .time("8.00am-4.00 pm.")
                .petsAllowed(false)
                .build());
        tempEvent.setOrganizer(org1);
        org1.getOwnEvents().add(tempEvent);
        Event event2 = tempEvent;

        tempEvent = eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Loy Krathong")
                .description("A time for Krathong")
                .location("Ping River")
                .date("21th Nov")
                .time("8.00-10.00 pm.")
                .petsAllowed(false)
                .build());
        tempEvent.setOrganizer(org2);
        org2.getOwnEvents().add(tempEvent);
        Event event3 = tempEvent;

        tempEvent = eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Songkran")
                .description("Let's Play Water")
                .location("Chiang Mai Moat")
                .date("13th April")
                .time("10.00am - 6.00 pm.")
                .petsAllowed(true)
                .build());
        tempEvent.setOrganizer(org3);
        org3.getOwnEvents().add(tempEvent);
        Event event4 = tempEvent;

        // 5 participants; each attends 3-4 of the 4 events so that every event ends up with at least 3 participants
        participantRepository.save(Participant.builder()
                .name("Alice Nguyen")
                .telNo("080-111-1111")
                .eventHistories(List.of(event1, event2, event3))
                .build());
        participantRepository.save(Participant.builder()
                .name("Bob Carter")
                .telNo("080-222-2222")
                .eventHistories(List.of(event1, event2, event4))
                .build());
        participantRepository.save(Participant.builder()
                .name("Chalisa Boonmee")
                .telNo("080-333-3333")
                .eventHistories(List.of(event1, event3, event4))
                .build());
        participantRepository.save(Participant.builder()
                .name("Daniel Kim")
                .telNo("080-444-4444")
                .eventHistories(List.of(event2, event3, event4))
                .build());
        participantRepository.save(Participant.builder()
                .name("Ploy Suwan")
                .telNo("080-555-5555")
                .eventHistories(List.of(event1, event2, event3, event4))
                .build());
    }
}
