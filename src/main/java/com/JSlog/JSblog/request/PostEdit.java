package com.JSlog.JSblog.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
public class PostEdit {

    //@NotBlank(message = "타이틀을 입력해주세요.")
    private final String title;

    //@NotBlank(message = "컨텐츠를 입력해주세요.")
    private final String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    public PostEdit changeTitle(String title){
//        return PostEdit.builder()
//                .title(title)
//                .content(content)
//                .build();
//    }

    //빌더의장점
    /*
     가독성에 좋다.
     값 생성에 대한 유연함
     필요한 값만 받을 수 있음 // 오버로딩 가능한 조건 찾아보세요.
     객체의 불변성
   */
}
