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

    private long price;

    private long quntity;

    private MoveType moveType;

   @ManyToOne()
   private Users users;


}
