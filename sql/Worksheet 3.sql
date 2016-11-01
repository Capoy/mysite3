select *
from (select rownum rn, a.*
from ( select no,
       name, 
	   content, 
	   password, 
	   to_char(reg_date, 'yyyy-mm-dd hh:mi:ss' ) as reg_date
 from guestbook
order by reg_date desc ) a )
where (3-1)*5+1 <= rn and rn <= 3*5;