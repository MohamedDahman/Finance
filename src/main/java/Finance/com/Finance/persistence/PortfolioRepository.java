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

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long>{
}
