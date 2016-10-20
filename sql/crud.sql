-- users

-- insert
insert into users
values( user_seq.nextval, '안대혁', 'kickscar@gmail.com', '1234', 'male');

-- delete
delete from users;

commit;

-- select ( login )
select no, name 
  from users
 where email='kickscar@gmail.com'
   and password='1234';

select * from users;

 
 
-- board  

--view
select no, title, content  from board where no = 2;
update board set hit = hit + 1 where no = 2; -- 조회수 늘리기 

-- list
select count(*) from board;

select *
from
(select no, title, hit, reg_date, depth, name, users_no, rownum as rn 
   from(  select a.no,
                 a.title, 
	             a.hit, 
	             to_char(a.reg_date, 'yyyy-mm-dd hh:mi:ss') as reg_date, 
  	             b.name,
				 a.users_no,
				 a.depth 
            from board a, users b
           where a.users_no = b.no
      --     and title like '%kwd%' or content like '%kwd%'
        order by group_no desc, order_no asc ))
where (1-1)*5+1 <= rn  -- current page:1, page_size:2
  and rn <= 1*5;               



-- 새글
insert
  into board
values( board_seq.nextval, '배고프다 그만!','냉무', sysdate, 0, nvl((select max(group_no) from board),0) + 1,	 1, 0, 2 ); 


--답글
update board
   set order_no = order_no + 1
 where group_no = 2  -- 부모 글 그룹
   and order_no > 1; -- 부모 글 순서
    
insert
  into board
values( board_seq.nextval, 
        '짬뽕','냉무', sysdate, 0, 
		2, -- 부모글의 그룹 
		2, -- 부모글 순서  + 1
		1, -- 부모글 깊이  + 1 
		2 ); 


commit;

 