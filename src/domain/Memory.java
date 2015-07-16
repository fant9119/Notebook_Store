package domain;

import javax.persistence.*;

@Entity
@Table(name = "MEMORIES")
public class Memory {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "MEMORIES_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @Column(name = "MEMORY_ID")
    private Long id;

    @ManyToOne
    private Vendor vendor;

    @Column(name = "CPU_FREQUENCY")
    private Double capacity;

    public Memory() {
        this(new Vendor(), 0D);
    }

    public Memory(Vendor vendor, double capacity) {
        this.vendor = vendor;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Memory {id: " + id
                + "; vendor:" + vendor
                + "; capacity:" + capacity + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Memory)) return false;

        Memory memory = (Memory) o;

        if (!vendor.equals(memory.vendor)) return false;
        if (!capacity.equals(memory.capacity)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 54 * vendor.hashCode();
        result = 54 * result + capacity.hashCode();
        return result;
    }
}
