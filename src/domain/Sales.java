package domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "SALES")
public class Sales {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "SALES_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @Column(name = "SALE_ID")
    private Long id;

    @ManyToMany
    private Set<Store> stores = new HashSet<>();

    @Column(name = "SALE_DATE")
    private Date date;

    @Column(name = "SALE_AMOUNT")
    private Integer amount;

    public Sales() {
        this(null, new Date(), 0);
    }

    public Sales(Set<Store> stores, Date date, Integer amount) {
        this.stores = stores;
        this.date = date;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
