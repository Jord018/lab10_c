package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Participant;
import com.example.demo.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ParticipantDaoImpl implements ParticipantDao {
    final ParticipantRepository participantRepository;

    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }
}
