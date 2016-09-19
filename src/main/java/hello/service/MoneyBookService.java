package hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.MoneyBook;
import hello.repository.MoneyBookRepository;

@Service
public class MoneyBookService {

	@Autowired
	MoneyBookRepository moneyBookRepository;

	public List<MoneyBook> getMoneyBookList() {
		return moneyBookRepository.findAll();
	}

	@Transactional
	public MoneyBook getMoneyBookById(Long id) {
		return moneyBookRepository.findOne(id);
	}

	@Transactional
	public MoneyBook createMoneyBook(MoneyBook moneyBook) {
		return moneyBookRepository.save(moneyBook);
	}

	@Transactional
	public MoneyBook createMoneyBookAndLog(MoneyBook moneyBook) throws Exception {
		MoneyBook moneyBookResult = moneyBookRepository.save(moneyBook);
		logging();
		return moneyBookResult;
	}

	public void logging() throws Exception {
		throwException1();
	}

	public void throwException1() throws Exception {
		throw new Exception("Some Error");
	}
}
