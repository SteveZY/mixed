package sbootdemo.domain;

import lombok.Builder;
import lombok.Value;

import javax.persistence.*;
import java.io.Serializable;

@Value
@Entity
public class Country implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String flag;

    public Country() {
        this.flag=null;
        this.name=null;
        this.id=null;

    }
    @Builder(toBuilder = true,builderClassName = "Bldr")
    public Country(String name, String flag) {
        this.id=null;
        this.name = name;
        this.flag = flag;
    }
}
