-- 모든 제약 조건 비활성화
SET REFERENTIAL_INTEGRITY FALSE;
truncate table user_tb;
SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO user_tb (`id`,`email`,`password`,`username`, `roles`) VALUES ('1', 'admin@green.com', 'password@2023', '김그린', 'ROLE_ADMIN');