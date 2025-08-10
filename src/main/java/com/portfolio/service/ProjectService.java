package com.portfolio.service;

import com.portfolio.dto.ProjectDto;
import com.portfolio.entity.Project;
import com.portfolio.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(project -> modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }

    public Page<ProjectDto> getProjectsWithFilters(Project.Status status, String technology, String search, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Project> projects = projectRepository.findProjectsWithFilters(status, technology, search, pageable);
        
        return projects.map(project -> modelMapper.map(project, ProjectDto.class));
    }

    public List<ProjectDto> getFeaturedProjects() {
        return projectRepository.findByIsFeaturedTrueOrderBySortOrderAsc()
                .stream()
                .map(project -> modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }

    public Optional<ProjectDto> getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(project -> modelMapper.map(project, ProjectDto.class));
    }

    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = modelMapper.map(projectDto, Project.class);
        project.setId(null); // Ensure it's a new entity
        Project savedProject = projectRepository.save(project);
        return modelMapper.map(savedProject, ProjectDto.class);
    }

    public Optional<ProjectDto> updateProject(Long id, ProjectDto projectDto) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                    modelMapper.map(projectDto, existingProject);
                    existingProject.setId(id); // Ensure ID is preserved
                    Project updatedProject = projectRepository.save(existingProject);
                    return modelMapper.map(updatedProject, ProjectDto.class);
                });
    }

    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<String> getAllTechnologies() {
        return projectRepository.findAllTechnologies();
    }
}