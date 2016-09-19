package hello.repository;

import hello.domain.MoneyBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyBookRepository extends JpaRepository<MoneyBook, Long> {
}
