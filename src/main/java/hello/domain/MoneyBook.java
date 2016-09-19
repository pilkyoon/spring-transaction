package hello.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "MONEYBOOK")
public class MoneyBook {

	@Id
	@GeneratedValue
	Long id;

//	@Column(name = "TITLE")
	String title;

//	@Column(name = "VALUE")
	Long value;

//	@Column(name = "COMMENT")
	String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MoneyBook moneyBook = (MoneyBook) o;

		return id != null ? id.equals(moneyBook.id) : moneyBook.id == null;

	}

	@Override public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override public String toString() {
		return com.google.common.base.MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("title", title)
				.add("value", value)
				.add("comment", comment)
				.toString();
	}
}