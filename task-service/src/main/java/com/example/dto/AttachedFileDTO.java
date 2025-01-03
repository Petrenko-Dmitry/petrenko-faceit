package com.example.dto;

import com.example.entity.AttachedFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

public record AttachedFileDTO(
        Long id,
        String fileName,
        String fileType,
        String data
) {

    public AttachedFileDTO(AttachedFile attachedFile) {
        this(
                attachedFile.getId(),
                attachedFile.getFileName(),
                attachedFile.getFileType(),
                new String(attachedFile.getData(), StandardCharsets.UTF_8)
        );
    }

    public static List<AttachedFileDTO> convertToDTO(List<AttachedFile> attachedFiles) {
        return attachedFiles.stream()
                .map(AttachedFileDTO::new)
                .toList();
    }
}
