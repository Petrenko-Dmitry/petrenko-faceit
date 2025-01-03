package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public AttachedFile(MultipartFile file, Task task) throws IOException {
        this.fileName = file.getOriginalFilename();
        this.fileType = file.getContentType();
        this.data = file.getBytes();
        this.task = task;
    }
}
