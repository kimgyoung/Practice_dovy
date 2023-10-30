-- 모든 제약 조건 비활성화
SET REFERENTIAL_INTEGRITY FALSE;
truncate table user_tb;
SET REFERENTIAL_INTEGRITY TRUE;

--INSERT INTO user_tb (`id`,`email`,`password`,`userName`, `roles`) VALUES ('1', 'user2@green.com', '{bcrypt}$2a$10$3ia6a4bC.WvWFB4dYUtbIeBas7ELyeybz965MKwA5feKJOO4JXVia', '임꺽정', 'ROLE_ADMIN');
INSERT INTO user_tb (`id`,`email`,`password`,`username`, `roles`) VALUES ('2', 'user5@green.com', '{bcrypt}$2a$10$lo1MQiqnOs6OESRa7hM5keN00NP67t66L8H9dfxpv1bd9s4ntTPP.', '김테스', 'ROLE_USER');
