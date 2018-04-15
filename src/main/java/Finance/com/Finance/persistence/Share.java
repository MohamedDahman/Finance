package Finance.com.Finance.persistence;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
public class Share {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;

    private String name;

    private double price;

    private long quntity;

    private MoveType moveType;

   @ManyToOne()
   private Users users;


    public Share() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double  price) {
        this.price = price;
    }

    public long getQuntity() {
        return quntity;
    }

    public void setQuntity(long quntity) {
        this.quntity = quntity;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
