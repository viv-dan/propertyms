use propertyproject;
-- Insert into company
insert into management_company (company_name,email_id,phone_number) values ('ABC','abc@gmail.com','9876543219');
SELECT * FROM management_company;

-- Building Inserts
insert into building (cid,address,building_name,zipcode,number_of_floors,parking_spots,type_of_building)
		values (1,'Apex,S Avenue','The First','02130',10,20,'Apartment');
insert into building (cid,address,building_name,zipcode,number_of_floors,parking_spots,type_of_building)
		values (1,'45 Apex Street,S Avenue','The Second','02130',9,20,'Apartment');        
SELECT * FROM BUILDING;



-- Amenities
insert into amenity values(1,'Swimming Pool');
insert into amenity values(2,'Gym');
insert into amenity values(3,'Parking Garage');
insert into amenity values(4,'Resident Lounge');
select * from amenity;

-- Units
insert into unit values(120,1,2,2,324,109090.989);
insert into unit values(121,1,2,2,324,109090.989);
insert into unit values(122,1,2,2,324,109090.989);
insert into unit values(123,1,2,2,324,109090.989);
insert into unit values(124,1,2,2,324,109090.989);
insert into unit(unit_no,bid,no_of_bedrooms,no_of_bathrooms,area) values(120,2,2,2,224);
insert into unit(unit_no,bid,no_of_bedrooms,no_of_bathrooms,area) values(121,2,2,2,224);
insert into unit(unit_no,bid,no_of_bedrooms,no_of_bathrooms,area) values(122,2,2,2,200);
insert into unit(unit_no,bid,no_of_bedrooms,no_of_bathrooms,area) values(123,2,2,2,200);
insert into unit(unit_no,bid,no_of_bedrooms,no_of_bathrooms,area) values(124,2,2,2,200);
select *  from unit order by bid;


-- Lease
insert into lease values (1201,'2021-01-01','2022-01-01');
insert into lease values (1211,'2021-01-01','2022-01-01');
insert into lease values (1221,'2021-01-01','2022-01-01');
insert into lease values (1231,'2021-01-01','2022-01-01');

select * from lease;

-- Tenant 
insert into tenant(

