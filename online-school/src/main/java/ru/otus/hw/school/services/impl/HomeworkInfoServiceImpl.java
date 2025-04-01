package ru.otus.hw.school.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.school.dto.HomeworkInfoDto;
import ru.otus.hw.school.models.HomeworkInfo;
import ru.otus.hw.school.repositories.HomeworkInfoRepository;
import ru.otus.hw.school.services.HomeworkInfoService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeworkInfoServiceImpl implements HomeworkInfoService {

    private final HomeworkInfoRepository homeworkInfoRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<HomeworkInfoDto> getAll(Pageable pageable) {
        Page<HomeworkInfo> homeworksPage = homeworkInfoRepository.findAll(pageable);
        List<HomeworkInfoDto> homeworkInfoDtos = homeworksPage.stream()
                .map(homeworkInfo -> modelMapper.map(homeworkInfo, HomeworkInfoDto.class))
                .toList();

        return new PageImpl<>(homeworkInfoDtos, homeworksPage.getPageable(), homeworksPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public HomeworkInfoDto getById(UUID id) {
        HomeworkInfo homeworkInfo = homeworkInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ДЗ Инфо с id %s не найдено".formatted(id)));

        return modelMapper.map(homeworkInfo, HomeworkInfoDto.class);
    }

    @Override
    @Transactional
    public HomeworkInfoDto create(HomeworkInfoDto homeworkInfoDto) {
        HomeworkInfo homeworkInfo = modelMapper.map(homeworkInfoDto, HomeworkInfo.class);
        homeworkInfo.setCreatedAt(Instant.now());
        homeworkInfoRepository.save(homeworkInfo);

        return modelMapper.map(homeworkInfo, HomeworkInfoDto.class);
    }

    @Override
    @Transactional
    public HomeworkInfoDto update(HomeworkInfoDto homeworkInfoDto) {
        UUID id = homeworkInfoDto.getId();
        HomeworkInfo homeworkInfo = homeworkInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ДЗ Инфо с id %s не найдено".formatted(id)));
        homeworkInfo.setTopic(homeworkInfoDto.getTopic());
        homeworkInfo.setUpdatedAt(Instant.now());

        homeworkInfoRepository.save(homeworkInfo);

        return modelMapper.map(homeworkInfo, HomeworkInfoDto.class);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        homeworkInfoRepository.deleteById(id);
    }
}
