package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hello.domain.MoneyBook;
import hello.repository.MoneyBookRepository;
import hello.service.MoneyBookService;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	MoneyBookService moneyBookService;

	@Autowired
	MoneyBookRepository moneyBookRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<MoneyBook> getMoneyBookList() {
		return moneyBookService.getMoneyBookList();
	}

	@RequestMapping(value = "{helloId}", method = RequestMethod.GET)
	public MoneyBook hello(@PathVariable Long helloId) {
		return moneyBookService.getMoneyBookById(helloId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public MoneyBook create(MoneyBook moneyBook) throws Exception {
		return moneyBookService.createMoneyBook(moneyBook);
	}

	@RequestMapping(value = "/createError", method = RequestMethod.POST)
	public MoneyBook createError(MoneyBook moneyBook) throws Exception {
		return moneyBookService.createMoneyBookAndLog(moneyBook);
	}

}