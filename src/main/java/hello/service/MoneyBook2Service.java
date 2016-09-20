package hello.service;

import hello.domain.MoneyBook;
import hello.repository.MoneyBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoneyBook2Service {

    @Autowired
    MoneyBookRepository moneyBookRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MoneyBook createMoneyBookRequiresNew(MoneyBook moneyBook) {
        return moneyBookRepository.save(moneyBook);
    }
}
