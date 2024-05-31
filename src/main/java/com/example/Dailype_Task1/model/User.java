package com.example.Dailype_Task1.model;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, updatable = true, name = "manager_id")
    private UUID managerId;

    // Other fields and getters/setters

    @PrePersist
    public void generateUuid() {
        if (managerId == null) {
            managerId = UUID.randomUUID();
        }
    }

    @Transient
    private String editableUuid; // Editable UUID-like field

    @Column(name = "full_name")
    @NotEmpty(message = "Full name must not be empty")
    private String fullName;

    @Column(name = "mobile_no")
    @Pattern(regexp = "^(0|\\+91)?\\d{10}$", message = "Must be a valid 10-digit mobile number")
    private String mobileNo;

    @Column(name = "pan_no")
    @Pattern(regexp = "^[A-Za-z]{5}[0-9]{4}[A-Za-z]$", message = "Must be a valid PAN number (e.g., AABCP1234C)")
    private String PanNo;

    public User() {

    }

    public User(int id, UUID managerId, String fullName, String mobileNo, String PanNo) {
        this.id = id;
        this.managerId = managerId;
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.PanNo = PanNo;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getManagerId() {
        return this.managerId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }

    public void setEditableUuid(String managerId) {
        // Validate UUID format before setting
        if (managerId.matches("^\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}$")) {
            this.editableUuid = managerId;
            // Convert editable UUID-like value to UUID and update the UUID field
            this.managerId = UUID.fromString(editableUuid);
        } else {
            throw new IllegalArgumentException("Invalid UUID format: " + managerId);
        }
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPanNo() {
        return this.PanNo;
    }

    public void setPanNo(String PanNo) {
        this.PanNo = PanNo.toUpperCase();
    }
}
