package kr.co.dglee.lecture.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

  private String city;
  private String street;
  private String zipcode;

  /**
   * Embeddable 클래스는 값을 변경할 수 없도록 설계해야 한다.
   * 따라서 기본 생성자를 protected로 제한한다. (JPA 요구사항)
   */
  protected Address() {
  }

  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }
}
