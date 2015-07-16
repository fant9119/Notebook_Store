package domain;

import javax.persistence.*;

@Entity
@Table(name = "CPUS")
public class CPU {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "CPUS_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @Column(name = "CPU_ID")
    private Long id;

    @ManyToOne
    private Vendor vendor;

    @Column(name = "CPU_FREQUENCY")
    private Double frequency;

    @Column(name = "CPU_MODEL")
    private String model;

    public CPU() {
        this(new Vendor(), 0D, "Default");
    }

    public CPU(Vendor vendor, Double frequency, String model) {
        this.vendor = vendor;
        this.frequency = frequency;
        this.model = model;
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

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "CPU {id: " + id
                + "; vendor:" + vendor
                + "; frequency:" + frequency
                + "; model:" + model + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CPU)) return false;

        CPU cpu = (CPU) o;

        if (!vendor.equals(cpu.vendor)) return false;
        if (!model.equals(cpu.model)) return false;
        if (!frequency.equals(cpu.frequency)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vendor.hashCode();
        result = 54 * result + model.hashCode();
        result = 54 * result + frequency.hashCode();
        return result;
    }
}
