package sbootdemo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Value
@Entity
public class City implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String state;
//    @Column
    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    public City() {
        this.id = null;
        this.name = null;
        this.state = null;
        this.country = null;
    }

    @Builder(toBuilder = true)
    public City(String name, String state, Country country) {
        this.id = null;
        this.name = name;
        this.state = state;
        this.country = country;
    }
//    @JsonPOJOBuilder(withPrefix = "")
//    public static final class Builder {
//    }
    // ... additional members, often include @OneToMany mappings


}