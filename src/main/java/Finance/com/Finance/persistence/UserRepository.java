/**************************************************
 * Finance
 *
 *
 * code written by: Mohamed Dahman
 *
 * This program for buy and sell shares .
 *
 ***************************************************/

package Finance.com.Finance.persistence;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Users,Long> {


}
