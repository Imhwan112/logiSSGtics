package entity;

import lombok.Data;

/**
 * 비회원 유저 클래스
 * 비회원도 문의를 남길 수 있기 때문에 QA_ID 를 제공을 해서,
 * 본인이 나중에 문의를 했던 글을 조회를 할 수 있게 해줌
 */
@Data
public class NonUser {
    private int id;
    private int qa_ID;
}
