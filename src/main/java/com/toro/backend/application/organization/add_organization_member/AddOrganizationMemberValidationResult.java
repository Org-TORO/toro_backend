package com.toro.backend.application.organization.add_organization_member;

import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.models.User;

public record AddOrganizationMemberValidationResult(
    Organization organization,
    User user
) {

}
