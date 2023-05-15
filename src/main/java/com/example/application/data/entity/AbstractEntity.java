package com.example.application.data.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    // The initial value is to account for data.sql demo data ids
    // @SequenceGenerator(name = "idgenerator", initialValue = 1000)
    // private int id;

    // @Version
    // private int version;

    // public int getId() {
    //     return id;
    // }

    // public void setId(int id) {
    //     this.id = id;
    // }

    // public int getVersion() {
    //     return version;
    // }

    @Override
    public int hashCode() {
        // if (getId() != null) {
        //     return getId().hashCode();
        // }
        return super.hashCode();
    }

    // @Override
    // public boolean equals(Object obj) {
    //     if (!(obj instanceof AbstractEntity)) {
    //         return false; // null or other class
    //     }
    //     AbstractEntity other = (AbstractEntity) obj;

    //     // return getId() == other.getId();
    //     // return super.equals(other);
    // }
}