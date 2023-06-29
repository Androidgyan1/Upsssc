package com.gov.upsssc;

public class InvegilaterModel {
    String Name,Designation,District_Code,District_Name,Centre_Code,Center_Name,Centre_Address,DateTimeFrom,DateTimeTo,IsActive;

    public InvegilaterModel(String name, String designation, String district_Code, String district_Name, String centre_Code, String center_Name, String centre_Address, String dateTimeFrom, String dateTimeTo, String isActive) {
        Name = name;
        Designation = designation;
        District_Code = district_Code;
        District_Name = district_Name;
        Centre_Code = centre_Code;
        Center_Name = center_Name;
        Centre_Address = centre_Address;
        DateTimeFrom = dateTimeFrom;
        DateTimeTo = dateTimeTo;
        IsActive = isActive;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getDistrict_Code() {
        return District_Code;
    }

    public void setDistrict_Code(String district_Code) {
        District_Code = district_Code;
    }

    public String getDistrict_Name() {
        return District_Name;
    }

    public void setDistrict_Name(String district_Name) {
        District_Name = district_Name;
    }

    public String getCentre_Code() {
        return Centre_Code;
    }

    public void setCentre_Code(String centre_Code) {
        Centre_Code = centre_Code;
    }

    public String getCenter_Name() {
        return Center_Name;
    }

    public void setCenter_Name(String center_Name) {
        Center_Name = center_Name;
    }

    public String getCentre_Address() {
        return Centre_Address;
    }

    public void setCentre_Address(String centre_Address) {
        Centre_Address = centre_Address;
    }

    public String getDateTimeFrom() {
        return DateTimeFrom;
    }

    public void setDateTimeFrom(String dateTimeFrom) {
        DateTimeFrom = dateTimeFrom;
    }

    public String getDateTimeTo() {
        return DateTimeTo;
    }

    public void setDateTimeTo(String dateTimeTo) {
        DateTimeTo = dateTimeTo;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }
}
