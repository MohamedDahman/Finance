package Finance.com.Finance.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;

    private String name;

    private long quntity;

    private double price;

    @ManyToOne
    private Users user;

    public Portfolio() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuntity() {
        return quntity;
    }

    public void setQuntity(long quntity) {
        this.quntity = quntity;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", quntity=" + quntity +
                ", user=" + user +
                '}';
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
