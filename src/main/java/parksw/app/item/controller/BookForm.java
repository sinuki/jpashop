package parksw.app.item.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * BookForm
 * author: sinuki
 * createdAt: 2019/12/08
 **/
@Getter
@Setter
public class BookForm {

    private Long id;

    @NotEmpty(message = "책 제목을 입력해주세요.")
    private String name;
    private int price;
    private int stockQuantity;

    @NotEmpty(message = "저자를 입력해주세요.")
    private String author;
    @NotEmpty(message = "ISBN을 입력해주세요.")
    private String isbn;
}
