package domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "STORES")
public class Store {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "STORES_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @Column(name = "STORE_ID")
    private Long id;

    @ManyToMany
    private Set<Notebook> notebooks = new HashSet<>();

    @Column(name = "STORE_AMOUNT")
    private Integer amount;

    @Column(name = "STORE_PRICE")
    private Double price;

    public Store() {
        this(null, 0, 0D);
    }

    public Store(Set<Notebook> notebooks, int amount, double price) {
        this.notebooks = notebooks;
        this.amount = amount;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Notebook> getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(Set<Notebook> notebooks) {
        this.notebooks = notebooks;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}