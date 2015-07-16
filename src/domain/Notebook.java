package domain;

import java.util.Date;
import javax.persistence.*;



@Entity
@Table(name = "NOTEBOOKS")
public class Notebook {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "NOTEBOOKS_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @Column(name = "NOTEBOOK_ID")
    private Long id;

    @ManyToOne
    private Vendor vendor;

    @Column(name = "NOTEBOOK_MODEL")
    private String model;

    @Column(name = "NOTEBOOK_MANUF_DATE")
    @Temporal(TemporalType.DATE)
    private Date manufactureDate;

    @ManyToOne
    private CPU cpu;

    @ManyToOne
    private Memory memory;

    public Notebook() {
        vendor = new Vendor();
        model = "Default";
        manufactureDate = new Date();
        cpu = new CPU();
        memory = new Memory();
    }

    public Notebook(Vendor vendor, String model,
                    Date manufactureDate, CPU cpu, Memory memory) {
        this.vendor = vendor;
        this.model = model;
        this.manufactureDate = manufactureDate;
        this.cpu = cpu;
        this.memory = memory;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Notebook {id: " + id
                + "; vendor:" + vendor
                + "; model:" + model
                + "; date:"	+ manufactureDate
                + "; cpu:" + cpu
                + "; memory:" + memory + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notebook)) return false;

        Notebook notebook = (Notebook) o;

        if (!id.equals(notebook.id)) return false;
        if (!vendor.equals(notebook.vendor)) return false;
        if (!model.equals(notebook.model)) return false;
        if (!manufactureDate.equals(notebook.manufactureDate)) return false;
        if (!cpu.equals(notebook.cpu)) return false;
        if (!memory.equals(notebook.memory)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 54 * result + vendor.hashCode();
        result = 54 * result + model.hashCode();
        result = 54 * result + manufactureDate.hashCode();
        result = 54 * result + cpu.hashCode();
        result = 54 * result + memory.hashCode();
        return result;
    }
}