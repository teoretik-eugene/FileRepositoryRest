package ru.eugene.restest.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eugene.restest.DTO.FileDTO;
import ru.eugene.restest.models.FileModel;

import java.util.List;

@Component
public class FileDTOConverter {
    private ModelMapper modelMapper;

    public FileDTOConverter() {
        modelMapper = new ModelMapper();
    }

    public FileDTO toDTO(FileModel model){
        return modelMapper.map(model, FileDTO.class);
    }

    public List<FileDTO> toDTOList(List<FileModel> list){
        return list.stream().map(v -> modelMapper.map(v, FileDTO.class)).toList();
    }
}
