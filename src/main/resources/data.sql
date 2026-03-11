insert into user_details(id,birth_date,name)
values
(10001,current_date(),'Range'),
(10002,current_date(),'Ravi'),
(10003,current_date(),'Sathish');

insert into post(id,description,user_id)
values
(20001,'I want to learn AWS',10001),
(20002,'I want to learn DevOps',10001),
(20003,'I want to Get AWS Certified',10002),
(20004,'I want to learn Multi Cloud',10002);


