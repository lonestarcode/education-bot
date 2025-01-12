package com.edubot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edubot.model.material.StudyMaterial;

import java.util.*;
import java.util.stream.Collectors;

/**
 * StudyMaterialService manages CRUD operations and delegates PDF operations to BotActivationService.
 */
@Service
public class StudyMaterialService {

    private final List<StudyMaterial> studyMaterials = new ArrayList<>();
    private final BotActivationService botActivationService;

    @Autowired
    public StudyMaterialService(BotActivationService botActivationService) {
        this.botActivationService = botActivationService;
    }

    // ========================= CRUD OPERATIONS =========================

    public String addMaterial(String title, String content, String subject) {
        if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Title and content cannot be empty.");
        }
        studyMaterials.add(new StudyMaterial(title, content, subject));
        return "Study material '" + title + "' added successfully.";
    }

    public List<String> getAllMaterials() {
        if (studyMaterials.isEmpty()) {
            return List.of("No study materials available.");
        }
        return studyMaterials.stream()
                .map(StudyMaterial::toString)
                .collect(Collectors.toList());
    }

    public String getMaterialByTitle(String title) {
        return studyMaterials.stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .map(StudyMaterial::getContent)
                .orElse("Study material with title '" + title + "' not found.");
    }

    public String updateMaterial(String title, String newContent) {
        for (StudyMaterial material : studyMaterials) {
            if (material.getTitle().equalsIgnoreCase(title)) {
                material.setContent(newContent);
                return "Study material '" + title + "' updated successfully.";
            }
        }
        return "Study material with title '" + title + "' not found.";
    }

    public String deleteMaterial(String title) {
        Optional<StudyMaterial> material = studyMaterials.stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(title))
                .findFirst();

        if (material.isPresent()) {
            studyMaterials.remove(material.get());
            return "Study material '" + title + "' deleted successfully.";
        } else {
            return "Study material with title '" + title + "' not found.";
        }
    }

    // ========================= PDF INTEGRATION =========================

    public String processMaterialPDF(String filePath) {
        return botActivationService.processPDF(filePath);
    }
}