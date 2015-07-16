package domain;

import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VENDORS")
public class Vendor {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "VENDORS_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @Column(name = "VENDOR_ID")
    private Long id;

    @Column(name = "VENDOR_NAME")
    private String name;

    @OneToMany (fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Set<CPU> cpus = new HashSet<>();

    public Vendor() {
        this("Default");
    }

    public Vendor(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendor)) return false;

        Vendor vendor = (Vendor) o;

        if (!name.equals(vendor.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 54*name.hashCode();
        return result;
    }
}