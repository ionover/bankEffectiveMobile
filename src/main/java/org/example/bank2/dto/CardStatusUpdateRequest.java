package org.example.bank2.dto;

import jakarta.validation.constraints.NotNull;
import org.example.bank2.dto.enums.Status;

public class CardStatusUpdateRequest {

    @NotNull
    private Status status;

    public CardStatusUpdateRequest() {
    }

    public CardStatusUpdateRequest(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
