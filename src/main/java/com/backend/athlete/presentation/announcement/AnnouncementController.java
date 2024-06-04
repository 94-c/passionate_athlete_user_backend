package com.backend.athlete.presentation.announcement;

import com.backend.athlete.application.AnnouncementService;
import com.backend.athlete.presentation.announcement.request.CreateAnnouncementRequest;
import com.backend.athlete.presentation.announcement.request.PageSearchAnnouncementRequest;
import com.backend.athlete.presentation.announcement.request.UpdateAnnouncementRequest;
import com.backend.athlete.presentation.announcement.request.UpdateAnnouncementStatusRequest;
import com.backend.athlete.presentation.announcement.response.*;
import com.backend.athlete.support.constant.PageConstant;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final ObjectMapper objectMapper;

    public AnnouncementController(AnnouncementService announcementService, ObjectMapper objectMapper) {
        this.announcementService = announcementService;
        this.objectMapper = objectMapper;
    }

    @GetMapping()
    public ResponseEntity<Page<PageSearchAnnouncementResponse>> searchAnnouncements(
            @RequestParam(defaultValue = "", required = false) String title,
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage
    ) {
        PageSearchAnnouncementRequest request = new PageSearchAnnouncementRequest(title, name);
        Page<PageSearchAnnouncementResponse> response = announcementService.searchAnnouncements(request, page, perPage);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<CreateAnnouncementResponse> createAnnouncement(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                         @RequestParam("announcement") String announcement,
                                                                         @RequestParam("file") MultipartFile file) {
        try {
            CreateAnnouncementRequest request = objectMapper.readValue(announcement, CreateAnnouncementRequest.class);
            CreateAnnouncementResponse response = announcementService.createAnnouncement(userPrincipal, request, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAnnouncementResponse> getAnnouncement(@PathVariable Long id) {
        GetAnnouncementResponse response = announcementService.getAnnouncement(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateAnnouncementResponse> updateAnnouncement(@PathVariable Long id,
                                                                         @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                         @RequestParam("announcement") String announcement,
                                                                         @RequestParam("file") MultipartFile file) {
        try {
            UpdateAnnouncementRequest request = objectMapper.readValue(announcement, UpdateAnnouncementRequest.class);
            UpdateAnnouncementResponse response = announcementService.updateAnnouncement(id, userPrincipal, request, file);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<UpdateAnnouncementStatusResponse> updateAnnouncementStatus(@PathVariable Long id, @RequestBody UpdateAnnouncementStatusRequest request) {
        UpdateAnnouncementStatusResponse response = announcementService.updateAnnouncementStatus(id, request.isStatus());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
