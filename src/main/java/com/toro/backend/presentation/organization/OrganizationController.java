package com.toro.backend.presentation.organization;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toro.backend.application.organization.create_organization.CreateOrganizationUseCase;
import com.toro.backend.application.organization.add_organization_member.AddOrganizationMemberUseCase;
import com.toro.backend.application.organization.delete_organization.DeleteOrganizationUseCase;
import com.toro.backend.application.organization.get_organization.GetOrganizationUseCase;
import com.toro.backend.application.organization.get_organization_members.GetOrganizationMembersUseCase;
import com.toro.backend.application.organization.get_organizations.GetOrganizationsUseCase;
import com.toro.backend.application.organization.remove_organization_member.RemoveOrganizationMemberUseCase;
import com.toro.backend.application.organization.search_organizations.SearchOrganizationsUseCase;
import com.toro.backend.application.organization.update_organization.UpdateOrganizationUseCase;
import com.toro.backend.application.organization.update_organization_member.UpdateOrganizationMemberUseCase;
import com.toro.backend.infrastructure.api.SuccessResponse;
import com.toro.backend.infrastructure.mapper.organization.AddOrganizationMemberMapper;
import com.toro.backend.infrastructure.mapper.organization.CreateOrganizationMapper;
import com.toro.backend.infrastructure.mapper.organization.GetOrganizationMapper;
import com.toro.backend.infrastructure.mapper.organization.GetOrganizationMembersMapper;
import com.toro.backend.infrastructure.mapper.organization.GetOrganizationsMapper;
import com.toro.backend.infrastructure.mapper.organization.SearchOrganizationsMapper;
import com.toro.backend.infrastructure.mapper.organization.UpdateOrganizationMapper;
import com.toro.backend.infrastructure.mapper.organization.UpdateOrganizationMemberMapper;
import com.toro.backend.presentation.organization.request.AddOrganizationMemberRequest;
import com.toro.backend.presentation.organization.request.CreateOrganizationRequest;
import com.toro.backend.presentation.organization.request.UpdateOrganizationMemberRequest;
import com.toro.backend.presentation.organization.request.UpdateOrganizationRequest;
import com.toro.backend.presentation.organization.response.AddOrganizationMemberResponse;
import com.toro.backend.presentation.organization.response.CreateOrganizationResponse;
import com.toro.backend.presentation.organization.response.GetOrganizationMembersResponse;
import com.toro.backend.presentation.organization.response.GetOrganizationResponse;
import com.toro.backend.presentation.organization.response.GetOrganizationsResponse;
import com.toro.backend.presentation.organization.response.SearchOrganizationsResponse;
import com.toro.backend.presentation.organization.response.UpdateOrganizationMemberResponse;
import com.toro.backend.presentation.organization.response.UpdateOrganizationResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/organizations")
@RequiredArgsConstructor 
public class OrganizationController {

    private final GetOrganizationsUseCase getOrganizationsUseCase;
    private final GetOrganizationUseCase getOrganizationUseCase;
    private final SearchOrganizationsUseCase searchOrganizationsUseCase;
    private final CreateOrganizationUseCase createOrganizationUseCase;
    private final UpdateOrganizationUseCase updateOrganizationUseCase;
    private final DeleteOrganizationUseCase deleteOrganizationUseCase;

    private final GetOrganizationMembersUseCase getOrganizationMembersUseCase;
    private final AddOrganizationMemberUseCase addOrganizationMemberUseCase;
    private final UpdateOrganizationMemberUseCase updateOrganizationMemberUseCase;
    private final RemoveOrganizationMemberUseCase removeOrganizationMemberUseCase;


    // ================================== Organization ===============================================
    @GetMapping("/get-organizations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<List<GetOrganizationsResponse>>> getOrganizations() {
        List<GetOrganizationsResponse> response = getOrganizationsUseCase.execute()
            .stream()
            .map(GetOrganizationsMapper::toResponse)
            .toList();

        return ResponseEntity.ok(
            SuccessResponse.success("Get organizations successfully", response)
        );
    }

    @GetMapping("/search-organizations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<List<SearchOrganizationsResponse>>> searchOrganizations(
        @RequestParam(value = "field", required = false) String field,
        @RequestParam(value = "value", required = false) String value
    ) {
        List<SearchOrganizationsResponse> response = searchOrganizationsUseCase.execute(field, value)
            .stream()
            .map(SearchOrganizationsMapper::toResponse)
            .toList();

        return ResponseEntity.ok(
            SuccessResponse.success("Search organizations successfully", response)
        );
    }

    @GetMapping("/get-organization")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<GetOrganizationResponse>> getOrganization(
        @RequestParam ("organization_id")
        @NotNull(message = "Organization id is required") Long organizationId
    ) {
        GetOrganizationResponse response = GetOrganizationMapper.toResponse(getOrganizationUseCase.execute(organizationId));

        return ResponseEntity.ok(
            SuccessResponse.success("Get organization successfully", response)
        );
    }

    @PostMapping("/create-organization")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<CreateOrganizationResponse>> createOrganization(
        @Valid @RequestBody CreateOrganizationRequest request
    ) {
        CreateOrganizationResponse response = CreateOrganizationMapper.toResponse(createOrganizationUseCase.execute(request));

        return ResponseEntity.ok(
            SuccessResponse.success("Create organization successfully", response)
        );
    }

    @PutMapping("/update-organization")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<UpdateOrganizationResponse>> updateOrganization(
        @RequestParam ("organization_id")
        @NotNull(message = "Organization id is required") Long organizationId,
        @Valid @RequestBody UpdateOrganizationRequest request
    ) {
        UpdateOrganizationResponse response = UpdateOrganizationMapper.toResponse(
            updateOrganizationUseCase.execute(organizationId, request)
        );

        return ResponseEntity.ok(
            SuccessResponse.success("Update organization successfully", response)
        );
    }

    @DeleteMapping("/delete-organization/{organization_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<Void>> deleteOrganization(
        @RequestParam ("organization_id")
        @NotNull(message = "Organization id is required") Long organizationId
    ) {
        deleteOrganizationUseCase.execute(organizationId);

        return ResponseEntity.ok(
            SuccessResponse.successMessage("Delete organization successfully")
        );
    }

    // ============================== Organization Member =========================================
    @GetMapping("/get-organization-members")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<List<GetOrganizationMembersResponse>>> getOrganizationMembers(
        @RequestParam("organization_id")
        @NotNull(message = "Organization id is required") Long organizationId
    ) {
        List<GetOrganizationMembersResponse> response = getOrganizationMembersUseCase.execute(organizationId)
            .stream()
            .map(GetOrganizationMembersMapper::toResponse)
            .toList();

        return ResponseEntity.ok(
            SuccessResponse.success("Get organization members successfully", response)
        );
    }

    @PostMapping("/add-organization-member")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<AddOrganizationMemberResponse>> addOrganizationMember(
        @RequestParam("organization_id")
        @NotNull(message = "Organization id is required") Long organizationId,
        @Valid @RequestBody AddOrganizationMemberRequest request
    ) {
        AddOrganizationMemberResponse response = AddOrganizationMemberMapper.toResponse(
            addOrganizationMemberUseCase.execute(organizationId, request)
        );

        return ResponseEntity.ok(
            SuccessResponse.success("Add organization member successfully", response)
        );
    }

    @PutMapping("/update-organization-member")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<UpdateOrganizationMemberResponse>> updateOrganizationMember(
        @RequestParam("member_id")
        @NotNull(message = "Member id is required") Long memberId,
        @Valid @RequestBody UpdateOrganizationMemberRequest request
    ) {
        UpdateOrganizationMemberResponse response = UpdateOrganizationMemberMapper.toResponse(
            updateOrganizationMemberUseCase.execute(memberId, request)
        );

        return ResponseEntity.ok(
            SuccessResponse.success("Update organization member successfully", response)
        );
    }

    @DeleteMapping("/remove-organization-member")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<Void>> removeOrganizationMember(
        @RequestParam("member_id")
        @NotNull(message = "Member id is required") Long memberId
    ) {
        removeOrganizationMemberUseCase.execute(memberId);

        return ResponseEntity.ok(
            SuccessResponse.successMessage("Remove organization member successfully")
        );
    }
}
